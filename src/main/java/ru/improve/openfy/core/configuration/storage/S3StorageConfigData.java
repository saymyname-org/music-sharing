package ru.improve.openfy.core.configuration.storage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "storage.s3", ignoreUnknownFields = false)
public class S3StorageConfigData {

    private String serviceEndpoint;

    private String signingRegion;

    private String musicBucketName;
}
