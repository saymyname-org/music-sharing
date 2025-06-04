package ru.improve.openfy.api.dto.track;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.improve.openfy.core.track.enums.MusicFormat;

@Data
@Builder
@Jacksonized
public class SelectTrackResponse {

    private long id;

    private String name;

    private int artistId;

    private int albumId;

    private MusicFormat format;

    private long size;

    private String hash;

    private long uploaderId;
}
