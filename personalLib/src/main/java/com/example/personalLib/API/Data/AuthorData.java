package com.example.personalLib.API.Data;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthorData {
    private Long id;
    private String name;
    private String description;
}
