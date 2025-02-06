package ru.improve.openfy.api.dto.albums;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Data
@Builder
@Jacksonized
public class SelectAlbumResponse {

    private int id;

    private String name;

    private int artistId;

    private String coverUrl;

    private LocalDate uploadDate;
}
