package ru.improve.openfy.core.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.improve.openfy.api.dto.albums.SelectAlbumResponse;
import ru.improve.openfy.api.dto.albums.SelectAlbumsRequest;
import ru.improve.openfy.core.configuration.EntitySelectLimits;
import ru.improve.openfy.core.mappers.AlbumMapper;
import ru.improve.openfy.core.service.AlbumService;
import ru.improve.openfy.repositories.AlbumRepository;

import java.util.List;

import static ru.improve.openfy.core.configuration.EntitySelectLimits.WITHOUT_LIMIT_CONSTANT;

@RequiredArgsConstructor
@Service
public class AlbumServiceImp implements AlbumService {

    private final AlbumRepository albumRepository;

    private final AlbumMapper albumMapper;

    private final EntitySelectLimits selectLimits;

    public List<SelectAlbumResponse> getAllAlbums(int pageNumber, int itemsPerPage) {
        if (itemsPerPage == WITHOUT_LIMIT_CONSTANT) {
            itemsPerPage = selectLimits.getAlbumsPerPage();
        }

        Pageable page = PageRequest.of(
                pageNumber,
                itemsPerPage,
                Sort.by("name"));

        return albumRepository.findAll(page).get()
                .map(albumMapper::toSelectAlbumsResponse)
                .toList();
    }

    public List<SelectAlbumResponse> getAllAlbums(SelectAlbumsRequest selectAlbumsRequest) {
        int itemsPerPage = selectAlbumsRequest.getItemsPerPage();
        if (itemsPerPage == WITHOUT_LIMIT_CONSTANT) {
            itemsPerPage = selectLimits.getAlbumsPerPage();
        }

        Pageable page = PageRequest.of(
                selectAlbumsRequest.getPageNumber(),
                itemsPerPage,
                Sort.by("name"));

        return albumRepository.findAll(page).get()
                .map(albumMapper::toSelectAlbumsResponse)
                .toList();
    }
}
