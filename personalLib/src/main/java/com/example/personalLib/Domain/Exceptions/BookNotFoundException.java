package com.example.personalLib.Domain.Exceptions;

public class BookNotFoundException extends BookException {

    private static final String DEFAULT_MSG = "Книга с id=%s не существует!";

    public BookNotFoundException(Long id) {
        super(String.format(DEFAULT_MSG, String.valueOf(id)));
    }
}