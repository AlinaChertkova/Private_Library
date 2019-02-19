package com.example.personalLib.Domain.Services.Admin;

import com.example.personalLib.DB.Models.AuthorModel;
import com.example.personalLib.DB.Models.ReviewModel;
import com.example.personalLib.DB.Repository.AuthorRepository;
import com.example.personalLib.DB.Repository.ReviewRepository;
import com.example.personalLib.Domain.Model.Author;
import com.example.personalLib.Domain.Model.Book;
import com.example.personalLib.Domain.Util.AuthorConverter;
import com.example.personalLib.Domain.Util.ReviewConverter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class AdminServiceImp implements AdminService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public void deleteBook(Long id) {

    }

    @Override
    public void deleteReview(Long id) {

    }

    @Override
    public Author addAuthor(String description, String name) {

        return AuthorConverter.convertToAuthorDomain(
                authorRepository.save(new AuthorModel(description, name)));
    }

    @Override
    public Book addBook(String isbn, String title, String description, String coverLink, Collection<Long> authorsIds) {
        return null;
    }
}
