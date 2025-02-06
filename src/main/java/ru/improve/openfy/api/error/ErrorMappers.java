package ru.improve.openfy.api.error;

import lombok.experimental.UtilityClass;
import org.springframework.validation.ObjectError;

import java.util.List;

@UtilityClass
public class ErrorMappers {

    public static String toMessage(List<ObjectError> errors) {
//        HashMap<>
//        errors.stream()
//                .forEach(error -> {
//
//                });
        return "";
    }
}
