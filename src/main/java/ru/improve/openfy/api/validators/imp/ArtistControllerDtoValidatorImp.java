package ru.improve.openfy.api.validators.imp;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.improve.openfy.api.dto.artist.SelectArtistRequest;
import ru.improve.openfy.api.validators.ArtistControllerDtoValidator;
import ru.improve.openfy.api.validators.ServiceDtoValidator;

@Component
public class ArtistControllerDtoValidatorImp extends ServiceDtoValidator implements ArtistControllerDtoValidator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(SelectArtistRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        createAndThrowException(errors);
    }
}
