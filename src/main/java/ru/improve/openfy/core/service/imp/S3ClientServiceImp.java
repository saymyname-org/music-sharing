package ru.improve.openfy.core.service.imp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.improve.openfy.api.dto.upload.UploadTrackRequest;
import ru.improve.openfy.api.error.ErrorCode;
import ru.improve.openfy.api.error.ServiceException;
import ru.improve.openfy.core.configuration.storage.S3StorageConfigData;
import ru.improve.openfy.core.service.S3ClientService;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Service
public class S3ClientServiceImp implements S3ClientService {

    private final S3Client s3Client;

    private final S3StorageConfigData s3StorageConfigData;

    @Override
    public void saveTrack(UploadTrackRequest uploadTrackRequest) {
        MultipartFile file = uploadTrackRequest.getFile();

        try {
            byte[] fileHashByteArray = DigestUtils.sha256(file.getInputStream());
            String fileHashString = Base64.getEncoder().encodeToString(fileHashByteArray);

            Map<String, String> fileMetadata = Map.of(
                    "trackName", uploadTrackRequest.getTrackName(),
                    "authorName", uploadTrackRequest.getAuthorName(),
                    "fileSize", String.valueOf(file.getSize())
            );

            PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(s3StorageConfigData.getMusicBucketName())
                    .key(fileHashString)
                    .metadata(fileMetadata)
                    .checksumSHA256("UNSIGNED-PAYLOAD")
                    .build();

            RequestBody requestBody = RequestBody.fromInputStream(file.getInputStream(), file.getSize());
            s3Client.putObject(putRequest, requestBody);
        } catch (IOException ex) {
            throw new ServiceException(ErrorCode.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
