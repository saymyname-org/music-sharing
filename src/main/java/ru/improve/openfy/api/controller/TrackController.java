package ru.improve.openfy.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.openfy.api.dto.upload.UploadTrackRequest;
import ru.improve.openfy.api.validators.TrackContollerValidator;
import ru.improve.openfy.core.service.S3ClientService;

import static ru.improve.openfy.api.Paths.TRACK;
import static ru.improve.openfy.api.Paths.UPLOAD;

@RestController
@RequiredArgsConstructor
@RequestMapping(TRACK)
public class TrackController {

    private final TrackContollerValidator trackContollerValidator;

    private final S3ClientService s3ClientService;

    @PostMapping(UPLOAD)
    public String uploadTrack(@Valid @ModelAttribute UploadTrackRequest uploadTrackRequest,
                              BindingResult bindingResult) {

        trackContollerValidator.validate(uploadTrackRequest, bindingResult);

        s3ClientService.saveTrack(uploadTrackRequest);

        return "something";
    }
}
