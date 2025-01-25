package ru.improve.openfy.core.storage;

import lombok.Getter;
import ru.improve.openfy.core.configuration.storage.S3StorageConfigData;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;

@Getter
public class S3StoragePresignerWrapper implements AutoCloseable {

    private final S3StorageConfigData s3StorageConfigData;

    private S3Presigner s3Presigner;

    public S3StoragePresignerWrapper(S3StorageConfigData s3StorageConfigData) {
        this.s3StorageConfigData = s3StorageConfigData;

        AwsCredentials awsCredentials = AwsBasicCredentials.create(
                s3StorageConfigData.getS3AccessKey(), s3StorageConfigData.getS3SecretKey());
        AwsCredentialsProvider awsProvider = StaticCredentialsProvider.create(awsCredentials);

        s3Presigner = S3Presigner.builder()
                .credentialsProvider(awsProvider)
                .endpointOverride(URI.create(s3StorageConfigData.getServiceEndpoint()))
                .region(Region.of(s3StorageConfigData.getSigningRegion()))
                .build();
    }

    @Override
    public void close() {
        s3Presigner.close();
    }
}
