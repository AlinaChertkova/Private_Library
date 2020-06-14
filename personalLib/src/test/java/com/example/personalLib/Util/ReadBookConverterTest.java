package com.example.personalLib.Util;

import com.example.personalLib.API.Data.ReadBookData;
import com.example.personalLib.DB.Models.BookModel;
import com.example.personalLib.DB.Models.ReadBookModel;
import com.example.personalLib.DB.Models.Role;
import com.example.personalLib.DB.Models.UserModel;
import com.example.personalLib.Domain.Model.Author;
import com.example.personalLib.Domain.Model.Book;
import com.example.personalLib.Domain.Model.ReadBook;
import com.example.personalLib.Domain.Util.ReadBookConverter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.*;

@RunWith(SpringRunner.class)
public class ReadBookConverterTest {

    @Test
    public void testConvertModelToDomain() {

        final String ISBN = "978-5-389-02467-0";
        final String title = "June";
        final String description = "Drama";
        final String coverLink = "link";
        final double avgRating = 4.1;

        final BookModel bookModel = new BookModel(ISBN, title, description, coverLink, avgRating, 2);

        final String login = "Alina";
        final String name = "Alina";
        final String password = "12345678";
        final LocalDateTime registrationDate = LocalDateTime.now();

        final UserModel userModel = new UserModel(login, name, password, registrationDate);
        Set<Role> r  = new HashSet<>();
        r.add(Role.USER);
        userModel.setRoles(r);

        final ReadBookModel readBookModel = new ReadBookModel(bookModel, userModel, 12.45);

        final ReadBook readBook = ReadBookConverter.convertToReadBookDomain(readBookModel);

        double myPrecision = 0.0001;

        Assert.assertNotNull(readBook);
        Assert.assertEquals(readBook.getId(), readBookModel.getId());
        Assert.assertEquals(readBook.getMark(), myPrecision, readBookModel.getMark());

        Assert.assertEquals(readBook.getBook().getId(), bookModel.getId());
        Assert.assertEquals(readBook.getBook().getISBN(), bookModel.getISBN());
        Assert.assertEquals(readBook.getBook().getTitle(), bookModel.getTitle());
        Assert.assertEquals(readBook.getBook().getDescription(), bookModel.getDescription());
        Assert.assertEquals(readBook.getBook().getCoverLink(), bookModel.getCoverLink());
        Assert.assertEquals(readBook.getBook().getAvgRating(), myPrecision,  bookModel.getAvgRating());


    }

    @Test
    public void testConvertListModelToListDomain() {

        final BookModel bookModel = new BookModel("book1", "book1", "book1", "book1", 123, 2);
        final BookModel bookModel2 = new BookModel("book2", "book2", "book2", "book2", 12.45, 2);

        final UserModel userModel = new UserModel("user1", "user1", "user1", LocalDateTime.now());
        Set<Role> r  = new HashSet<>();
        r.add(Role.USER);
        userModel.setRoles(r);

        final UserModel userModel2 = new UserModel("user2", "user2", "user2", LocalDateTime.now());
        Set<Role> r2  = new HashSet<>();
        r2.add(Role.ADMIN);
        userModel2.setRoles(r2);

        List<ReadBookModel> models = new ArrayList<>();
        models.add(new ReadBookModel(bookModel, userModel, 12.45));
        models.add(new ReadBookModel(bookModel2, userModel2, 4.36));

        List<ReadBook> listReadBook = new ArrayList<>(ReadBookConverter.convertToReadBookDomainList(models));

        Assert.assertNotNull(listReadBook);
        Assert.assertFalse(listReadBook.isEmpty());

        double myPrecision = 0.0001;

        Assert.assertEquals(listReadBook.get(0).getId(), models.get(0).getId());
        Assert.assertEquals(listReadBook.get(0).getMark(), myPrecision, models.get(0).getMark());

        Assert.assertEquals(listReadBook.get(1).getId(), models.get(1).getId());
        Assert.assertEquals(listReadBook.get(1).getMark(), myPrecision, models.get(1).getMark());

        Assert.assertEquals(listReadBook.get(0).getBook().getId(), bookModel.getId());
        Assert.assertEquals(listReadBook.get(0).getBook().getISBN(), bookModel.getISBN());
        Assert.assertEquals(listReadBook.get(0).getBook().getTitle(), bookModel.getTitle());
        Assert.assertEquals(listReadBook.get(0).getBook().getDescription(), bookModel.getDescription());
        Assert.assertEquals(listReadBook.get(0).getBook().getCoverLink(), bookModel.getCoverLink());
        Assert.assertEquals(listReadBook.get(0).getBook().getAvgRating(), myPrecision,  bookModel.getAvgRating());

        Assert.assertEquals(listReadBook.get(1).getBook().getId(), bookModel2.getId());
        Assert.assertEquals(listReadBook.get(1).getBook().getISBN(), bookModel2.getISBN());
        Assert.assertEquals(listReadBook.get(1).getBook().getTitle(), bookModel2.getTitle());
        Assert.assertEquals(listReadBook.get(1).getBook().getDescription(), bookModel2.getDescription());
        Assert.assertEquals(listReadBook.get(1).getBook().getCoverLink(), bookModel2.getCoverLink());
        Assert.assertEquals(listReadBook.get(1).getBook().getAvgRating(), myPrecision,  bookModel2.getAvgRating());
    }

    @Test
    public void testConvertDomainToDTO() {

        final String nameA = "Diana Wiynne Jones";
        final String descriptionA = "Storytailer";

        Author author =  Author.builder()
                .id(1L)
                .name(nameA)
                .description(descriptionA).build();

        final String ISBN = "978-5-389-02467-0";
        final String title = "June";
        final String description = "Drama";
        final String coverLink = "link";
        final double avgRating = 4.1;

        Book book =  Book.builder()
                .id(1L)
                .ISBN(ISBN)
                .Title(title)
                .description(description)
                .coverLink(coverLink)
                .bookAuthors(Arrays.asList(author))
                .avgRating(avgRating).build();

        ReadBook readBook = ReadBook.builder()
                .id(1L)
                .mark(12.23)
                .book(book).build();

        final ReadBookData readBookData = ReadBookConverter.convertToReadBookDTO(readBook);

        double myPrecision = 0.0001;

        Assert.assertNotNull(readBook);
        Assert.assertEquals(readBook.getId(), readBookData.getId());
        Assert.assertEquals(readBook.getMark(), myPrecision, readBookData.getMark());

        Assert.assertEquals(readBookData.getBook().getId(), book.getId());
        Assert.assertEquals(readBookData.getBook().getISBN(), book.getISBN());
        Assert.assertEquals(readBookData.getBook().getTitle(), book.getTitle());
        Assert.assertEquals(readBookData.getBook().getDescription(), book.getDescription());
        Assert.assertEquals(readBookData.getBook().getCoverLink(), book.getCoverLink());
        Assert.assertEquals(readBookData.getBook().getAvgRating(), myPrecision,  book.getAvgRating());
    }

    @Test
    public void testConvertListDomainTOListDTO() {
        final String nameA = "Diana Wiynne Jones";
        final String descriptionA = "Storytailer";

        Author author =  Author.builder()
                .id(1L)
                .name(nameA)
                .description(descriptionA).build();

        Book book =  Book.builder()
                .id(1L)
                .ISBN("test1")
                .Title("test1")
                .description("test1")
                .coverLink("test1")
                .bookAuthors(Arrays.asList(author))
                .avgRating(3.2).build();

        Book book2 =  Book.builder()
                .id(2L)
                .ISBN("test2")
                .Title("test2")
                .description("test2")
                .coverLink("test2")
                .bookAuthors(Arrays.asList(author))
                .avgRating(5.3).build();

        List<ReadBook> readBooks = new ArrayList<>();
        readBooks.add(ReadBook.builder().id(1L).mark(78).book(book).build());
        readBooks.add(ReadBook.builder().id(1L).mark(12.96).book(book2).build());

        List<ReadBookData> listReadBookData = new ArrayList<>(ReadBookConverter.convertToReadBookDTOList(readBooks));

        Assert.assertNotNull(listReadBookData);
        Assert.assertFalse(listReadBookData.isEmpty());

        double myPrecision = 0.0001;

        Assert.assertEquals(listReadBookData.get(0).getId(), readBooks.get(0).getId());
        Assert.assertEquals(listReadBookData.get(0).getMark(), myPrecision, readBooks.get(0).getMark());

        Assert.assertEquals(listReadBookData.get(1).getId(), readBooks.get(1).getId());
        Assert.assertEquals(listReadBookData.get(1).getMark(), myPrecision, readBooks.get(1).getMark());

        Assert.assertEquals(listReadBookData.get(0).getBook().getId(), book.getId());
        Assert.assertEquals(listReadBookData.get(0).getBook().getISBN(), book.getISBN());
        Assert.assertEquals(listReadBookData.get(0).getBook().getTitle(), book.getTitle());
        Assert.assertEquals(listReadBookData.get(0).getBook().getDescription(), book.getDescription());
        Assert.assertEquals(listReadBookData.get(0).getBook().getCoverLink(), book.getCoverLink());
        Assert.assertEquals(listReadBookData.get(0).getBook().getAvgRating(), myPrecision,  book.getAvgRating());

        Assert.assertEquals(listReadBookData.get(1).getBook().getId(), book2.getId());
        Assert.assertEquals(listReadBookData.get(1).getBook().getISBN(), book2.getISBN());
        Assert.assertEquals(listReadBookData.get(1).getBook().getTitle(), book2.getTitle());
        Assert.assertEquals(listReadBookData.get(1).getBook().getDescription(), book2.getDescription());
        Assert.assertEquals(listReadBookData.get(1).getBook().getCoverLink(), book2.getCoverLink());
        Assert.assertEquals(listReadBookData.get(1).getBook().getAvgRating(), myPrecision,  book2.getAvgRating());
    }
}
