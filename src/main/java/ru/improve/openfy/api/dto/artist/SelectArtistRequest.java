package ru.improve.openfy.api.dto.artist;

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
}
