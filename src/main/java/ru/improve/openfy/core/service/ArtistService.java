package ru.improve.openfy.core.service;

import ru.improve.openfy.api.dto.artist.CreateArtistRequest;
import ru.improve.openfy.api.dto.artist.CreateArtistResponse;
import ru.improve.openfy.api.dto.artist.SelectArtistRequest;
import ru.improve.openfy.api.dto.artist.SelectArtistResponse;

import java.util.List;

public interface ArtistService {

    List<SelectArtistResponse> getAllArtist(int pageNumber, int itemsPerPage);

    List<SelectArtistResponse> getArtistWithParameters(SelectArtistRequest selectArtistRequest);

    CreateArtistResponse createArtist(CreateArtistRequest createArtistRequest);
}
