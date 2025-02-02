package ru.improve.openfy.api.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.openfy.api.dto.albums.SelectAlbumResponse;
import ru.improve.openfy.api.dto.albums.SelectAlbumsRequest;
import ru.improve.openfy.core.service.AlbumService;

import java.util.List;

import static ru.improve.openfy.api.Paths.ALBUMS;
import static ru.improve.openfy.api.Paths.ALL;
import static ru.improve.openfy.api.Paths.ARTIST;
import static ru.improve.openfy.api.Paths.ID;
import static ru.improve.openfy.api.Paths.SELECT;
import static ru.improve.openfy.core.configuration.EntitySelectLimits.MAX_SELECT_ITEMS_NUMBER;
import static ru.improve.openfy.core.configuration.EntitySelectLimits.WITHOUT_LIMIT_CONSTANT;

@RequiredArgsConstructor
@RestController
@RequestMapping(ALBUMS)
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping(ALL)
    public ResponseEntity<List<SelectAlbumResponse>> getAllAlbums(@RequestParam @Min(0) int page,
                                                                  @RequestParam @Min(WITHOUT_LIMIT_CONSTANT) @Max(MAX_SELECT_ITEMS_NUMBER) int itemsPerPage) {

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
}
