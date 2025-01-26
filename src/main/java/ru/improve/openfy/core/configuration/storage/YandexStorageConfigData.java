package ru.improve.openfy.core.configuration.storage;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "yandex-storage.s3", ignoreUnknownFields = false)
public class YandexStorageConfigData {

    private String serviceEndpoint;

    private String signingRegion;

    private String musicBucketName;

    @Value("${openfy.s3.access_key}")
    private String s3AccessKey;

    @Value("${openfy.s3.private_key}")
    private String s3SecretKey;
}
