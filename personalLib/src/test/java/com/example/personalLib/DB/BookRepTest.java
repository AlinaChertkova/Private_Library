package com.example.personalLib.DB;

import com.example.personalLib.DB.Models.*;
import com.example.personalLib.DB.Repository.*;
import com.example.personalLib.Domain.Model.Genre;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PublishingRepository publishingRepository;
    @Autowired
    private EditionRepository editionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private AuthorRepository authorRepository;


    @Test
    public void testSaveBook() {

        final String ISBN = "978-5-389-02467-0";
        final String title = "June";
        final String description = "Drama";
        final String coverLink = "link";
        final double avgRating = 4.1;

        final BookModel bookModel = new BookModel(ISBN, title, description, coverLink, avgRating);

        final BookModel saved = bookRepository.save(bookModel);

        double myPrecision = 0.0001;

        Assert.assertNotNull(saved.getId());
        Assert.assertEquals(saved.getISBN(), bookModel.getISBN());
        Assert.assertEquals(saved.getDescription(), bookModel.getDescription());
        Assert.assertEquals(saved.getCoverLink(), bookModel.getCoverLink());
        Assert.assertEquals(saved.getAvgRating(), myPrecision,  bookModel.getAvgRating());

        Optional<BookModel> optSelectedValue = bookRepository.findById(saved.getId());

        Assert.assertTrue(optSelectedValue.isPresent());

        final BookModel selectedValue = optSelectedValue.get();

        Assert.assertNotNull(selectedValue.getId());
        Assert.assertEquals(selectedValue.getISBN(), bookModel.getISBN());
        Assert.assertEquals(selectedValue.getDescription(), bookModel.getDescription());
        Assert.assertEquals(selectedValue.getCoverLink(), bookModel.getCoverLink());
        Assert.assertEquals(selectedValue.getAvgRating(), myPrecision,  bookModel.getAvgRating());
    }

    @Test
    public void testUpdateBook() {

        final String ISBN = "978-5-389-02467-0";
        final String title = "June";
        final String description = "Drama";
        final String coverLink = "link";
        final double avgRating = 4.1;

        final BookModel bookModel = new BookModel(ISBN, title, description, coverLink, avgRating);

        final BookModel saved = bookRepository.save(bookModel);

        Optional<BookModel> optSelectedValue = bookRepository.findById(saved.getId());

        Assert.assertTrue(optSelectedValue.isPresent());

        final BookModel selectedValue = optSelectedValue.get();

        selectedValue.setISBN("test_ISBN");
        selectedValue.setDescription("test_description");
        selectedValue.setTitle("test_title");
        selectedValue.setCoverLink("test_coverLink");
        selectedValue.setAvgRating(11.56);

        double myPrecision = 0.0001;

        final BookModel updated = bookRepository.save(bookModel);

        Assert.assertEquals(selectedValue.getId(), updated.getId());
        Assert.assertEquals(selectedValue.getISBN(), updated.getISBN());
        Assert.assertEquals(selectedValue.getDescription(), updated.getDescription());
        Assert.assertEquals(selectedValue.getCoverLink(), updated.getCoverLink());
        Assert.assertEquals(selectedValue.getAvgRating(), bookModel.getAvgRating(), myPrecision);
    }
    //Проверка каскадного удаления
    @Test
    public void testCascadeDeleteBook() {

        final String ISBN = "978-5-389-02467-0";
        final String title = "June";
        final String description = "Drama";
        final String coverLink = "link";
        final double avgRating = 4.1;

        final BookModel bookModel = new BookModel(ISBN, title, description, coverLink, avgRating);


        //Save publishing
        final String name = "Alpina";
        final String pubDescription = "New pub";

        final PublishingModel publishingModel = new PublishingModel(name, pubDescription);
        final PublishingModel savedPub = publishingRepository.save(publishingModel);

        //Save edition
        final EditionModel editoin = new EditionModel(publishingModel, 1999, "test_link", "Roza vetrov", bookModel);
        final EditionModel sevedEdition = editionRepository.save(editoin);

        final EditionModel editoin2 = new EditionModel(publishingModel, 2005, "test_link2", "Roza vetrov", bookModel);
        final EditionModel sevedEdition2 = editionRepository.save(editoin);


        /*Optional<BookModel> optSelectedValueUpd = bookRepository.findById(saved.getId());

        Assert.assertTrue(optSelectedValueUpd.isPresent());

        final BookModel selectedValueUpd = optSelectedValueUpd.get();*/

        List<EditionModel> mySet = new ArrayList<>();
        EditionModel [] myArray = {sevedEdition, sevedEdition2};
        Collections.addAll(mySet, myArray);
        bookModel.setBookEditions(mySet);

        final BookModel saved = bookRepository.save(bookModel);

        Optional<BookModel> optSelectedValue = bookRepository.findById(saved.getId());
        Assert.assertTrue(optSelectedValue.isPresent());

        final BookModel selectedValue = optSelectedValue.get();
        bookRepository.delete(selectedValue);

        Optional<BookModel> optSelectedValue2 = bookRepository.findById(saved.getId());
        Assert.assertFalse(optSelectedValue2.isPresent());

        Optional<EditionModel> optSelectedValueEdition = editionRepository.findById(sevedEdition.getId());
        Assert.assertFalse(optSelectedValueEdition.isPresent());

        Optional<EditionModel> optSelectedValueEdition2 = editionRepository.findById(sevedEdition2.getId());
        Assert.assertFalse(optSelectedValueEdition2.isPresent());

        Optional<PublishingModel> optSelectedValuePublishing = publishingRepository.findById(savedPub.getId());
        Assert.assertTrue(optSelectedValuePublishing.isPresent());
    }

    @Test
    public void testDeleteBook() {

        final String ISBN = "978-5-389-02467-0";
        final String title = "June";
        final String description = "Drama";
        final String coverLink = "link";
        final double avgRating = 4.1;

        final BookModel bookModel = new BookModel(ISBN, title, description, coverLink, avgRating);

        final BookModel saved = bookRepository.save(bookModel);

        Optional<BookModel> optSelectedValue = bookRepository.findById(saved.getId());

        Assert.assertTrue(optSelectedValue.isPresent());

        final BookModel selectedValue = optSelectedValue.get();

        bookRepository.delete(selectedValue);

        Optional<BookModel> optSelectedValue2 = bookRepository.findById(saved.getId());

        Assert.assertFalse(optSelectedValue2.isPresent());
    }

    @Test
    public void testBookEditionRelations() {
        //Создадим книгу
        final String ISBN = "978-5-389-02467-0";
        final String title = "June";
        final String description = "Drama";
        final String coverLink = "link";
        final double avgRating = 4.1;

        final BookModel bookModel = new BookModel(ISBN, title, description, coverLink, avgRating);

        final BookModel saved = bookRepository.save(bookModel);

        Optional<BookModel> optSelectedValueUpd = bookRepository.findById(saved.getId());
        Assert.assertTrue(optSelectedValueUpd.isPresent());

        //Save publishing
        final String name = "Alpina";
        final String pubDescription = "New pub";

        final PublishingModel publishingModel = new PublishingModel(name, pubDescription);
        final PublishingModel savedPub = publishingRepository.save(publishingModel);

        //Save edition
        final EditionModel editoin = new EditionModel(publishingModel, 1999, "test_link", "Roza vetrov", saved);
        final EditionModel sevedEdition = editionRepository.save(editoin);

        final EditionModel editoin2 = new EditionModel(publishingModel, 2005, "test_link2", "Roza vetrov", saved);
        final EditionModel sevedEdition2 = editionRepository.save(editoin2);

        Assert.assertEquals(editionRepository.findByBookId(saved.getId()).size(), 2);

    }

    @Test
    public void testBookReviewRelations() {

        BookModel book = bookRepository.save(new BookModel("122222", "Rrrrrrr", "fdffdfdf", "link", 1234));
       // UserModel user = userRepository.save(new UserModel("Alina", "User", "Al", "111111", LocalDateTime.now()));
        //Long userId, Long bookId, String text, LocalDateTime date, double mark
        //createReview(user.getId(), book.getId(), "Aaaaaaaa", LocalDateTime.now(), 122 );
        //createReview(user.getId(), book.getId(), "Bbbbbbbbb", LocalDateTime.now(), 122 );
        //(String isbn, String title, String description, String coverLink, double avgRating)
        bookRepository.save(new BookModel("122222", "Rrrrrrr", "fdffdfdf", "link", 1234));

    }

    @Test
    public void testFindByTitle() {

        final String ISBN = "978-5-389-02467-0";
        final String title = "June";
        final String description = "Drama";
        final String coverLink = "link";
        final double avgRating = 4.1;

        final BookModel bookModel = new BookModel(ISBN, title, description, coverLink, avgRating);

        bookRepository.save(bookModel);

        final BookModel bookModel2 = new BookModel("test", "June june July", "test", "test", 12.52);

        bookRepository.save(bookModel2);

        List<BookModel> books = bookRepository.findDistinctByTitleContainingIgnoreCase("june");

        Assert.assertEquals(books.size(), 2);
    }

    @Test
    public void testFindByIsbn() {

        final String ISBN = "978-5-389-02467-1";
        final String title = "June";
        final String description = "Drama";
        final String coverLink = "link";
        final double avgRating = 4.1;

        final BookModel bookModel = new BookModel(ISBN, title, description, coverLink, avgRating);

        bookRepository.save(bookModel);

        final BookModel bookModel2 = new BookModel("test", "June", "test", "test", 12.52);

        bookRepository.save(bookModel2);

        List<BookModel> book = bookRepository.findByIsbn("978-5-389-02467-1");

        Assert.assertEquals(book.get(0).getISBN(), bookModel.getISBN());
    }

    @Test
    public void testBookGenreRelations() {

        final String ISBN = "978-5-389-02467-0";
        final String title = "June";
        final String description = "Drama";
        final String coverLink = "link";
        final double avgRating = 4.1;

        final BookModel bookModel = new BookModel(ISBN, title, description, coverLink, avgRating);
        final BookModel bookModel2 = new BookModel("test", "test", "test", "test", 12.3);

        final GenreModel genre1 = new GenreModel( "Humor");
        GenreModel savedGenre1 = genreRepository.save(genre1);

        final GenreModel genre2 = new GenreModel( "NonFiction");
        GenreModel savedGenre2 = genreRepository.save(genre2);

        bookModel.setBookgenres(Arrays.asList(genre1, genre2));
        bookModel2.setBookgenres(Arrays.asList(genre2));

        bookRepository.save(bookModel);
        bookRepository.save(bookModel2);

        Assert.assertEquals(bookRepository.findByBookGenresId(savedGenre1.getId()).size(), 1);
        Assert.assertEquals(bookRepository.findByBookGenresId(savedGenre2.getId()).size(), 2);
    }

    @Test
    public void testBookAuthorRelations() {

        final String ISBN = "978-5-389-02467-0";
        final String title = "June";
        final String description = "Drama";
        final String coverLink = "link";
        final double avgRating = 4.1;

        final BookModel bookModel = new BookModel(ISBN, title, description, coverLink, avgRating);
        final BookModel bookModel2 = new BookModel("test", "test", "test", "test", 12.3);

        final AuthorModel author1 = new AuthorModel( "test", "Alina Chertkova");
        AuthorModel savedA = authorRepository.save(author1);

        final AuthorModel author2 = new AuthorModel( "test2", "Alina Alina");
        AuthorModel savedA2 = authorRepository.save(author2);

        bookModel.setBookAuthors(Arrays.asList(author1, author2));
        bookModel2.setBookAuthors(Arrays.asList(author2));

        BookModel savedBook = bookRepository.save(bookModel);
        BookModel savedBook2 = bookRepository.save(bookModel2);

        Assert.assertEquals(bookRepository.findDistinctByBookAuthorsNameContainingIgnoreCase("Alina").size(), 2);
        Assert.assertEquals(bookRepository.findDistinctByBookAuthorsNameContainingIgnoreCase("Chertkova").size(), 1);
    }
}