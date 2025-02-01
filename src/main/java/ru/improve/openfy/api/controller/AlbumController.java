package ru.improve.openfy.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ru.improve.openfy.api.Paths.ALL;
import static ru.improve.openfy.api.Paths.LIMIT;
import static ru.improve.openfy.api.Paths.SELECT;

@RequiredArgsConstructor
@RestController
@RequestMapping("/albums")
public class AlbumController {

    @GetMapping(ALL + LIMIT)
    public void getAllAlbums() {

    }

    @GetMapping(SELECT)
    public void getAlbumsWithParameters() {

    }

    @GetMapping()
    public void getAlbumsByArtist() {

    }
}
