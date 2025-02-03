package ru.improve.openfy.api.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(1),

    ALREADY_EXIST(2),

    NOT_FOUND(3),

    UNAUTHORIZED(4),

    SESSION_IS_OVER(5),

//    ILLEGAL_DTO_VALUE(6),

    ILLEGAL_VALUE(7);

    private final int code;
}
