package com.example.personalLib.Domain.Exceptions;

public class UserNotFoundException extends UserException{

    private static final String DEFAULT_MSG = "Пользователь с id=%s не существует!";

    public UserNotFoundException(Long id) {
        super(String.format(DEFAULT_MSG, String.valueOf(id)));
    }
}
