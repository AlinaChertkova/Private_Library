package com.example.personalLib.DB.Models;


import javax.persistence.*;

@Entity
@Table(name = "Editions")
public class EditionModel {

    public EditionModel(){}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "publishing_id")
    private PublishingModel publishing;

    @Column(name = "Year", nullable = false)
    private long year;

    @Column(name = "Cover", nullable = true)
    private String coverLink;

    @Column(name = "Series", nullable = true)
    private String series;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookModel book;

    public EditionModel(PublishingModel publishingModel, int i, String test_link, String roza_vetrov, BookModel bookModel) {
        this.book = bookModel;
        this.coverLink = test_link;
        this.publishing = publishingModel;
        this.year = i;
        this.series = roza_vetrov;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PublishingModel getPublishing() {
        return publishing;
    }

    public void setPublishing(PublishingModel publishing) {
        this.publishing = publishing;
    }

    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
    }

    public String getCoverLink() {
        return coverLink;
    }

    public void setCoverLink(String coverLink) {
        this.coverLink = coverLink;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public BookModel getBook() {
        return book;
    }

    public void setBook(BookModel book) {
        this.book = book;
    }
}
