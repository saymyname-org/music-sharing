package ru.improve.openfy.core.validators;

import lombok.experimental.UtilityClass;
import ru.improve.openfy.api.error.ServiceException;

import static ru.improve.openfy.api.error.ErrorCode.ILLEGAL_VALUE;
import static ru.improve.openfy.util.MessageKeys.VALIDATION_CHECK_VALUE_IS_BLANK;
import static ru.improve.openfy.util.MessageKeys.VALIDATION_CHECK_VALUE_IS_NULL;

@UtilityClass
public class Validators {

    public static void checkNotNull(Object value, String valueName) {
        if (value == null) {
            throw new ServiceException(ILLEGAL_VALUE, VALIDATION_CHECK_VALUE_IS_NULL, valueName);
        }
    }

    public static void checkNotBlank(String value, String valueName) {
        checkNotNull(value, valueName);
        if (value.isBlank()) {
            throw new ServiceException(ILLEGAL_VALUE, VALIDATION_CHECK_VALUE_IS_BLANK, valueName);
        }
    }
}
