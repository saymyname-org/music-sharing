package ru.improve.skufify.api.validators.imp;

import org.springframework.validation.Errors;
import ru.improve.skufify.api.dto.upload.UploadTrackRequest;
import ru.improve.skufify.api.validators.MusicSharingDtoValidator;
import ru.improve.skufify.api.validators.MusicValidator;

public class MusicValidatorImp extends MusicSharingDtoValidator implements MusicValidator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UploadTrackRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        createAndThrowException(errors);
    }
}
