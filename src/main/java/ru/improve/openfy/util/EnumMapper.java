package ru.improve.openfy.util;

import lombok.experimental.UtilityClass;
import ru.improve.openfy.api.error.ServiceException;

import static ru.improve.openfy.api.error.ErrorCode.ILLEGAL_VALUE;

@UtilityClass
public class EnumMapper {

    public static <T extends Enum<T>> T enumFromString(String key, Class<T> type, String message) {
        key = key.replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase();
        try {
            return key == null ? null : Enum.valueOf(type, key);
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ILLEGAL_VALUE, message);
        }
    }
}
