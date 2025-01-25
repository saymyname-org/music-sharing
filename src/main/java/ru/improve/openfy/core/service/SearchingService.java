package ru.improve.openfy.core.service;

import ru.improve.openfy.api.dto.searching.SearchTrackRequest;

public interface SearchingService {

    void findMaterials(SearchTrackRequest searchTrackRequest);
}
