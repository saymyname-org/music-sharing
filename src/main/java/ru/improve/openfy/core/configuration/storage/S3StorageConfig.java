package ru.improve.openfy.core.configuration.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class S3StorageConfig {

    private final S3StorageConfigData s3StorageConfigData;

    /*@Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                s3StorageConfigData.getServiceEndpoint(), s3StorageConfigData.getSigningRegion()
                        )
                )
                .withCredentials(null)
                .build();
    }*/
}
