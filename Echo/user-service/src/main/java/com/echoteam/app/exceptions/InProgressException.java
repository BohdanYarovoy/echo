package com.echoteam.app.exceptions;

public class InProgressException extends RuntimeException {
    public InProgressException() {
        super("Logic hasn't implemented yet.");
    }
}
