package com.example.personalLib.DB.Models;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Books")
public class BookModel {

    public BookModel(){}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id", nullable = false)
    private Long id;

    @Column(name = "Isbn", nullable = false, unique = true)
    private String isbn;

    @Column(name = "Title", nullable = false)
    private String title;

    @Lob
    @Column(name = "Description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "cover", nullable = false)
    private String coverLink;

    @Column(name = "avgRating", columnDefinition="default '0'")
    private double avgRating;

    @Column(name = "markCount", columnDefinition="int not null default 0")
    private int markCount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ReviewModel> bookReviews = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ReadBookModel> bookRdf = new ArrayList<>();

    public List<UserModel> getBookUsersToReadList() {
        return bookUsersToReadList;
    }

    public void setBookUsersToReadList(List<UserModel> bookUsersToReadList) {
        this.bookUsersToReadList = bookUsersToReadList;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Book_genre",
            joinColumns = { @JoinColumn(name = "Book_id") },
            inverseJoinColumns = { @JoinColumn(name = "genre_id") }
    )
    private List<GenreModel> bookGenres = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "userToReadList")
    private List<UserModel> bookUsersToReadList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Book_Author",
            joinColumns = { @JoinColumn(name = "Book_id") },
            inverseJoinColumns = { @JoinColumn(name = "Author_id") }
    )
    private List<AuthorModel> bookAuthors = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<EditionModel> bookEditions = new ArrayList<>();

    public BookModel(String isbn, String title, String description, String coverLink, double avgRating, int markCount) {
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.coverLink = coverLink;
        this.avgRating = avgRating;
        this.markCount = markCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getISBN() {
        return isbn;
    }

    public void setISBN(String ISBN) {
        this.isbn = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverLink() {
        return coverLink;
    }

    public void setCoverLink(String coverLink) {
        this.coverLink = coverLink;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public int getMarkCount() {
        return markCount;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public void setMarkCount(int markCount) {
        this.markCount = markCount;
    }

    public List<ReviewModel> getBookReviews() {
        return bookReviews;
    }

    public void setBookReviews(List<ReviewModel> bookReviews) {
        this.bookReviews = bookReviews;
    }

    public List<ReadBookModel> getBookRdf() {
        return bookRdf;
    }

    public void setBookRdf(List<ReadBookModel> bookRdf) {
        this.bookRdf = bookRdf;
    }

    public List<GenreModel> getBookgenres() {
        return bookGenres;
    }

    public void setBookgenres(List<GenreModel> bookgenres) {
        this.bookGenres = bookgenres;
    }

    public List<AuthorModel> getBookAuthors() {
        return bookAuthors;
    }

    public void setBookAuthors(List<AuthorModel> bookAuthors) {
        this.bookAuthors = bookAuthors;
    }

    public List<EditionModel> getBookEditions() {
        return bookEditions;
    }

    public void setBookEditions(List<EditionModel> bookEditions) {
        this.bookEditions = bookEditions;
    }
}
