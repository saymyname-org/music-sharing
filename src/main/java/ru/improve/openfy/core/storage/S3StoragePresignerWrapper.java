package ru.improve.openfy.core.storage;

import lombok.Getter;
import ru.improve.openfy.core.configuration.YandexStorageConfigData;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;

@Getter
public class S3StoragePresignerWrapper implements AutoCloseable {

    private final YandexStorageConfigData yandexStorageConfigData;

    private S3Presigner s3Presigner;

    public S3StoragePresignerWrapper(YandexStorageConfigData yandexStorageConfigData) {
        this.yandexStorageConfigData = yandexStorageConfigData;

        AwsCredentials awsCredentials = AwsBasicCredentials.create(
                yandexStorageConfigData.getS3AccessKey(), yandexStorageConfigData.getS3SecretKey());
        AwsCredentialsProvider awsProvider = StaticCredentialsProvider.create(awsCredentials);

        s3Presigner = S3Presigner.builder()
                .credentialsProvider(awsProvider)
                .endpointOverride(URI.create(yandexStorageConfigData.getServiceEndpoint()))
                .region(Region.of(yandexStorageConfigData.getSigningRegion()))
                .build();
    }

    @Override
    public void close() {
        s3Presigner.close();
    }
}
