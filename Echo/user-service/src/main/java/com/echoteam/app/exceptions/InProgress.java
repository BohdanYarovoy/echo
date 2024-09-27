package com.echoteam.app.exceptions;

public class InProgress extends RuntimeException {
    public InProgress() {
        super("Logic hasn`t implemented yet.");
    }
}
