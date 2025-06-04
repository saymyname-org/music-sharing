package ru.improve.openfy.core.storage;

import lombok.Getter;
import ru.improve.openfy.core.configuration.YandexStorageConfigData;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Getter
public class S3StorageClientWrapper implements AutoCloseable {

    private final YandexStorageConfigData yandexStorageConfigData;

    private S3Client s3Client;

    public S3StorageClientWrapper(YandexStorageConfigData yandexStorageConfigData) {
        this.yandexStorageConfigData = yandexStorageConfigData;

        AwsCredentials awsCredentials = AwsBasicCredentials.create(
                yandexStorageConfigData.getS3AccessKey(), yandexStorageConfigData.getS3SecretKey());
        AwsCredentialsProvider awsProvider = StaticCredentialsProvider.create(awsCredentials);

        s3Client = S3Client.builder()
                .credentialsProvider(awsProvider)
                .endpointOverride(URI.create(yandexStorageConfigData.getServiceEndpoint()))
                .region(Region.of(yandexStorageConfigData.getSigningRegion()))
                .build();
    }

    @Override
    public void close() {
        s3Client.close();
    }
}
