package com.example.personalLib.DB.Models;


import com.example.personalLib.Domain.Model.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

@Entity
@Table(name = "Reviews")
public class ReviewModel {

    public ReviewModel(){}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false, insertable = false, updatable = false)
    private Long id;

    @Lob
    @Column(name = "Text", nullable = false, columnDefinition = "CLOB")
    private String text;

    //@Column(name = "User", nullable = false)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

   //@Column(name = "Book", nullable = false)
    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookModel book;

    @Column(name = "Date_of_Pub", nullable = true)
    private LocalDateTime publishingDate;

    @Column(name = "Mark", nullable = true)
    private Double mark;

    public ReviewModel (@NotNull UserModel user, @NotNull BookModel book, @NotNull String text, @NotNull LocalDateTime date, Double mark)
    {
        this.user = user;
        this.book = book;
        this.text = text;
        this.publishingDate = date;
        this.mark = mark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public BookModel getBook() {
        return book;
    }

    public void setBook(BookModel book) {
        this.book = book;
    }

    public LocalDateTime getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(LocalDateTime publishingDate) {
        this.publishingDate = publishingDate;
    }

    public Double getMark() {
        return mark;
    }

    public void setMark(Double mark) {
        this.mark = mark;
    }
}
