package com.example.personalLib.DB.Models;


import javax.persistence.*;


@Entity
@Table(name = "To_Read")
public class ReadBookModel {

    public ReadBookModel(){}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private Long id;

    //@Column(name = "BookId", nullable = false)
    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookModel book;

    //@Column(name = "UserId", nullable = false)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @Column(name = "Mark")
    private Double mark;

    public ReadBookModel(BookModel bookModel, UserModel userModel, double v) {
        this.book = bookModel;
        this.user = userModel;
        this.mark = v;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookModel getBook() {
        return book;
    }

    public void setBook(BookModel book) {
        this.book = book;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public Double getMark() {
        return mark;
    }

    public void setMark(Double mark) {
        this.mark = mark;
    }
}
