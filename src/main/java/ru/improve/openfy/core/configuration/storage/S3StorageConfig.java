package ru.improve.openfy.core.configuration.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.endpoints.Endpoint;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.endpoints.S3EndpointParams;
import software.amazon.awssdk.services.s3.endpoints.S3EndpointProvider;

@Configuration
@RequiredArgsConstructor
public class S3StorageConfig {

    private final S3StorageConfigData s3StorageConfigData;

    @Value("${openfy.s3.access_key}")
    private String s3AccessKey;

    @Value("${openfy.s3.private_key}")
    private String s3SecretKey;

    @Bean
    public S3Client s3Client() {
        S3EndpointParams s3EndpointParams = S3EndpointParams.builder()
                .endpoint(s3StorageConfigData.getServiceEndpoint())
                .region(Region.of(s3StorageConfigData.getSigningRegion()))
                .build();

        Endpoint s3Endpoint = S3EndpointProvider
                .defaultProvider()
                .resolveEndpoint(s3EndpointParams).join();


        AwsCredentials awsCredentials = AwsBasicCredentials.create(s3AccessKey, s3SecretKey);
        AwsCredentialsProvider awsProvider = StaticCredentialsProvider.create(awsCredentials);

        return S3Client.builder()
                .credentialsProvider(awsProvider)
                .endpointOverride(s3Endpoint.url())
                .forcePathStyle(true)
                .build();
    }
}
