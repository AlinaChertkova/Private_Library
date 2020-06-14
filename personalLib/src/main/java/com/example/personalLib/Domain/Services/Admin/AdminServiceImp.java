package com.example.personalLib.Domain.Services.Admin;

import com.example.personalLib.DB.Models.*;
import com.example.personalLib.DB.Repository.*;
import com.example.personalLib.Domain.Exceptions.AuthorNotFoundException;
import com.example.personalLib.Domain.Exceptions.BookNotFoundException;
import com.example.personalLib.Domain.Exceptions.ReviewNotFoundException;
import com.example.personalLib.Domain.Model.Author;
import com.example.personalLib.Domain.Model.Book;
import com.example.personalLib.Domain.Model.Genre;
import com.example.personalLib.Domain.Util.AuthorConverter;
import com.example.personalLib.Domain.Util.BookConverter;
import com.example.personalLib.Domain.Util.GenreConverter;
import com.example.personalLib.Domain.Util.ReviewConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminServiceImp implements AdminService {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public void deleteBook(Long id) throws BookNotFoundException {
        if (!bookRepository.existsById(id))
        {
            throw new BookNotFoundException(id);
        }
        else bookRepository.deleteById(id);
    }

    @Override
    public void deleteReview(Long id) throws ReviewNotFoundException {
        if (!reviewRepository.existsById(id))
        {
            throw new ReviewNotFoundException(id);
        }
        else reviewRepository.deleteById(id);
    }

    @Override
    public Author addAuthor(String description, String name) {

        return AuthorConverter.convertToAuthorDomain(
                authorRepository.save(new AuthorModel(description, name)));
    }

    @Override
    public Author updateAuthor(Long id, String description, String name) throws AuthorNotFoundException {
        final AuthorModel author = authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));

        author.setName(name);
        author.setDescription(description);
        return AuthorConverter.convertToAuthorDomain(authorRepository.save(author));
    }

    @Override
    public void deleteAuthor(Long id) throws AuthorNotFoundException {

        if (!authorRepository.existsById(id))
        {
            throw new AuthorNotFoundException(id);
        }
        else
            authorRepository.deleteById(id);
    }

    @Override
    public Genre addGenre(String name) {
        return GenreConverter.convertToGenreDomain(
                genreRepository.save(new GenreModel(name)));
    }
}








