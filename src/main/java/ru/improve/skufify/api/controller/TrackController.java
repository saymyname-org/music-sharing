package ru.improve.skufify.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.skufify.api.dto.upload.UploadTrackRequest;
import ru.improve.skufify.api.validators.TrackContollerValidator;

import static ru.improve.skufify.api.Paths.TRACK;
import static ru.improve.skufify.api.Paths.UPLOAD;

@RestController
@RequiredArgsConstructor
@RequestMapping(TRACK)
public class TrackController {

    private final TrackContollerValidator trackContollerValidator;

    @PostMapping(UPLOAD)
    public void uploadTrack(@RequestBody @Valid UploadTrackRequest uploadTrackRequest,
                            BindingResult bindingResult) {

        trackContollerValidator.validate(uploadTrackRequest, bindingResult);


    }
}
