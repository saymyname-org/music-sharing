package ru.improve.openfy.api.error;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

import static ru.improve.openfy.api.error.ErrorCode.ALREADY_EXIST;
import static ru.improve.openfy.api.error.ErrorCode.ILLEGAL_DTO_VALUE;
import static ru.improve.openfy.api.error.ErrorCode.ILLEGAL_VALUE;
import static ru.improve.openfy.api.error.ErrorCode.INTERNAL_SERVER_ERROR;
import static ru.improve.openfy.api.error.ErrorCode.NOT_FOUND;
import static ru.improve.openfy.api.error.ErrorCode.SESSION_IS_OVER;
import static ru.improve.openfy.api.error.ErrorCode.UNAUTHORIZED;
import static ru.improve.openfy.util.MessageKeys.INVALID_AUTHORIZATION;
import static ru.improve.openfy.util.MessageKeys.SESSION_DISABLE;
import static ru.improve.openfy.util.MessageKeys.TITLE_ENTITY_ALREADY_EXIST;
import static ru.improve.openfy.util.MessageKeys.TITLE_ILLEGAL_DTO_VALUE;
import static ru.improve.openfy.util.MessageKeys.TITLE_ILLEGAL_VALUE;
import static ru.improve.openfy.util.MessageKeys.TITLE_INTERNAL_SERVER_ERROR;
import static ru.improve.openfy.util.MessageKeys.TITLE_USER_NOT_FOUND;

@Log4j2
@RequiredArgsConstructor
@RestControllerAdvice
public class ServiceExceptionHandler {

    private static final Map<ErrorCode, String> MESSAGE_KEYS_MAP = Maps.immutableEnumMap(
            Map.of(
                    ILLEGAL_VALUE, TITLE_ILLEGAL_VALUE,
                    ILLEGAL_DTO_VALUE, TITLE_ILLEGAL_DTO_VALUE,
                    ALREADY_EXIST, TITLE_ENTITY_ALREADY_EXIST,
                    NOT_FOUND, TITLE_USER_NOT_FOUND,
                    UNAUTHORIZED, INVALID_AUTHORIZATION,
                    SESSION_IS_OVER, SESSION_DISABLE,
                    INTERNAL_SERVER_ERROR, TITLE_INTERNAL_SERVER_ERROR
            )
    );

    private static final Map<ErrorCode, HttpStatus> HTTP_STATUS_MAP = Maps.immutableEnumMap(
            Map.of(
                    ILLEGAL_VALUE, HttpStatus.BAD_REQUEST,
                    ILLEGAL_DTO_VALUE, HttpStatus.BAD_REQUEST,
                    ALREADY_EXIST, HttpStatus.BAD_REQUEST,
                    NOT_FOUND, HttpStatus.NOT_FOUND,
                    UNAUTHORIZED, HttpStatus.UNAUTHORIZED,
                    SESSION_IS_OVER, HttpStatus.UNAUTHORIZED,
                    INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR
            )
    );

    private final MessageSource messageSource;

    @ExceptionHandler
    public ResponseEntity<ErrorResponseBody> handleException(Exception ex) {
        Pair<ErrorCode, String> resolvedException = resolveException(ex);
        ErrorCode errorCode = resolvedException.getFirst();

        return ResponseEntity.status(HTTP_STATUS_MAP.get(errorCode))
                .body(ErrorResponseBody.builder()
                        .code(errorCode.getCode())
                        .message(resolvedException.getSecond())
                        .build());
    }

    private Pair<ErrorCode, String> resolveException(Exception ex) {
        Pair<ErrorCode, String> resolvedException;
         if (ex instanceof ServiceException) {
            resolvedException = resolveOpenfyException((ServiceException) ex);
        } else {
            resolvedException = resolveServerErrorException(ex);
        }
        return resolvedException;
    }

    private Pair<ErrorCode, String> resolveOpenfyException(ServiceException ex) {
        StringBuilder messageBuild = new StringBuilder(buildMessage(MESSAGE_KEYS_MAP.get(ex.getCode()), ex.getParams()));
        if (ex.getMessage() != null) {
            messageBuild.append(" " + ex.getMessage());
        }
        messageBuild.append(buildMessageFromOpenfyExceptions(ex));
        return Pair.of(ex.getCode(), messageBuild.toString());
    }

    private Pair<ErrorCode, String> resolveServerErrorException(Exception ex) {
        StringBuilder messageBuild  = new StringBuilder(
                buildMessage(MESSAGE_KEYS_MAP.get(INTERNAL_SERVER_ERROR), null)
        );
        if (ex.getMessage() != null) {
            messageBuild.append(": " + ex.getMessage());
        }
        return Pair.of(INTERNAL_SERVER_ERROR, messageBuild.toString());
    }

    private String buildMessageFromOpenfyExceptions(ServiceException ex) {
        int messageCount = 0;
        Throwable causeException = ex.getCause();
        StringBuilder messageBuilder = new StringBuilder();
        while (causeException != null) {
            if (causeException.getMessage() != null) {
                if (messageCount == 0) {
                    messageBuilder.append(": ");
                }
                messageBuilder
                        .append(buildMessage(causeException.getMessage(), ex.getParams()))
                        .append(" ");
            }
            causeException = causeException.getCause();
        }
        return messageBuilder.toString().strip();
    }

    private String buildMessage(String key, String[] params) {
        return messageSource.getMessage(
                key,
                params,
                key,
                LocaleContextHolder.getLocale()
        );
    }
}
