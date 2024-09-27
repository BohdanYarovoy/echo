package com.echoteam.app.exceptions;

public class UniqueRecordAlreadyExistsException extends RuntimeException{

    public UniqueRecordAlreadyExistsException() {
    }

    public UniqueRecordAlreadyExistsException(String message) {
        super(message);
    }

    public UniqueRecordAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UniqueRecordAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public UniqueRecordAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
