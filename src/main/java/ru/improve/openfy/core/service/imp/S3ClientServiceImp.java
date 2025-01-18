package ru.improve.openfy.core.service.imp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.improve.openfy.api.dto.upload.UploadTrackRequest;
import ru.improve.openfy.core.configuration.storage.S3StorageConfigData;
import ru.improve.openfy.core.service.S3ClientService;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

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
            Map<String, String> metadata = Map.of(
                    "size", String.valueOf(file.getBytes().length)
            );

            PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(s3StorageConfigData.getMusicBucketName())
                    .key(uploadTrackRequest.getTrackName())
                    .metadata(metadata)
                    .build();

            RequestBody requestBody = RequestBody.fromBytes(file.getBytes());

        s3Client.putObject(putRequest, requestBody);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
