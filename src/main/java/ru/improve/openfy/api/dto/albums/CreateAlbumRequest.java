package ru.improve.openfy.api.dto.albums;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@Jacksonized
public class CreateAlbumRequest {

    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    @NotNull
    @Min(1)
    private int artistId;

    private MultipartFile cover;
}
