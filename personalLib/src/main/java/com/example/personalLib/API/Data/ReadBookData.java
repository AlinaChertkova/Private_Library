package com.example.personalLib.API.Data;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadBookData
{

    private Long id;
    private double mark;
    private BookData book;
}
