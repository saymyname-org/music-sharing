package ru.improve.openfy.api.dto.upload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@Jacksonized
public class UploadTrackRequest {

    @NotNull
    @NotBlank
    private String trackName;

    @NotNull
    @NotBlank
    private String authorName;

    @NotNull
    @NotBlank
    private String musicFormat;

    @NotNull
    private MultipartFile file;
}
