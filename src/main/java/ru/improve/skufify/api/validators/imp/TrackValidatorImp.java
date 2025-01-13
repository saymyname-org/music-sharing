package ru.improve.skufify.api.validators.imp;

import org.springframework.validation.Errors;
import ru.improve.skufify.api.dto.upload.UploadTrackRequest;
import ru.improve.skufify.api.validators.TrackSharingDtoValidator;
import ru.improve.skufify.api.validators.TrackContollerValidator;

public class TrackValidatorImp extends TrackSharingDtoValidator implements TrackContollerValidator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UploadTrackRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        createAndThrowException(errors);
    }
}
