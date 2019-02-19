package com.example.personalLib.Domain.Model;


import com.example.personalLib.DB.Models.BookModel;
import com.example.personalLib.DB.Models.UserModel;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Builder
public class Review {

    private Long id;
    private String text;
    //private Long user;
    //private Long book;
    private LocalDateTime publishingDate;
    private Double mark;
    private User user;
    private Book book;
}
