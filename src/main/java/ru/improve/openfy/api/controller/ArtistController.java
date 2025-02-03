package ru.improve.openfy.api.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.openfy.api.dto.artist.CreateArtistRequest;
import ru.improve.openfy.api.dto.artist.CreateArtistResponse;
import ru.improve.openfy.api.dto.artist.PatchArtistRequest;
import ru.improve.openfy.api.dto.artist.SelectArtistRequest;
import ru.improve.openfy.api.dto.artist.SelectArtistResponse;
import ru.improve.openfy.core.service.ArtistService;

import java.util.List;

import static ru.improve.openfy.api.Paths.ALL;
import static ru.improve.openfy.api.Paths.ARTISTS;
import static ru.improve.openfy.api.Paths.SELECT;

@RequiredArgsConstructor
@RestController
@RequestMapping(ARTISTS)
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping(SELECT + ALL)
    public ResponseEntity<List<SelectArtistResponse>> findAllArtists(@RequestParam @Min(0) int page,
                                                                     @RequestParam @Min(0) int itemsPerPage) {

        List<SelectArtistResponse> selectArtistResponseList = artistService.getAllArtist(page, itemsPerPage);
        return new ResponseEntity<>(selectArtistResponseList, HttpStatus.OK);
    }

    @GetMapping(SELECT)
    public ResponseEntity<List<SelectArtistResponse>> findArtistsWithParameters(@Valid @RequestBody SelectArtistRequest selectArtistRequest) {
        List<SelectArtistResponse> selectArtistResponseList = artistService.getArtistWithParameters(selectArtistRequest);
        return new ResponseEntity<>(selectArtistResponseList, HttpStatus.OK);
    }

    @PostMapping
    public CreateArtistResponse createArtist(@Valid @ModelAttribute CreateArtistRequest createArtistRequest) {
        return artistService.createArtist(createArtistRequest);
    }

    @PatchMapping()
    public ResponseEntity<String> patchArtist(@Valid @RequestBody PatchArtistRequest patchArtistRequest) {
        return new ResponseEntity<>("nothing happened", HttpStatus.OK);
    }
}
