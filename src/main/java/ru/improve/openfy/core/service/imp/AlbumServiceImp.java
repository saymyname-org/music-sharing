package ru.improve.openfy.core.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.improve.openfy.api.dto.albums.CreateAlbumRequest;
import ru.improve.openfy.api.dto.albums.CreateAlbumResponse;
import ru.improve.openfy.api.dto.albums.SelectAlbumResponse;
import ru.improve.openfy.api.dto.albums.SelectAlbumsRequest;
import ru.improve.openfy.api.error.ErrorCode;
import ru.improve.openfy.api.error.ServiceException;
import ru.improve.openfy.core.configuration.EntitySelectLimits;
import ru.improve.openfy.core.mappers.AlbumMapper;
import ru.improve.openfy.core.models.Album;
import ru.improve.openfy.core.models.Artist;
import ru.improve.openfy.core.security.UserPrincipal;
import ru.improve.openfy.core.service.AlbumService;
import ru.improve.openfy.repositories.AlbumRepository;
import ru.improve.openfy.repositories.ArtistRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static ru.improve.openfy.api.error.ErrorCode.NOT_FOUND;

@RequiredArgsConstructor
@Service
public class AlbumServiceImp implements AlbumService {

    private final ArtistRepository artistRepository;

    private final AlbumRepository albumRepository;

    private final AlbumMapper albumMapper;

    private final EntitySelectLimits selectLimits;

    @Transactional
    @Override
    public List<SelectAlbumResponse> getAllAlbumsWithParameters(int pageNumber, int itemsPerPage) {
        if (itemsPerPage > selectLimits.getAlbumsPerPage()) {
            itemsPerPage = selectLimits.getAlbumsPerPage();
        }

        Pageable page = PageRequest.of(
                pageNumber,
                itemsPerPage,
                Sort.by("name"));

        Page<Album> albumsPage = albumRepository.findAll(page);
        return albumStreamToListSelectAlbumResponseMap(albumsPage.get());
    }

    @Transactional
    @Override
    public List<SelectAlbumResponse> getAllAlbumsWithParameters(SelectAlbumsRequest selectAlbumsRequest) {
        int itemsPerPage = selectAlbumsRequest.getItemsPerPage();
        if (itemsPerPage > selectLimits.getAlbumsPerPage()) {
            itemsPerPage = selectLimits.getAlbumsPerPage();
        }

        Pageable page = PageRequest.of(
                selectAlbumsRequest.getPageNumber(),
                itemsPerPage,
                Sort.by("name"));

        List<Album> albums = albumRepository.findAllByNameContainingIgnoreCase(selectAlbumsRequest.getName(), page);
        return albumStreamToListSelectAlbumResponseMap(albums.stream());
    }

    @Transactional
    @Override
    public List<SelectAlbumResponse> getAllAlbumsByArtistId(int artistId) {
        if (!artistRepository.existsById(artistId)) {
            throw new ServiceException(NOT_FOUND, "artistId");
        }
        List<Album> albums = albumRepository.findAllByArtist_Id(artistId);
        return albumStreamToListSelectAlbumResponseMap(albums.stream());
    }

    @Transactional
    @Override
    public Album getAlbumById(int id) {
        return albumRepository.findById(id)
                .orElseThrow(() -> new ServiceException(NOT_FOUND, "album", "id"));
    }

    @Transactional
    @Override
    public CreateAlbumResponse createAlbum(CreateAlbumRequest createAlbumRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        Artist artist = artistRepository.findById(createAlbumRequest.getArtistId())
                .orElseThrow(() -> new ServiceException(NOT_FOUND));

        Album album = Album.builder()
                .name(createAlbumRequest.getName())
                .artist(artist)
                .coverUrl(null)
                .uploaderId(principal.getId())
                .uploadDate(LocalDate.now())
                .build();

        try {
            albumRepository.save(album);
        } catch (DataIntegrityViolationException ex) {
            throw new ServiceException(ErrorCode.ALREADY_EXIST, "name");
        }

        return CreateAlbumResponse.builder()
                .id(album.getId())
                .build();
    }

    private List<SelectAlbumResponse> albumStreamToListSelectAlbumResponseMap(Stream<Album> albumStream) {
        return albumStream
                .map(album -> {
                    SelectAlbumResponse selectAlbumResponse = albumMapper.toSelectAlbumsResponse(album);
                    selectAlbumResponse.setArtistId(album.getArtist().getId());
                    return selectAlbumResponse;
                })
                .toList();
    }
}
