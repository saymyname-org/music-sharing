package ru.improve.openfy.api.dto.artist;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class CreateArtistResponse {

    private int id;
}
