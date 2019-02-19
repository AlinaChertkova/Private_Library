package com.example.personalLib.API.Data;


import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Data
public class BookData {

    private Long id;
    private String ISBN;
    private String Title;
    private String description;
    private String coverLink;
    private Double avgRating;
    private List<AuthorData> bookAuthors = new ArrayList<>();
}
