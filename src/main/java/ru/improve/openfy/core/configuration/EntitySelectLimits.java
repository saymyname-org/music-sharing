package ru.improve.openfy.core.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "select-limits", ignoreUnknownFields = false)
public class EntitySelectLimits {

    private int artistsPerPage;

    private int albumsPerPage;

    private int tracksPerPage;
}
