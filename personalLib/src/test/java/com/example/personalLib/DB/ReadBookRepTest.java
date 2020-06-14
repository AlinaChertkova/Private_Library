package com.example.personalLib.DB;

import com.example.personalLib.DB.Models.*;
import com.example.personalLib.DB.Repository.BookRepository;
import com.example.personalLib.DB.Repository.ReadBookRepository;
import com.example.personalLib.DB.Repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class ReadBookRepTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReadBookRepository readBookRepository;

    @Test
    public void testSaveReadBook() {

        final String login = "Alina";
        final String name = "Alina";
        final String password = "12345678";
        final LocalDateTime registrationDate = LocalDateTime.now();

        final UserModel userModel = new UserModel(login, name, password, registrationDate);
        userModel.setRoles(Collections.singleton(Role.USER));

        final String ISBN = "978-5-389-02467-0";
        final String title = "June";
        final String description = "Drama";
        final String coverLink = "link";
        final double avgRating = 4.1;

        final BookModel bookModel = new BookModel(ISBN, title, description, coverLink, avgRating, 2);
        final BookModel savedBook = bookRepository.save(bookModel);

        final ReadBookModel readBook = new ReadBookModel(bookModel, userModel, 12.3);

        final ReadBookModel savedReadBook = readBookRepository.save(readBook);

        double precision = 0.0001;
        Assert.assertNotNull(savedReadBook.getId());
        Assert.assertEquals(savedReadBook.getMark(), precision, readBook.getMark());

        Assert.assertNotNull(savedReadBook.getBook());
        Assert.assertEquals(savedReadBook.getBook().getId(), bookModel.getId());

        Assert.assertNotNull(savedReadBook.getUser());
        Assert.assertEquals(savedReadBook.getUser().getId(), userModel.getId());
    }

    @Test
    public void testDeleteReadBook() {

        final String login = "Alina";
        final String name = "Alina";
        final String password = "12345678";
        final LocalDateTime registrationDate = LocalDateTime.now();

        final UserModel userModel = new UserModel(login, name, password, registrationDate);
        userModel.setRoles(Collections.singleton(Role.USER));
        final UserModel savedUser = userRepository.save(userModel);

        final String ISBN = "978-5-389-02467-0";
        final String title = "June";
        final String description = "Drama";
        final String coverLink = "link";
        final double avgRating = 4.1;

        final BookModel bookModel = new BookModel(ISBN, title, description, coverLink, avgRating, 2);
        final BookModel savedBook = bookRepository.save(bookModel);

        final ReadBookModel readBook = new ReadBookModel(bookModel, userModel, 12.3);

        final ReadBookModel savedReadBook = readBookRepository.save(readBook);

        Optional<ReadBookModel> optSelectedValue = readBookRepository.findById(savedReadBook.getId());
        Assert.assertTrue(optSelectedValue.isPresent());

        final ReadBookModel selectedValue = optSelectedValue.get();
        readBookRepository.delete(selectedValue);

        Optional<ReadBookModel> optSelectedValue2 = readBookRepository.findById(savedReadBook.getId());
        Assert.assertFalse(optSelectedValue2.isPresent());

        Optional<BookModel> optSelectedValueBook = bookRepository.findById(savedBook.getId());
        Assert.assertTrue(optSelectedValueBook.isPresent());

        Optional<UserModel> optSelectedValueUser = userRepository.findById(savedUser.getId());
        Assert.assertTrue(optSelectedValueUser.isPresent());
    }

    @Test
    public void testUpdateEdition() {
        //Save books
        final String ISBN = "978-5-389-02467-0";
        final String title = "June";
        final String bookDescription = "Drama";
        final String coverLink = "link";
        final double avgRating = 4.1;

        final BookModel bookModel = new BookModel(ISBN, title, bookDescription, coverLink, avgRating, 2);
        final BookModel savedBook = bookRepository.save(bookModel);

        final BookModel bookModel2 = new BookModel("new ISBN", "new title", "new description", "new link", 123.45, 2);
        final BookModel savedBook2 = bookRepository.save(bookModel2);

        //Save publishing
        final String login = "Alina";
        final String name = "Alina";
        final String password = "12345678";
        final LocalDateTime registrationDate = LocalDateTime.now();

        final UserModel userModel = new UserModel(login, name, password, registrationDate);
        userModel.setRoles(Collections.singleton(Role.USER));

        final UserModel savedUser = userRepository.save(userModel);

        final UserModel userModel2 = new UserModel("new login", "Alya", "qwerty", registrationDate);
        userModel2.setRoles(Collections.singleton(Role.USER));
        final UserModel savedUser2 = userRepository.save(userModel2);

        //Save Read Book
        final ReadBookModel readBook = new ReadBookModel(bookModel, userModel, 122);
        final ReadBookModel sevedReadBook = readBookRepository.save(readBook);

        Optional<ReadBookModel> optSelectedValue = readBookRepository.findById(sevedReadBook.getId());
        Assert.assertTrue(optSelectedValue.isPresent());
        //Update
        final ReadBookModel selectedValue = optSelectedValue.get();

        selectedValue.setBook(bookModel2);
        selectedValue.setUser(userModel2);

        final ReadBookModel updated = readBookRepository.save(readBook);

        Assert.assertEquals(selectedValue.getId(), updated.getId());
        Assert.assertEquals(selectedValue.getBook(), updated.getBook());
        Assert.assertEquals(selectedValue.getUser(), updated.getUser());

        //Check saved book is still present
        Optional<BookModel> optSelectedValueBook = bookRepository.findById(savedBook.getId());
        Assert.assertTrue(optSelectedValueBook.isPresent());

        //Check user is still present
        Optional<UserModel> optSelectedValueUser = userRepository.findById(savedUser.getId());
        Assert.assertTrue(optSelectedValueUser.isPresent());
    }
}
