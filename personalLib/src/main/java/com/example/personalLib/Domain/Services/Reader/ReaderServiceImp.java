package com.example.personalLib.Domain.Services.Reader;


import com.example.personalLib.DB.Models.BookModel;
import com.example.personalLib.DB.Models.ReadBookModel;
import com.example.personalLib.DB.Models.ReviewModel;
import com.example.personalLib.DB.Models.UserModel;
import com.example.personalLib.DB.Repository.*;
import com.example.personalLib.Domain.Exceptions.BookNotFoundException;
import com.example.personalLib.Domain.Exceptions.ReadBookNotFoundException;
import com.example.personalLib.Domain.Exceptions.ReviewNotFoundException;
import com.example.personalLib.Domain.Exceptions.UserNotFoundException;
import com.example.personalLib.Domain.Model.*;
import com.example.personalLib.Domain.Util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.Cacheable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ReaderServiceImp implements ReaderService{

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReadBookRepository readBookRepository;

    @Override
    public Book getBookById(Long id) throws BookNotFoundException{
        final BookModel userModel = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return BookConverter.convertToBookDomain(userModel);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll().stream().map(BookConverter::convertToBookDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Review createReview(Long userId, Long bookId, String text, LocalDateTime date, Double mark) throws UserNotFoundException, BookNotFoundException
    {
        UserModel user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(userId));
        BookModel book = bookRepository.findById(bookId).orElseThrow(()-> new BookNotFoundException(bookId));

        return ReviewConverter.convertToReviewDomain(
                reviewRepository.save(new ReviewModel(user, book, text, date, mark)));
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        return BookConverter.convertToBookDomainList(bookRepository.findDistinctByTitleContainingIgnoreCase(title));
    }

    @Override
    public List<Book> getBooksByAuthorName(String name) {
        return BookConverter.convertToBookDomainList(bookRepository.findDistinctByBookAuthorsNameContainingIgnoreCase (name));
    }

    @Override
    public List<Book> getBookByISBN(String isbn){

        List<BookModel> book = bookRepository.findByIsbn(isbn)/*.orElseThrow(()-> new BookNotFoundException(isbn.toString()))*/;
        return BookConverter.convertToBookDomainList(book);
    }

    @Override
    public ReadBook addToList(Long userId, Long bookId, Double mark) throws UserNotFoundException, BookNotFoundException {

        UserModel user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(userId));
        BookModel book = bookRepository.findById(bookId).orElseThrow(()-> new BookNotFoundException(userId));

        return ReadBookConverter.convertToReadBookDomain(
                readBookRepository.save(new ReadBookModel(book, user, mark)));
    }

    @Override
    public List<Review> getAllReviewsByBookId(Long bookId) throws BookNotFoundException {
        if (bookRepository.existsById(bookId))
        {
            return ReviewConverter.convertToReviewDomainList(reviewRepository.findByBookId(bookId));
        }
        else throw new BookNotFoundException(bookId);
    }

    @Override
    public Review getReviewById(Long id) throws ReviewNotFoundException {
        return ReviewConverter.convertToReviewDomain(reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id)));
    }

    @Override
    public List<ReadBook> getAllReadBooksByUserId(Long userId) throws UserNotFoundException {

        UserModel user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        return ReadBookConverter.convertToReadBookDomainList(readBookRepository.findByUserId(userId));
    }

    @Override
    public User getUserByLogin(String login) {
        return UserConverter.convertToUserDomain(userRepository.findByLogin(login));
    }

    @Override
    public List<Genre> getAllGenresByBookId(Long id) {
        return GenreConverter.convertToGenreDomainList(genreRepository.findByBooksOfGenreId(id));
    }

    @Override
    public void deleteReadBooks(List<Long> ids) throws Exception {
        for (Long id : ids)
        {
            if (!readBookRepository.existsById(id))
            {
                throw new BookNotFoundException(id);
            }
            readBookRepository.deleteById(id);
        }
    }

    @Override
    public ReadBook updateMark(Long id, Double mark) throws ReadBookNotFoundException {

        final ReadBookModel readBookModel = readBookRepository.findById(id)
                .orElseThrow(() -> new ReadBookNotFoundException(id));

        readBookModel.setMark(mark);

        return ReadBookConverter.convertToReadBookDomain(readBookRepository.save(readBookModel));
    }

    @Override
    public Review updateReview(Long id, String text, Double newMark) throws ReviewNotFoundException {

        final ReviewModel review = reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException(id));

        review.setText(text);
        review.setMark(newMark);
        return ReviewConverter.convertToReviewDomain(reviewRepository.save(review));
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
    public List<Review> getAllReviewsByUserId(Long userId) throws UserNotFoundException {
        if (userRepository.existsById(userId))
        {
            return ReviewConverter.convertToReviewDomainList(reviewRepository.findByUserId(userId));
        }
        else throw new UserNotFoundException(userId);
    }

    @Override
    public User updateUser(Long id, String login, String name, String password) throws UserNotFoundException {
        final UserModel user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        user.setLogin(login);
        user.setName(name);
        user.setPassword(password);
        return UserConverter.convertToUserDomain(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) throws UserNotFoundException {
        final UserModel user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        userRepository.deleteById(id);
    }
}
