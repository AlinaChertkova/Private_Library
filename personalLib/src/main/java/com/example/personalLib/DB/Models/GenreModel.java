package com.example.personalLib.DB.Models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "genres")
public class GenreModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "genre_id", nullable = false)
    private Long id;

    @Column(name = "genre", nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "bookGenres")
    private List<BookModel> booksOfGenre = new ArrayList<>();

    public GenreModel(String name) {
        this.name = name;
    }

    public GenreModel() {    }

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

    public List<BookModel> getBooksOfgenre() {
        return booksOfGenre;
    }

    public void setBooksOfgenre(List<BookModel> booksOfgenre) {
        this.booksOfGenre = booksOfgenre;
    }
}
