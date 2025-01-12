package ru.improve.skufify.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.skufify.api.dto.upload.UploadTrackRequest;
import ru.improve.skufify.api.validators.MusicValidator;

import static ru.improve.skufify.api.Paths.MUSIC;
import static ru.improve.skufify.api.Paths.UPLOAD;

@RestController
@RequiredArgsConstructor
@RequestMapping(MUSIC)
public class MusicController {

    private final MusicValidator musicValidator;

    @PostMapping(UPLOAD)
    public void uploadTrack(@RequestBody @Valid UploadTrackRequest uploadTrackRequest,
                            BindingResult bindingResult) {

        musicValidator.validate(uploadTrackRequest, bindingResult);


    }
}
