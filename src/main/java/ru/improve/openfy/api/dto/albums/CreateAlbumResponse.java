package ru.improve.openfy.api.dto.albums;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class CreateAlbumResponse {

    private int id;
}
