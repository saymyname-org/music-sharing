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

    @Transactional
    @Override
    public List<SelectArtistResponse> getAllArtist(int limit) {
        Pageable page = PageRequest.of(0, limit, Sort.by("name"));
        Page<Artist> artistPage = artistRepository.findAll(page);
        return artistPage.get()
                .map(artist -> artistMapper.toSearchArtistResponse(artist))
                .toList();
    }

    @Transactional
    @Override
    public List<SelectArtistResponse> getArtistWithParameters(SelectArtistRequest selectArtistRequest) {
        Pageable page = PageRequest.of(0, selectArtistRequest.getLimit(), Sort.by("name"));
        List<Artist> artistList = artistRepository.findAllByName(selectArtistRequest.getName(), page);
        return artistList.stream()
                .map(artist -> artistMapper.toSearchArtistResponse(artist))
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
