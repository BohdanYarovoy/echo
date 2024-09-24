package com.echoteam.app.exceptions;

public class ParameterIsNullException  extends Exception{

    public ParameterIsNullException() {
    }

    public ParameterIsNullException(String message) {
        super(message);
    }

    public ParameterIsNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterIsNullException(Throwable cause) {
        super(cause);
    }

    public ParameterIsNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
