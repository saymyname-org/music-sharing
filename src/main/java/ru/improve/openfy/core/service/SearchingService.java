package ru.improve.openfy.core.service;

import ru.improve.openfy.api.dto.searching.SearchTrackRequest;
import ru.improve.openfy.api.dto.searching.SearchTrackResponse;

import java.util.List;

public interface SearchingService {

    List<SearchTrackResponse> findMaterials(SearchTrackRequest searchTrackRequest);
}
