package ru.improve.openfy.api.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public interface SearchControllerDtoValidator extends Validator {

    boolean supports(Class<?> clazz);

    void validate(Object target, Errors errors);
}
