package com.example.personalLib.Domain.Services.Admin;

import com.example.personalLib.DB.Models.AuthorModel;
import com.example.personalLib.DB.Models.BookModel;
import com.example.personalLib.DB.Models.GenreModel;
import com.example.personalLib.DB.Models.ReviewModel;
import com.example.personalLib.DB.Repository.AuthorRepository;
import com.example.personalLib.DB.Repository.BookRepository;
import com.example.personalLib.DB.Repository.GenreRepository;
import com.example.personalLib.DB.Repository.ReviewRepository;
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

import java.util.Collection;
@Service
public class AdminServiceImp implements AdminService {

    @Autowired
    private AuthorRepository authorRepository;
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
    public Book addBook(String isbn, String title, String description, String coverLink, Collection<Long> authorsIds) {
       return null;
       /* return BookConverter.convertToBookDomain(
                bookRepository.save(new BookModel(isbn, title, description,  name)));*/
    }

    @Override
    public Book updateBook(Long id, String isbn, String title, String description, String coverLink, Collection<Long> authorsIds) {

        return null;
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
