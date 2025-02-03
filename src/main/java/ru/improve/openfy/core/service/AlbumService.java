package ru.improve.openfy.core.service;

import ru.improve.openfy.api.dto.albums.CreateAlbumRequest;
import ru.improve.openfy.api.dto.albums.CreateAlbumResponse;
import ru.improve.openfy.api.dto.albums.SelectAlbumResponse;
import ru.improve.openfy.api.dto.albums.SelectAlbumsRequest;
import ru.improve.openfy.core.models.Album;

import java.util.List;

public interface AlbumService {

    List<SelectAlbumResponse> getAllAlbumsWithParameters(int pageNumber, int itemsPerPage);

    List<SelectAlbumResponse> getAllAlbumsWithParameters(SelectAlbumsRequest selectAlbumsRequest);

    List<SelectAlbumResponse> getAllAlbumsByArtistId(int artistId);

    Album getAlbumById(int id);

    CreateAlbumResponse createAlbum(CreateAlbumRequest createAlbumRequest);
}
