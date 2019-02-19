package com.example.personalLib.Domain.Model;

import com.example.personalLib.DB.Models.BookModel;
import com.example.personalLib.DB.Models.UserModel;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Getter
@Builder
public class ReadBook {

    private Long id;
    private double mark;
    private Book book;
}
