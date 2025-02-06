package ru.improve.openfy.api.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.openfy.api.dto.albums.CreateAlbumRequest;
import ru.improve.openfy.api.dto.albums.CreateAlbumResponse;
import ru.improve.openfy.api.dto.albums.SelectAlbumResponse;
import ru.improve.openfy.api.dto.albums.SelectAlbumsRequest;
import ru.improve.openfy.core.service.AlbumService;

import java.util.List;

import static ru.improve.openfy.api.Paths.ALBUMS;
import static ru.improve.openfy.api.Paths.ALL;
import static ru.improve.openfy.api.Paths.ARTIST;
import static ru.improve.openfy.api.Paths.ID;
import static ru.improve.openfy.api.Paths.SELECT;

@RequiredArgsConstructor
@RestController
@RequestMapping(ALBUMS)
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping(SELECT + ALL)
    public ResponseEntity<List<SelectAlbumResponse>> getAllAlbums(@RequestParam @Min(0) int page,
                                                                  @RequestParam @Min(1) int itemsPerPage) {

        List<SelectAlbumResponse> albums = albumService.getAllAlbumsWithParameters(page, itemsPerPage);
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    @GetMapping(SELECT)
    public ResponseEntity<List<SelectAlbumResponse>> getAlbumsWithParameters(
            @Valid @RequestBody SelectAlbumsRequest selectAlbumsRequest) {

        List<SelectAlbumResponse> albums = albumService.getAllAlbumsWithParameters(selectAlbumsRequest);
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    @GetMapping(ARTIST + ID)
    public ResponseEntity<List<SelectAlbumResponse>> getAlbumsByArtist(@PathVariable("id") int artistId) {
        List<SelectAlbumResponse> albums = albumService.getAllAlbumsByArtistId(artistId);
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CreateAlbumResponse> createAlbum(@Valid @RequestBody CreateAlbumRequest createAlbumRequest) {
        CreateAlbumResponse createAlbumResponse = albumService.createAlbum(createAlbumRequest);
        return new ResponseEntity<>(createAlbumResponse, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<String> patchAlbum() {
        return new ResponseEntity<>("nothing happened", HttpStatus.OK);
    }
}
