package ru.improve.openfy.api.dto.albums;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class SelectAlbumsRequest {

    @NotNull
    private String name;

    @NotNull
    @Min(0)
    private int pageNumber;

    @Min(1)
    private int itemsPerPage;
}
