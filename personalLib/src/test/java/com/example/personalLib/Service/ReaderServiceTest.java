package com.example.personalLib.Service;

import com.example.personalLib.DB.Models.*;
import com.example.personalLib.DB.Repository.*;
import com.example.personalLib.Domain.Exceptions.BookNotFoundException;
import com.example.personalLib.Domain.Exceptions.ReviewNotFoundException;
import com.example.personalLib.Domain.Exceptions.UserNotFoundException;
import com.example.personalLib.Domain.Model.*;
import com.example.personalLib.Domain.Services.Reader.ReaderService;
import com.example.personalLib.Domain.Services.Reader.ReaderServiceImp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class ReaderServiceTest {

    @TestConfiguration
    static class ReaderServiceTestConfiguration {

        @Bean
        public ReaderService readerService() {
            return new ReaderServiceImp();
        }
    }

    @Autowired
    private ReaderService readerService;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private ReviewRepository reviewRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private GenreRepository genreRepository;
    @MockBean
    private ReadBookRepository readBookRepository;

    @Before
    public void setUp() {

        final String login = "Alina";
        final String name = "Alina";
        final String password = "12345678";
        final LocalDateTime registrationDate = LocalDateTime.now();

        final UserModel userModel = new UserModel(login, name, password, registrationDate);
        Set<Role> r  = new HashSet<>();
        r.add(Role.USER);
        userModel.setRoles(r);
        userModel.setId(1L);

        Mockito.when(userRepository.save(any(UserModel.class))).thenReturn(userModel);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(userModel));
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        Mockito.when(userRepository.findByLogin("Alina")).thenReturn(userModel);

        final String ISBN = "978-5-389-02467-0";
        final String title = "June";
        final String description = "Drama";
        final String coverLink = "link";
        final double avgRating = 4.1;

        final BookModel bookModel = new BookModel(ISBN, title, description, coverLink, avgRating);
        bookModel.setId(1L);

        final BookModel bookModel2 = new BookModel("test", "June", "test", "test", 12.52);
        bookModel2.setId(2L);

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(bookModel));
        Mockito.when(bookRepository.existsById(1L)).thenReturn(true);

        final List<BookModel> books = Arrays.asList(bookModel, bookModel2);

        Mockito.when(bookRepository.findDistinctByTitleContainingIgnoreCase("June")).thenReturn(books);
        //Mockito.when(bookRepository.findByIsbn("978-5-389-02467-0")).thenReturn(Optional.of(bookModel));

        Mockito.when(bookRepository.findAll()).thenReturn(books);

        final ReviewModel reviewModel = new ReviewModel(userModel, bookModel, "text", LocalDateTime.now(),15.36);
        reviewModel.setId(1L);

        final ReviewModel reviewModel2 = new ReviewModel(userModel, bookModel, "text", LocalDateTime.now(),78.0);
        reviewModel2.setId(2L);

        Mockito.when(reviewRepository.save(any(ReviewModel.class))).thenReturn(reviewModel);
        Mockito.when(reviewRepository.findById(1L)).thenReturn(Optional.of(reviewModel));
        Mockito.when(reviewRepository.findByBookId(1L)).thenReturn(Arrays.asList(reviewModel, reviewModel2));

        final GenreModel genre = new GenreModel("test");
        genre.setId(1L);

        final GenreModel genre2 = new GenreModel("test2");
        genre.setId(2L);

        Mockito.when(genreRepository.findByBooksOfGenreId(1L)).thenReturn(Arrays.asList(genre, genre2));

        final ReadBookModel readBookModel = new ReadBookModel(bookModel, userModel, 15.36);
        readBookModel.setId(1L);

        final ReadBookModel readBookModel2 = new ReadBookModel(bookModel2, userModel, 154);
        readBookModel2.setId(2L);

        Mockito.when(readBookRepository.save(any(ReadBookModel.class))).thenReturn(readBookModel);
        Mockito.when(readBookRepository.findById(1L)).thenReturn(Optional.of(readBookModel));
        Mockito.when(readBookRepository.findByUserId(1L)).thenReturn(Arrays.asList(readBookModel, readBookModel2));
        Mockito.when(readBookRepository.existsById(1L)).thenReturn(true);
    }

    @Test
    public void getBookByIdTest() throws BookNotFoundException {
        Book book = readerService.getBookById(1L);

        double myPrecision = 0.0001;

        Assert.assertNotNull(book);
        Assert.assertEquals(book.getTitle(), "June");
        Assert.assertEquals(book.getISBN(), "978-5-389-02467-0");
        Assert.assertEquals(book.getDescription(), "Drama");
        Assert.assertEquals(book.getCoverLink(), "link");
        Assert.assertEquals(book.getAvgRating(), 4.1, myPrecision);
    }

    @Test
    public void getAllBooksTest(){

        final List<Book> books = new ArrayList<>(readerService.getAllBooks());

        Assert.assertNotNull(books);
        Assert.assertEquals(books.size(), 2);

        double myPrecision = 0.0001;

        Assert.assertEquals(books.get(0).getISBN(), "978-5-389-02467-0");
        Assert.assertEquals(books.get(0).getDescription(), "Drama");
        Assert.assertEquals(books.get(0).getTitle(), "June");
        Assert.assertEquals(books.get(0).getCoverLink(), "link");
        Assert.assertEquals(books.get(0).getAvgRating(), myPrecision, 4.1);

        Assert.assertEquals(books.get(1).getISBN(), "test");
        Assert.assertEquals(books.get(1).getDescription(), "test");
        Assert.assertEquals(books.get(1).getTitle(), "June");
        Assert.assertEquals(books.get(1).getCoverLink(), "test");
        Assert.assertEquals(books.get(1).getAvgRating(), 12.52, myPrecision);
    }

    @Test
    public void createReviewTest() throws BookNotFoundException, UserNotFoundException {

       final Review review =  readerService.createReview(1L, 1L, "test",
               LocalDateTime.now(), 15.36);

        double myPrecision = 0.0001;

        Assert.assertNotNull(review);
        Assert.assertEquals(review.getId(), Long.valueOf(1L));
        Assert.assertEquals(review.getText(), "text");
        Assert.assertEquals(review.getMark(), 15.36, myPrecision);
    }

    @Test
    public void getBooksByTitleTest(){

       final List<Book> books = new ArrayList<>(readerService.getBooksByTitle("June"));

        Assert.assertNotNull(books);
        Assert.assertEquals(books.size(), 2);
        Assert.assertEquals(books.get(0).getId(), Long.valueOf(1L));
        Assert.assertEquals(books.get(1).getId(), Long.valueOf(2L));
    }

   /* @Test
    public void getBookByISBNTest(){

        final List<Book> book = readerService.getBookByISBN("978-5-389-02467-0");

        double myPrecision = 0.001;

        Assert.assertNotNull(book);
        Assert.assertEquals(book.get(0).getId(), Long.valueOf(1L));
        Assert.assertEquals(book.get(0).getISBN(), "978-5-389-02467-0");
        Assert.assertEquals(book.get(0).getDescription(), "Drama");
        Assert.assertEquals(book.get(0).getTitle(), "June");
        Assert.assertEquals(book.get(0).getCoverLink(), "link");
        Assert.assertEquals(book.get(0).getAvgRating(), 4.1, myPrecision);
    }*/

    @Test
    public void addToListTest() throws BookNotFoundException, UserNotFoundException {

        final ReadBook readBook =  readerService.addToList(1L, 1L, 15.36);

        double myPrecision = 0.001;

        Assert.assertNotNull(readBook);
        Assert.assertEquals(readBook.getMark(), 15.36, myPrecision);
        Assert.assertEquals(readBook.getId(), Long.valueOf(1L));
    }

    @Test
    public void getAllReviewsByBookIdTest() throws BookNotFoundException, UserNotFoundException {

        final List<Review> review = new ArrayList<>(readerService.getAllReviewsByBookId(1L));

        Assert.assertNotNull(review);
        Assert.assertEquals(review.size(), 2);
    }

    @Test
    public void getReviewByIdTest() throws ReviewNotFoundException {

        final Review review =  readerService.getReviewById(1L);

        Assert.assertNotNull(review);
        Assert.assertEquals(review.getId(), Long.valueOf(1L));
    }

    @Test
    public void getAllReadBooksByUserIdTest() throws UserNotFoundException {

        final List<ReadBook> readBooks = new ArrayList<>(readerService.getAllReadBooksByUserId(1L));

        Assert.assertNotNull(readBooks);
        Assert.assertEquals(readBooks.size(), 2);
    }

    @Test
    public void getUserByLoginTest()  {

        final User user = readerService.getUserByLogin("Alina");

        Assert.assertNotNull(user);
        Assert.assertEquals(user.getId(), Long.valueOf(1L));
    }

    @Test
    public void getAllGenresByBookIdTest() {

        final List<GenreModel> genres = new ArrayList<>(genreRepository.findByBooksOfGenreId(1L));

        Assert.assertNotNull(genres);
        Assert.assertEquals(genres.size(), 2);
    }
}
