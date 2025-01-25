package ru.improve.openfy.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.openfy.api.dto.searching.SearchTrackRequest;
import ru.improve.openfy.api.dto.upload.SearchTrackResponse;
import ru.improve.openfy.api.validators.SearchControllerDtoValidator;
import ru.improve.openfy.core.service.SearchingService;

import static ru.improve.openfy.api.Paths.SEARCH;

@RequiredArgsConstructor
@RestController(SEARCH)
public class SearchController {

    private final SearchControllerDtoValidator searchControllerDtoValidator;

    private final SearchingService searchingService;

    @GetMapping
    public ResponseEntity<SearchTrackResponse> searchMaterials(@Valid @RequestBody SearchTrackRequest searchTrackRequest,
                                                               BindingResult bindingResult) {

        searchControllerDtoValidator.validate(searchTrackRequest, bindingResult);

        searchingService.findMaterials(searchTrackRequest);
        return null;
    }
}