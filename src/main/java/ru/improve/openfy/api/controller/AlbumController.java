package ru.improve.openfy.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.openfy.api.dto.albums.SelectAlbumsRequest;
import ru.improve.openfy.core.service.AlbumService;

import static ru.improve.openfy.api.Paths.ALBUM;
import static ru.improve.openfy.api.Paths.ALL;
import static ru.improve.openfy.api.Paths.SELECT;

@RequiredArgsConstructor
@RestController
@RequestMapping("/albums")
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping(ALL)
    public void getAllAlbums(@RequestParam int page,
                             @RequestParam int itemsPerPage) {


    }

    @GetMapping(SELECT)
    public void getAlbumsWithParameters(@Valid @RequestBody SelectAlbumsRequest selectAlbumsRequest) {

    }

    @GetMapping(ALBUM)
    public void getAlbumsByArtist() {

    }
}
