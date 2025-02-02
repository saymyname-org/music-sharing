package ru.improve.openfy.api.dto.albums;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import static ru.improve.openfy.core.configuration.EntitySelectLimits.WITHOUT_LIMIT_CONSTANT;

@Data
@Builder
@Jacksonized
public class SelectAlbumsRequest {

    @NotNull
    private String name;

    @NotNull
    @Min(0)
    private int pageNumber;

    @Min(WITHOUT_LIMIT_CONSTANT)
    private int itemsPerPage;
}
