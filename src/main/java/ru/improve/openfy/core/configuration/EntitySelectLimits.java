package ru.improve.openfy.core.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "select-limits", ignoreUnknownFields = false)
public class EntitySelectLimits {

    public static final int WITHOUT_LIMIT_CONSTANT = -1;

    public static final int MAX_SELECT_ITEMS_NUMBER = 50;

    private int artistsPerPage;

    private int albumsPerPage;

    private int tracksPerPage;
}
