package ru.improve.openfy.api.dto.artist;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class SelectArtistRequest {

    @NotNull
    private String name;

    @NotNull
    @Min(0)
    private int pageNumber;

    @NotNull
    @Min(1)
    private int itemsPerPage;
}
