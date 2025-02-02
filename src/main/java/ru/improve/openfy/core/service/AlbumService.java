package ru.improve.openfy.core.service;

import ru.improve.openfy.api.dto.albums.SelectAlbumResponse;
import ru.improve.openfy.api.dto.albums.SelectAlbumsRequest;

import java.util.List;

public interface AlbumService {

    List<SelectAlbumResponse> getAllAlbumsWithParameters(int pageNumber, int itemsPerPage);

    List<SelectAlbumResponse> getAllAlbumsWithParameters(SelectAlbumsRequest selectAlbumsRequest);

    List<SelectAlbumResponse> getAllAlbumsByArtistId(int artistId);
}
