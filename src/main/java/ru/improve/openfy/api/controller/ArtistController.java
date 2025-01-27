package ru.improve.openfy.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.openfy.api.dto.artist.CreateArtistRequest;
import ru.improve.openfy.api.dto.artist.SelectArtistRequest;
import ru.improve.openfy.api.validators.ArtistControllerDtoValidator;
import ru.improve.openfy.core.service.ArtistService;

import static ru.improve.openfy.api.Paths.ALL;
import static ru.improve.openfy.api.Paths.ARTIST;
import static ru.improve.openfy.api.Paths.LIMIT;
import static ru.improve.openfy.api.Paths.SELECT;

@RequiredArgsConstructor
@RestController
@RequestMapping(ARTIST)
public class ArtistController {

    private final ArtistControllerDtoValidator artistControllerDtoValidator;

    private final ArtistService artistService;

    @GetMapping(ALL + LIMIT)
    public void findAllArtists(@PathVariable ("limit") int limit) {

    }

    @GetMapping(SELECT)
    public void findArtistsWithParameters(@Valid @RequestBody SelectArtistRequest selectArtistRequest,
                                          BindingResult bindingResult) {

        artistControllerDtoValidator.validate(selectArtistRequest, bindingResult);

    }

    @PostMapping
    public void createArtist(@ModelAttribute CreateArtistRequest createArtistRequest) {

    }
}
