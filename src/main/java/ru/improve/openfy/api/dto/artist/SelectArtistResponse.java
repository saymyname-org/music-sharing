package ru.improve.openfy.api.dto.artist;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Data
@Builder
@Jacksonized
public class SelectArtistResponse {

    private int id;

    private String name;

    private String coverUrl;

    private LocalDate uploadDate;
}
