package ru.improve.openfy.api.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import ru.improve.openfy.api.error.ServiceException;

import java.util.HashSet;
import java.util.Set;

import static ru.improve.openfy.api.error.ErrorCode.ILLEGAL_DTO_VALUE;

public abstract class ServiceDtoValidator {

    protected void createAndThrowException(Errors errors) {
        if (errors.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            Set<String> fieldsWithErrors = new HashSet<>();
            for (FieldError error : errors.getFieldErrors()) {
                fieldsWithErrors.add(error.getField());
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append("; ");
            }

            throw new ServiceException(ILLEGAL_DTO_VALUE, null, new String[]{
                    String.join(" ", fieldsWithErrors)
            });
        }
    }
}
