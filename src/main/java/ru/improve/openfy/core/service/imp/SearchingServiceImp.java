package ru.improve.openfy.core.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.improve.openfy.api.dto.searching.SearchTrackRequest;
import ru.improve.openfy.core.dao.imp.SearchingDao;
import ru.improve.openfy.core.models.Track;
import ru.improve.openfy.core.service.SearchingService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchingServiceImp implements SearchingService {

    private SearchingDao searchingDao;

    @Override
    public void findMaterials(SearchTrackRequest searchTrackRequest) {
        String request = searchTrackRequest.getSearchRequest();
        List<Track> tracks = searchingDao.findMaterials(request);
        System.out.println(tracks);
    }
}
