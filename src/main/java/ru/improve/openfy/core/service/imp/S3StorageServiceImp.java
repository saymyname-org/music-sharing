package ru.improve.openfy.core.service.imp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.improve.openfy.api.dto.upload.UploadTrackRequest;
import ru.improve.openfy.core.configuration.YandexStorageConfigData;
import ru.improve.openfy.core.service.S3StorageService;
import ru.improve.openfy.core.storage.S3StorageClientWrapper;
import ru.improve.openfy.core.storage.S3StoragePresignerWrapper;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;

import static ru.improve.openfy.core.storage.constants.HashRequestType.UNSIGNED_PAYLOAD;

@RequiredArgsConstructor
@Slf4j
@Service
public class S3StorageServiceImp implements S3StorageService {

    private final YandexStorageConfigData yandexStorageConfigData;

    @Override
    public void uploadTrackInStorage(UploadTrackRequest uploadTrackRequest, String fileHash, String bucket) throws IOException {
        MultipartFile file = uploadTrackRequest.getFile();

        try (S3StorageClientWrapper s3StorageClientWrapper = new S3StorageClientWrapper(yandexStorageConfigData)) {
            S3Client s3Client = s3StorageClientWrapper.getS3Client();

            Map<String, String> fileMetadata = Map.of(
                    "trackName", uploadTrackRequest.getTrackName(),
                    "authorName", uploadTrackRequest.getAuthorName(),
                    "fileSize", String.valueOf(file.getSize())
            );

            PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileHash)
                    .metadata(fileMetadata)
                    .checksumSHA256(UNSIGNED_PAYLOAD)
                    .build();

            RequestBody requestBody = RequestBody.fromInputStream(file.getInputStream(), file.getSize());
            s3Client.putObject(putRequest, requestBody);
        }
    }

    @Override
    public String getFileLink(String key, String bucket) {
        try (S3StoragePresignerWrapper s3StoragePresignerWrapper = new S3StoragePresignerWrapper(yandexStorageConfigData)) {
            S3Presigner s3Presigner = s3StoragePresignerWrapper.getS3Presigner();

            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();

            GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(10))
                    .getObjectRequest(getObjectRequest)
                    .build();

            return s3Presigner.presignGetObject(getObjectPresignRequest).url().toString();
        }
    }
}
