package ru.improve.openfy.api.error;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        ErrorCodeMessagePair resolvedException = resolveException(ex);

        ErrorCode errorCode = resolvedException.errorCode;
        return ResponseEntity.status(HTTP_STATUS_MAP.get(errorCode))
                .body(ErrorResponseBody.builder()
                        .code(errorCode.getCode())
                        .message(resolvedException.message)
                        .build());
    }

    private ErrorCodeMessagePair resolveException(Exception ex) {
        ErrorCodeMessagePair resolvedException;
         if (ex instanceof ServiceException e) {
            resolvedException = resolveServiceException(e);
        } else if (ex instanceof MethodArgumentNotValidException ||
                   ex instanceof HandlerMethodValidationException) {
             resolvedException = resolveControllerDtoValidatorException(ex);
         } else {
            resolvedException = resolveInternalServerErrorException(ex);
        }
        return resolvedException;
    }

    private ErrorCodeMessagePair resolveServiceException(ServiceException ex) {
        ErrorCode errorCode = ex.getCode();
        StringBuilder message = new StringBuilder(buildMessage(MESSAGE_KEYS_MAP.get(errorCode), ex.getParams()));
        if (ex.getMessage() != null) {
            message.append(": " + buildMessage(ex.getMessage(), ex.getParams()));
        }
        message.append(createMessageFromThrowable(ex.getCause()));

        return ErrorCodeMessagePair.of(errorCode, message.toString());
    }

    private ErrorCodeMessagePair resolveControllerDtoValidatorException(Exception ex) {
        ErrorCode errorCode = ILLEGAL_DTO_VALUE;
        StringBuilder messageBuilder = new StringBuilder(
                buildMessage(MESSAGE_KEYS_MAP.get(errorCode), null)
        );

        String fieldErrorsMessage = getErrorsStream(ex)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(". "));

        messageBuilder.append(": ").append(fieldErrorsMessage);
        return ErrorCodeMessagePair.of(errorCode, messageBuilder.toString());
    }

    private Stream<String> getErrorsStream(Exception ex) {
        if (ex instanceof MethodArgumentNotValidException e) {
            return e.getBindingResult().getFieldErrors().stream()
                    .map(fieldError -> {
                        String fieldName = fieldError.getField();
                        String fieldErrorMsg = fieldError.getDefaultMessage();
                        return fieldName + " " + fieldErrorMsg;
                    });
        }

        if (ex instanceof HandlerMethodValidationException e) {
            return e.getAllValidationResults().stream()
                    .filter(result -> result.getMethodParameter().getMethod() != null)
                    .map(result -> {
                        String parameterName = result.getMethodParameter().getParameterName();
                        String fieldErrorsMsg = result.getResolvableErrors().stream()
                                .map(MessageSourceResolvable::getDefaultMessage)
                                .collect(Collectors.joining(", "));
                        return parameterName + " " + fieldErrorsMsg;
                    });
        }

        return null;
    }

    private ErrorCodeMessagePair resolveInternalServerErrorException(Exception ex) {
        return ErrorCodeMessagePair.of(INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    private String createMessageFromThrowable(Throwable throwable) {
        StringBuilder message = new StringBuilder();
        if (throwable != null) {
            message.append(": ").append(throwable.getMessage());
        }
        return message.toString();
    }

    private String buildMessage(String messageKey, String[] params) {
        return messageSource.getMessage(
                messageKey,
                params,
                messageKey,
                Locale.ENGLISH
        );
    }

    private record ErrorCodeMessagePair(ErrorCode errorCode, String message) {

        public static ErrorCodeMessagePair of(ErrorCode errorCode, String message) {
            return new ErrorCodeMessagePair(errorCode, message);
        }
    }
}
