package ru.improve.openfy.core.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.improve.openfy.core.service.ArtistService;
import ru.improve.openfy.repositories.ArtistRepository;

@RequiredArgsConstructor
@Service
public class ArtistServiceImp implements ArtistService {

    private final ArtistRepository artistRepository;


}
