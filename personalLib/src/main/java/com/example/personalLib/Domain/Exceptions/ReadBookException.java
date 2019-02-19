package com.example.personalLib.Domain.Exceptions;

public class ReadBookException extends Exception {

    public ReadBookException(String message) {
        super(message);
    }

    public ReadBookException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReadBookException(Throwable cause) {
        super(cause);
    }
}

