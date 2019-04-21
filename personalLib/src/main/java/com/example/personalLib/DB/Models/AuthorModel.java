package com.example.personalLib.DB.Models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Authors")
public class AuthorModel {

    public AuthorModel() {}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "Name", nullable = false, unique = true)
    private String name;

    @Lob
    @Column(name = "Description", columnDefinition = "text")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "bookAuthors")
    private List<BookModel> booksOfAuthor = new ArrayList<>();

    public AuthorModel(String description, String name) {

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

    public List<BookModel> getBooksOfAuthor() {
        return booksOfAuthor;
    }

    public void setBooksOfAuthor(List<BookModel> booksOfAuthor) {
        this.booksOfAuthor = booksOfAuthor;
    }
}

