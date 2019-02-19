package com.example.personalLib.Domain.Exceptions;

public class ReviewNotFoundException extends ReviewExceptoin {
    private static final String DEFAULT_MSG = "Рецензия с id=%s не существует!";

    public ReviewNotFoundException(Long id) {
        super(String.format(DEFAULT_MSG, String.valueOf(id)));
    }
}
