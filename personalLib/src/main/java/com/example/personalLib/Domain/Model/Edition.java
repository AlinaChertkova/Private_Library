package com.example.personalLib.Domain.Model;

import com.example.personalLib.DB.Models.BookModel;
import com.example.personalLib.DB.Models.PublishingModel;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Builder
@Getter
public class Edition {

    private Long id;
    private long year;
    private String coverLink;
    private String series;
    private Book book;
    private Publishing publishing;
}
