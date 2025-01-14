package ru.improve.openfy.api.dto.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadTrackRequest {

    private String trackName;

    private String authorName;

    private int size;
}
