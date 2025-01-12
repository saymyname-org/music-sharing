package ru.improve.skufify.api.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicSharingException extends RuntimeException {

    private ErrorCode code;

    private String[] params = null;

    private Throwable cause = null;

    public MusicSharingException(ErrorCode code) {
        this.code = code;
    }

    public MusicSharingException(ErrorCode code, String message) {
        super(message);
        this.code = code;
    }

    public MusicSharingException(ErrorCode code, String message, Throwable cause) {
        super(message);
        this.code = code;
        this.cause = cause;
    }

    public MusicSharingException(ErrorCode code, Throwable cause) {
        this.code = code;
        this.cause = cause;
    }

    public MusicSharingException(ErrorCode code, String message, String[] params) {
        super(message);
        this.code = code;
        this.params = params;
    }

    public MusicSharingException(ErrorCode code, String[] params) {
        this.code = code;
        this.params = params;
    }
}
