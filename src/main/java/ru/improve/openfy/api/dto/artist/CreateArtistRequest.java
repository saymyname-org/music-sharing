package ru.improve.openfy.api.dto.artist;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@Jacksonized
public class CreateArtistRequest {

    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    private MultipartFile cover;
}
