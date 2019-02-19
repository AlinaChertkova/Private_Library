package com.example.personalLib.API.Data;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewData {

    private Long id;
    private String text;
    private LocalDateTime publishingDate;
    private Double mark;
    private UserData user;
    private BookData book;
}