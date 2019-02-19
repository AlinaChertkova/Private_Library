package com.example.personalLib.Domain.Model;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Genre {

    private Long id;
    private String name;
}
