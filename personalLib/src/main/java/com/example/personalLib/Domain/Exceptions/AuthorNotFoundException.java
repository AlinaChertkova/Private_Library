package com.example.personalLib.Domain.Exceptions;

public class AuthorNotFoundException extends AuthorException {

    private static final String DEFAULT_MSG = "Автор с id=%s не существует!";

    public AuthorNotFoundException(Long id) {
        super(String.format(DEFAULT_MSG, String.valueOf(id)));
    }
}
