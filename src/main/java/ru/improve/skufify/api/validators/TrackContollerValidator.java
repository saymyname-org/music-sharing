package ru.improve.skufify.api.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public interface TrackContollerValidator extends Validator {

    boolean supports(Class<?> clazz);

    void validate(Object target, Errors errors);
}
