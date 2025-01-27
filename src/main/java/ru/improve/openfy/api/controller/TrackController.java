package ru.improve.openfy.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.openfy.api.dto.searching.DownloadTrackResponse;
import ru.improve.openfy.api.dto.upload.UploadTrackRequest;
import ru.improve.openfy.api.validators.TrackControllerDtoValidator;
import ru.improve.openfy.core.service.TrackService;

import static ru.improve.openfy.api.Paths.DOWNLOAD;
import static ru.improve.openfy.api.Paths.TRACK;
import static ru.improve.openfy.api.Paths.UPLOAD;

@RestController
@RequiredArgsConstructor
@RequestMapping(TRACK)
public class TrackController {

    private final TrackControllerDtoValidator trackControllerDtoValidator;

    private final TrackService trackService;

    @GetMapping(DOWNLOAD)
    public ResponseEntity<DownloadTrackResponse> getDownloadTrackLink(@RequestParam("track") String trackHash) {
        DownloadTrackResponse downloadTrackResponse = trackService.getTrackDownloadLink(trackHash);
        return new ResponseEntity<>(downloadTrackResponse, HttpStatus.OK);
    }

    @PostMapping(UPLOAD)
    public void uploadTrack(@Valid @ModelAttribute UploadTrackRequest uploadTrackRequest,
                            BindingResult bindingResult) {

        trackControllerDtoValidator.validate(uploadTrackRequest, bindingResult);
        trackService.uploadTrack(uploadTrackRequest);
    }
}
