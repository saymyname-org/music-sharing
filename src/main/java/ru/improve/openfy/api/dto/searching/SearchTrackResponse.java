package ru.improve.openfy.api.dto.searching;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.improve.openfy.core.track.enums.MusicFormat;

@Data
@Builder
@Jacksonized
public class SearchTrackResponse {

    private long id;

    private String name;

    private String authorName;

    private MusicFormat format;

    private long size;

    private String hash;

    private long uploaderId;

    private String link;
}
