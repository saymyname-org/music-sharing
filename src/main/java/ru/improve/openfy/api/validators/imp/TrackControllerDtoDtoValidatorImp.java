package ru.improve.openfy.api.validators.imp;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.improve.openfy.api.dto.upload.UploadTrackRequest;
import ru.improve.openfy.api.validators.ServiceDtoValidator;
import ru.improve.openfy.api.validators.TrackControllerDtoValidator;

@Component
public class TrackControllerDtoDtoValidatorImp extends ServiceDtoValidator implements TrackControllerDtoValidator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UploadTrackRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        createAndThrowException(errors);
    }
}
