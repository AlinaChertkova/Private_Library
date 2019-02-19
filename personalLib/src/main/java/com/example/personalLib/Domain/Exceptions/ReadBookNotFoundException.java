package com.example.personalLib.Domain.Exceptions;

public class ReadBookNotFoundException extends BookException {

    private static final String DEFAULT_MSG = "Запись с id=%s не найдена!";

    public ReadBookNotFoundException(Long id) {
        super(String.format(DEFAULT_MSG, String.valueOf(id)));
    }
}