package ru.improve.openfy.api.validators.imp;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.improve.openfy.api.dto.searching.SearchTrackRequest;
import ru.improve.openfy.api.validators.SearchControllerDtoValidator;
import ru.improve.openfy.api.validators.ServiceDtoValidator;

@Component
public class SearchControllerDtoValidatorImp extends ServiceDtoValidator implements SearchControllerDtoValidator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(SearchTrackRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        createAndThrowException(errors);
    }
}
