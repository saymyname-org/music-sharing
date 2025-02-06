package ru.improve.openfy.api.dto.track;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class DownloadTrackResponse {

    private String downloadLink;
}
