package ru.improve.openfy.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.openfy.api.dto.searching.SearchTrackRequest;
import ru.improve.openfy.api.dto.searching.SearchTrackResponse;
import ru.improve.openfy.core.service.SearchingService;

import java.util.List;

import static ru.improve.openfy.api.Paths.SEARCH;

@RequiredArgsConstructor
@RestController
@RequestMapping(SEARCH)
public class SearchController {

    private final SearchingService searchingService;

    @GetMapping
    public ResponseEntity<List<SearchTrackResponse>> searchMaterials(@Valid @RequestBody SearchTrackRequest searchTrackRequest) {

        List<SearchTrackResponse> searchTrackResponseList = searchingService.findMaterials(searchTrackRequest);
        return new ResponseEntity(searchTrackResponseList, HttpStatus.OK);
    }
}