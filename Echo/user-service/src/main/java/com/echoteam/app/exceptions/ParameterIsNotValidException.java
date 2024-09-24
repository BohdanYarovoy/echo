package com.echoteam.app.exceptions;

public class ParameterIsNotValidException extends Exception {

    public ParameterIsNotValidException() {
    }

    public ParameterIsNotValidException(String message) {
        super(message);
    }

    public ParameterIsNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterIsNotValidException(Throwable cause) {
        super(cause);
    }

    public ParameterIsNotValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
