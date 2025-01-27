package ru.improve.openfy.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.openfy.api.dto.artist.CreateArtistRequest;

import static ru.improve.openfy.api.Paths.ALL;
import static ru.improve.openfy.api.Paths.ARTIST;
import static ru.improve.openfy.api.Paths.SELECT;

@RequiredArgsConstructor
@RestController
@RequestMapping(ARTIST)
public class ArtistController {

    @GetMapping(ALL)
    public void findAllArtists(@PathVariable("limit") int limit) {

    }

    @GetMapping(SELECT)
    public void findArtistsWithParameters(@PathVariable("request") String name) {

    }

    @PostMapping
    public void createArtist(@ModelAttribute CreateArtistRequest createArtistRequest) {

    }
}
