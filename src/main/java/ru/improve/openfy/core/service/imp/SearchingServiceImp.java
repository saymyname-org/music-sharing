package ru.improve.openfy.core.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.improve.openfy.api.dto.searching.SearchTrackRequest;
import ru.improve.openfy.api.dto.searching.SearchTrackResponse;
import ru.improve.openfy.core.configuration.YandexStorageConfigData;
import ru.improve.openfy.core.dao.SearchingDao;
import ru.improve.openfy.core.mappers.SearchTrackMapper;
import ru.improve.openfy.core.models.Track;
import ru.improve.openfy.core.service.S3StorageService;
import ru.improve.openfy.core.service.SearchingService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchingServiceImp implements SearchingService {

    private final SearchingDao searchingDao;

    private final SearchTrackMapper searchTrackMapper;

    private final S3StorageService s3StorageService;

    private final YandexStorageConfigData yandexStorageConfigData;

    @Override
    public List<SearchTrackResponse> findMaterials(SearchTrackRequest searchTrackRequest) {
        String request = searchTrackRequest.getSearchRequest();
        List<Track> tracks = searchingDao.findMaterials(request);

        return tracks.stream()
                .map(track -> {
                    SearchTrackResponse searchTrackResponse = searchTrackMapper.mapToSearchTrackResponse(track);
                    return searchTrackResponse;
                })
                .toList();
    }
}
