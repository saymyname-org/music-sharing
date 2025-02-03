package ru.improve.openfy.api.dto.track;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class SelectTrackRequest {

    @NotNull
    private String name;

    @NotNull
    @Min(0)
    private int pageNumber;

    @NotNull
    @Min(0)
    private int itemsPerPage;
}
