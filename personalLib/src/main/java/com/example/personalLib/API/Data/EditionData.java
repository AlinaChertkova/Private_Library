package com.example.personalLib.API.Data;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EditionData {

    private Long id;
    private long year;
    private String coverLink;
    private String series;
    private BookData book;
    private PublishingData publishing;
}
