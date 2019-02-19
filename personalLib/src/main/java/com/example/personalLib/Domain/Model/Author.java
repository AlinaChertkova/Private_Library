package com.example.personalLib.Domain.Model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Lob;

@Builder
@Getter
public class Author {

    private Long id;
    private String name;
    private String description;

}
