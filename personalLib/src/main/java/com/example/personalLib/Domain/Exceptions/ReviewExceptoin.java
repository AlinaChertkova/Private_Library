package com.example.personalLib.Domain.Exceptions;

public abstract class ReviewExceptoin extends Exception {

    public ReviewExceptoin(String message) {
        super(message);
    }

    public ReviewExceptoin(String message, Throwable cause) {
        super(message, cause);
    }

    public ReviewExceptoin(Throwable cause) {
        super(cause);
    }
}
