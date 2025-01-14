package ru.improve.openfy.api.validators.imp;

import org.springframework.validation.Errors;
import ru.improve.openfy.api.dto.upload.UploadTrackRequest;
import ru.improve.openfy.api.validators.TrackSharingDtoValidator;
import ru.improve.openfy.api.validators.TrackContollerValidator;

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
