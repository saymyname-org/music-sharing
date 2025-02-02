package ru.improve.openfy.core.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.improve.openfy.api.dto.artist.CreateArtistRequest;
import ru.improve.openfy.api.dto.artist.CreateArtistResponse;
import ru.improve.openfy.api.dto.artist.SelectArtistRequest;
import ru.improve.openfy.api.dto.artist.SelectArtistResponse;
import ru.improve.openfy.core.configuration.EntitySelectLimits;
import ru.improve.openfy.core.dao.ArtistDao;
import ru.improve.openfy.core.mappers.ArtistMapper;
import ru.improve.openfy.core.models.Artist;
import ru.improve.openfy.core.service.ArtistService;
import ru.improve.openfy.repositories.ArtistRepository;

import java.util.List;

import static ru.improve.openfy.core.configuration.EntitySelectLimits.WITHOUT_LIMIT_CONSTANT;

@RequiredArgsConstructor
@Service
public class ArtistServiceImp implements ArtistService {

    private final ArtistRepository artistRepository;

    private final ArtistDao artistDao;

    private final ArtistMapper artistMapper;

    private final EntitySelectLimits selectLimits;

    @Transactional
    @Override
    public List<SelectArtistResponse> getAllArtist(int pageNumber, int itemsPerPage) {
        if (itemsPerPage == WITHOUT_LIMIT_CONSTANT) {
            itemsPerPage = selectLimits.getArtistsPerPage();
        }

        Pageable page = PageRequest.of(
                pageNumber,
                itemsPerPage,
                Sort.by("name"));

        Page<Artist> artistPage = artistRepository.findAll(page);
        return artistPage.get()
                .map(artistMapper::toSearchArtistResponse)
                .toList();
    }

    @Transactional
    @Override
    public List<SelectArtistResponse> getArtistWithParameters(SelectArtistRequest selectArtistRequest) {
        int itemsPerPage = selectArtistRequest.getItemsPerPage();
        if (itemsPerPage == WITHOUT_LIMIT_CONSTANT) {
            itemsPerPage = selectLimits.getArtistsPerPage();
        }

        Pageable page = PageRequest.of(
                selectArtistRequest.getPageNumber(),
                itemsPerPage,
                Sort.by("name"));

//        List<Artist> artistList = artistRepository.findAllByName(selectArtistRequest.getName(), page);
        List<Artist> artistList = artistDao.findAllArtistsByName(selectArtistRequest.getName());
        return artistList.stream()
                .map(artistMapper::toSearchArtistResponse)
                .toList();
    }

    @Transactional
    @Override
    public CreateArtistResponse createArtist(CreateArtistRequest createArtistRequest) {
        Artist artist = artistMapper.toArtist(createArtistRequest);
        artistRepository.save(artist);
        return CreateArtistResponse.builder()
                .id(artist.getId())
                .build();
    }
}
