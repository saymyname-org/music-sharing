package ru.improve.openfy.api.dto.upload;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadTrackRequest {

    @NotNull
    @NotBlank
    private String trackName;

    @NotNull
    @NotBlank
    private String authorName;

    @NotNull
    @Max(100 * 1024 * 1024)
    private int size;
}
