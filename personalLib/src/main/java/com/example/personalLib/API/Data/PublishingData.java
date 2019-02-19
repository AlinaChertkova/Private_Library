package com.example.personalLib.API.Data;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PublishingData {

    private Long id;
    private String name;
    private String description;
}
