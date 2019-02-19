package com.example.personalLib.DB.Models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Publishing")
public class PublishingModel {

    public PublishingModel(){}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "publishing_id", nullable = false)
    private Long id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Lob
    @Column(name = "Description", nullable = true, columnDefinition = "CLOB")
    private String description ;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "publishing")
    private List<EditionModel> bookEditions = new ArrayList<>();

    public PublishingModel(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<EditionModel> getBookEditions() {
        return bookEditions;
    }

    public void setBookEditions(List<EditionModel> bookEditions) {
        this.bookEditions = bookEditions;
    }
}
