package ru.improve.openfy.api.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.openfy.api.dto.track.DownloadTrackResponse;
import ru.improve.openfy.api.dto.track.SelectTrackRequest;
import ru.improve.openfy.api.dto.track.SelectTrackResponse;
import ru.improve.openfy.api.dto.track.UploadTrackRequest;
import ru.improve.openfy.core.service.TrackService;

import java.util.List;

import static ru.improve.openfy.api.Paths.ALL;
import static ru.improve.openfy.api.Paths.DOWNLOAD;
import static ru.improve.openfy.api.Paths.SELECT;
import static ru.improve.openfy.api.Paths.TRACK;
import static ru.improve.openfy.api.Paths.UPLOAD;

@RestController
@RequiredArgsConstructor
@RequestMapping(TRACK)
public class TrackController {

    private final TrackService trackService;

    @GetMapping(SELECT + ALL)
    public ResponseEntity<List<SelectTrackResponse>> findAllTracks(@RequestParam @Min(0) int page,
                                                                   @RequestParam @Min(1) int itemsPerPage) {

        List<SelectTrackResponse> tracks = trackService.getAllTracks(page, itemsPerPage);
        return new ResponseEntity<>(tracks, HttpStatus.OK);
    }

    @GetMapping(SELECT)
    public ResponseEntity<List<SelectTrackResponse>> findTracksWithParameters(
            @Valid @RequestBody SelectTrackRequest selectTrackRequest) {

        List<SelectTrackResponse> tracks = trackService.getTracksWithParameters(selectTrackRequest);
        return new ResponseEntity<>(tracks, HttpStatus.OK);
    }

    @GetMapping(DOWNLOAD)
    public ResponseEntity<DownloadTrackResponse> getDownloadTrackLink(@RequestParam("track") String trackHash) {
        DownloadTrackResponse downloadTrackResponse = trackService.getTrackDownloadLink(trackHash);
        return new ResponseEntity<>(downloadTrackResponse, HttpStatus.OK);
    }

    @PostMapping(UPLOAD)
    public ResponseEntity<Void> uploadTrack(@Valid @ModelAttribute UploadTrackRequest uploadTrackRequest) {
        trackService.uploadTrack(uploadTrackRequest);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
