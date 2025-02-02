package ru.improve.openfy.core.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
import ru.improve.openfy.api.error.ErrorCode;
import ru.improve.openfy.api.error.ServiceException;
import ru.improve.openfy.core.configuration.EntitySelectLimits;
import ru.improve.openfy.core.mappers.ArtistMapper;
import ru.improve.openfy.core.models.Artist;
import ru.improve.openfy.core.service.ArtistService;
import ru.improve.openfy.repositories.ArtistRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ArtistServiceImp implements ArtistService {

    private final ArtistRepository artistRepository;

    private final ArtistMapper artistMapper;

    private final EntitySelectLimits selectLimits;

    @Transactional
    @Override
    public List<SelectArtistResponse> getAllArtist(int pageNumber, int itemsPerPage) {
        if (itemsPerPage > selectLimits.getArtistsPerPage()) {
            itemsPerPage = selectLimits.getArtistsPerPage();
        }

        Pageable page = PageRequest.of(
                pageNumber,
                itemsPerPage,
                Sort.by("name"));

        Page<Artist> artistsPage = artistRepository.findAll(page);
        return artistsPage.get()
                .map(artistMapper::toSearchArtistResponse)
                .toList();
    }

    @Transactional
    @Override
    public List<SelectArtistResponse> getArtistWithParameters(SelectArtistRequest selectArtistRequest) {
        int itemsPerPage = selectArtistRequest.getItemsPerPage();
        if (itemsPerPage > selectLimits.getArtistsPerPage()) {
            itemsPerPage = selectLimits.getArtistsPerPage();
        }

        Pageable page = PageRequest.of(
                selectArtistRequest.getPageNumber(),
                itemsPerPage,
                Sort.by("name"));

        List<Artist> artists = artistRepository.findAllByNameContainingIgnoreCase(selectArtistRequest.getName(), page);
        return artists.stream()
                .map(artistMapper::toSearchArtistResponse)
                .toList();
    }

    @Transactional
    @Override
    public CreateArtistResponse createArtist(CreateArtistRequest createArtistRequest) {
        Artist artist = artistMapper.toArtist(createArtistRequest);
        try {
            artistRepository.save(artist);
        } catch (DataIntegrityViolationException ex) {
            throw new ServiceException(ErrorCode.ALREADY_EXIST, new String[]{"name"});
        }
        return CreateArtistResponse.builder()
                .id(artist.getId())
                .build();
    }
}
