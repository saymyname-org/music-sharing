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

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static ru.improve.openfy.api.error.ErrorCode.NOT_FOUND;

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
        return artistStreamToSelectArtistResponseListMap(artistsPage.get());
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
        return artistStreamToSelectArtistResponseListMap(artists.stream());
    }

    @Transactional
    @Override
    public Artist getArtistById(int id) {
        return artistRepository.findById(id)
                .orElseThrow(() -> new ServiceException(NOT_FOUND, "artist", "id"));
    }

    @Transactional
    @Override
    public CreateArtistResponse createArtist(CreateArtistRequest createArtistRequest) {
        Artist artist = artistMapper.toArtist(createArtistRequest);
        artist.setUploadDate(LocalDate.now());

        try {
            artistRepository.save(artist);
        } catch (DataIntegrityViolationException ex) {

            /* заменить везде на маппер исключений */
            SQLException psqlException = (SQLException) ex.getCause().getCause();
            String errorCode = psqlException.getSQLState();

            throw new ServiceException(ErrorCode.ALREADY_EXIST, "artist", "name");
        }

        return CreateArtistResponse.builder()
                .id(artist.getId())
                .build();
    }

    private List<SelectArtistResponse> artistStreamToSelectArtistResponseListMap(Stream<Artist> artistStream) {
        return artistStream
                .map(artistMapper::toSearchArtistResponse)
                .toList();
    }
}
