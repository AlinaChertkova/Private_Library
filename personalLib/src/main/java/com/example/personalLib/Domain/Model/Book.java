package com.example.personalLib.Domain.Model;


import com.example.personalLib.DB.Models.AuthorModel;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Getter
public class Book {

    private Long id;
    private String ISBN;
    private String Title;
    private String description;
    private String coverLink;
    private double avgRating;
    private List<Author> bookAuthors = new ArrayList<>();
}
