package ru.improve.openfy.core.service;

import ru.improve.openfy.api.dto.artist.CreateArtistRequest;
import ru.improve.openfy.api.dto.artist.CreateArtistResponse;
import ru.improve.openfy.api.dto.artist.SelectArtistRequest;
import ru.improve.openfy.api.dto.artist.SelectArtistResponse;
import ru.improve.openfy.core.models.Artist;

import java.util.List;

public interface ArtistService {

    List<SelectArtistResponse> getAllArtist(int pageNumber, int itemsPerPage);

    List<SelectArtistResponse> getArtistWithParameters(SelectArtistRequest selectArtistRequest);

    Artist getArtistById(int id);

    CreateArtistResponse createArtist(CreateArtistRequest createArtistRequest);
}
