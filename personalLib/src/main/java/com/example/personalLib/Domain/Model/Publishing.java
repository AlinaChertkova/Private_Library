package com.example.personalLib.Domain.Model;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Lob;

@Builder
@Getter
public class Publishing {

    private Long id;
    private String name;
    private String description;
}
