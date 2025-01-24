package ru.improve.openfy.api.dto.searching;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class SearchTrackRequest {

    private String searchRequest;
}
