package ru.improve.openfy.core.storage;

import lombok.Getter;
import ru.improve.openfy.core.configuration.storage.S3StorageConfigData;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Getter
public class S3StorageClientWrapper implements AutoCloseable {

    private final S3StorageConfigData s3StorageConfigData;

    private S3Client s3Client;

    public S3StorageClientWrapper(S3StorageConfigData s3StorageConfigData) {
        this.s3StorageConfigData = s3StorageConfigData;

        AwsCredentials awsCredentials = AwsBasicCredentials.create(
                s3StorageConfigData.getS3AccessKey(), s3StorageConfigData.getS3SecretKey());
        AwsCredentialsProvider awsProvider = StaticCredentialsProvider.create(awsCredentials);

        s3Client = S3Client.builder()
                .credentialsProvider(awsProvider)
                .endpointOverride(URI.create(s3StorageConfigData.getServiceEndpoint()))
                .region(Region.of(s3StorageConfigData.getSigningRegion()))
                .build();
    }

    @Override
    public void close() {
        s3Client.close();
    }
}
