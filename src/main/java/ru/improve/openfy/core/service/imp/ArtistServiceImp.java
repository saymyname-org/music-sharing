package ru.improve.openfy.core.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.improve.openfy.api.dto.artist.SelectArtistRequest;
import ru.improve.openfy.api.dto.artist.SelectArtistResponse;
import ru.improve.openfy.core.service.ArtistService;
import ru.improve.openfy.repositories.ArtistRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ArtistServiceImp implements ArtistService {

    private final ArtistRepository artistRepository;

    public List<SelectArtistResponse> getAllArtist(int limit) {
//        artistRepository.fin
        return null;
    }

    public List<SelectArtistResponse> getArtistWithParameters(SelectArtistRequest selectArtistRequest) {
        return null;
    }
}
