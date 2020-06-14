package com.example.personalLib.Util;

import com.example.personalLib.API.Data.BookData;
import com.example.personalLib.DB.Models.AuthorModel;
import com.example.personalLib.DB.Models.BookModel;
import com.example.personalLib.Domain.Model.Author;
import com.example.personalLib.Domain.Model.Book;
import com.example.personalLib.Domain.Util.BookConverter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
public class BookConverterTest {

    @Test
    public void testConvertModelToDomain() {

        final String ISBN = "978-5-389-02467-0";
        final String title = "June";
        final String description = "Drama";
        final String coverLink = "link";
        final double avgRating = 4.1;

        final String nameA = "Diana Wiynne Jones";
        final String descriptionA = "Storytailer";

        final AuthorModel authorModel = new AuthorModel(nameA, descriptionA);
        authorModel.setId(1L);

        final AuthorModel authorModel2 = new AuthorModel("test", "test");
        authorModel.setId(2L);

        final BookModel bookModel = new BookModel(ISBN, title, description, coverLink, avgRating, 2);
        bookModel.setId(1L);
        bookModel.setBookAuthors(Arrays.asList(authorModel, authorModel2));

        final Book book = BookConverter.convertToBookDomain(bookModel);

        double myPrecision = 0.0001;

        Assert.assertNotNull(book);
        Assert.assertEquals(book.getId(), bookModel.getId());
        Assert.assertEquals(book.getISBN(), bookModel.getISBN());
        Assert.assertEquals(book.getTitle(), bookModel.getTitle());
        Assert.assertEquals(book.getDescription(), bookModel.getDescription());
        Assert.assertEquals(book.getCoverLink(), bookModel.getCoverLink());
        Assert.assertEquals(book.getAvgRating(), myPrecision,  bookModel.getAvgRating());

        Assert.assertEquals(book.getBookAuthors().get(0).getDescription(), authorModel.getDescription());
        Assert.assertEquals(book.getBookAuthors().get(0).getName(), authorModel.getName());
        Assert.assertEquals(book.getBookAuthors().get(0).getId(), authorModel.getId());

        Assert.assertEquals(book.getBookAuthors().get(1).getDescription(), authorModel2.getDescription());
        Assert.assertEquals(book.getBookAuthors().get(1).getName(), authorModel2.getName());
        Assert.assertEquals(book.getBookAuthors().get(1).getId(), authorModel2.getId());
    }

    @Test
    public void testConvertListModelToListDomain() {

        final AuthorModel authorModel = new AuthorModel("test1", "test1");
        authorModel.setId(1L);

        final AuthorModel authorModel2 = new AuthorModel("test1", "test1");
        authorModel.setId(2L);

        final AuthorModel authorModel3 = new AuthorModel("test1", "test1");
        authorModel.setId(3L);

        final AuthorModel authorModel4 = new AuthorModel("test1", "test1");
        authorModel.setId(4L);

        List<BookModel> models = new ArrayList<>();
        BookModel madel = new BookModel("test1", "test1", "test1", "test1", 123, 2);
        madel.setBookAuthors(Arrays.asList(authorModel, authorModel2));
        models.add(madel);

        BookModel madel2 = new BookModel("test2", "test2", "test2", "test2", 123, 2);
        madel2.setBookAuthors(Arrays.asList(authorModel3, authorModel4));
        models.add(madel2);

        List<Book> listBook = new ArrayList<>(BookConverter.convertToBookDomainList(models));

        Assert.assertNotNull(listBook);
        Assert.assertFalse(listBook.isEmpty());

        double myPrecision = 0.0001;

        Assert.assertEquals(listBook.get(0).getISBN(), models.get(0).getISBN());
        Assert.assertEquals(listBook.get(0).getDescription(), models.get(0).getDescription());
        Assert.assertEquals(listBook.get(0).getTitle(), models.get(0).getTitle());
        Assert.assertEquals(listBook.get(0).getCoverLink(), models.get(0).getCoverLink());
        Assert.assertEquals(listBook.get(0).getAvgRating(), myPrecision, models.get(0).getAvgRating());

        Assert.assertEquals(listBook.get(1).getISBN(), models.get(1).getISBN());
        Assert.assertEquals(listBook.get(1).getDescription(), models.get(1).getDescription());
        Assert.assertEquals(listBook.get(1).getTitle(), models.get(1).getTitle());
        Assert.assertEquals(listBook.get(1).getCoverLink(), models.get(1).getCoverLink());
        Assert.assertEquals(listBook.get(1).getAvgRating(), myPrecision, models.get(1).getAvgRating());

        Assert.assertEquals(listBook.get(0).getBookAuthors().get(0).getDescription(), authorModel.getDescription());
        Assert.assertEquals(listBook.get(0).getBookAuthors().get(0).getName(), authorModel.getName());
        Assert.assertEquals(listBook.get(0).getBookAuthors().get(0).getId(), authorModel.getId());

        Assert.assertEquals(listBook.get(0).getBookAuthors().get(1).getDescription(), authorModel2.getDescription());
        Assert.assertEquals(listBook.get(0).getBookAuthors().get(1).getName(), authorModel2.getName());
        Assert.assertEquals(listBook.get(0).getBookAuthors().get(1).getId(), authorModel2.getId());

        Assert.assertEquals(listBook.get(1).getBookAuthors().get(0).getDescription(), authorModel3.getDescription());
        Assert.assertEquals(listBook.get(1).getBookAuthors().get(0).getName(), authorModel3.getName());
        Assert.assertEquals(listBook.get(1).getBookAuthors().get(0).getId(), authorModel3.getId());

        Assert.assertEquals(listBook.get(1).getBookAuthors().get(1).getDescription(), authorModel4.getDescription());
        Assert.assertEquals(listBook.get(1).getBookAuthors().get(1).getName(), authorModel4.getName());
        Assert.assertEquals(listBook.get(1).getBookAuthors().get(1).getId(), authorModel4.getId());
    }

    @Test
    public void testConvertDomainToDTO() {

        final String name = "Diana Wiynne Jones";
        final String descriptionA = "Storytailer";

        Author author =  Author.builder()
                .id(1L)
                .name(name)
                .description(descriptionA).build();

        Author author2 =  Author.builder()
                .id(2L)
                .name("test")
                .description("test").build();

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
                .avgRating(avgRating)
                .bookAuthors(Arrays.asList(author, author2)).build();

        final BookData bookData = BookConverter.convertToBookDTO(book);

        double myPrecision = 0.0001;

        Assert.assertNotNull(book);
        Assert.assertEquals(book.getISBN(), bookData.getISBN());
        Assert.assertEquals(book.getTitle(), bookData.getTitle());
        Assert.assertEquals(book.getDescription(), bookData.getDescription());
        Assert.assertEquals(book.getCoverLink(), bookData.getCoverLink());
        Assert.assertEquals(book.getAvgRating(), myPrecision,  bookData.getAvgRating());

        Assert.assertEquals(book.getBookAuthors().get(0).getDescription(), author.getDescription());
        Assert.assertEquals(book.getBookAuthors().get(0).getName(), author.getName());
        Assert.assertEquals(book.getBookAuthors().get(0).getId(), author.getId());

        Assert.assertEquals(book.getBookAuthors().get(1).getDescription(), author2.getDescription());
        Assert.assertEquals(book.getBookAuthors().get(1).getName(), author2.getName());
        Assert.assertEquals(book.getBookAuthors().get(1).getId(), author2.getId());
    }

    @Test
    public void testConvertListDomainTOListDTO() {

        Author author =  Author.builder()
                .id(1L)
                .name("test")
                .description("test").build();

        Author author2 =  Author.builder()
                .id(2L)
                .name("test2")
                .description("test2").build();

        Author author3 =  Author.builder()
                .id(3L)
                .name("test3")
                .description("test3").build();

        Author author4 =  Author.builder()
                .id(4L)
                .name("test4")
                .description("test4").build();

        List<Book> books = new ArrayList<>();
        books.add(Book.builder().id(1L).ISBN("test1").Title("test1").description("test1").coverLink("test1").avgRating(145)
                .bookAuthors(Arrays.asList(author, author2)).build());
        books.add(Book.builder().id(1L).ISBN("test2").Title("test2").description("test2").coverLink("test2").avgRating(12.34)
                .bookAuthors(Arrays.asList(author3, author4)).build());

        List<BookData> listbooksData = new ArrayList<>(BookConverter.convertToBookDTOList(books));

        Assert.assertNotNull(listbooksData);
        Assert.assertFalse(listbooksData.isEmpty());

        double myPrecision = 0.0001;

        Assert.assertEquals(listbooksData.get(0).getISBN(), books.get(0).getISBN());
        Assert.assertEquals(listbooksData.get(0).getDescription(), books.get(0).getDescription());
        Assert.assertEquals(listbooksData.get(0).getTitle(), books.get(0).getTitle());
        Assert.assertEquals(listbooksData.get(0).getCoverLink(), books.get(0).getCoverLink());
        Assert.assertEquals(listbooksData.get(0).getAvgRating(), myPrecision, books.get(0).getAvgRating());

        Assert.assertEquals(listbooksData.get(1).getISBN(), books.get(1).getISBN());
        Assert.assertEquals(listbooksData.get(1).getDescription(), books.get(1).getDescription());
        Assert.assertEquals(listbooksData.get(1).getTitle(), books.get(1).getTitle());
        Assert.assertEquals(listbooksData.get(1).getCoverLink(), books.get(1).getCoverLink());
        Assert.assertEquals(listbooksData.get(1).getAvgRating(), myPrecision, books.get(1).getAvgRating());

        Assert.assertEquals(listbooksData.get(0).getBookAuthors().get(0).getDescription(), author.getDescription());
        Assert.assertEquals(listbooksData.get(0).getBookAuthors().get(0).getName(), author.getName());
        Assert.assertEquals(listbooksData.get(0).getBookAuthors().get(0).getId(), author.getId());

        Assert.assertEquals(listbooksData.get(0).getBookAuthors().get(1).getDescription(), author2.getDescription());
        Assert.assertEquals(listbooksData.get(0).getBookAuthors().get(1).getName(), author2.getName());
        Assert.assertEquals(listbooksData.get(0).getBookAuthors().get(1).getId(), author2.getId());

        Assert.assertEquals(listbooksData.get(1).getBookAuthors().get(0).getDescription(), author3.getDescription());
        Assert.assertEquals(listbooksData.get(1).getBookAuthors().get(0).getName(), author3.getName());
        Assert.assertEquals(listbooksData.get(1).getBookAuthors().get(0).getId(), author3.getId());

        Assert.assertEquals(listbooksData.get(1).getBookAuthors().get(1).getDescription(), author4.getDescription());
        Assert.assertEquals(listbooksData.get(1).getBookAuthors().get(1).getName(), author4.getName());
        Assert.assertEquals(listbooksData.get(1).getBookAuthors().get(1).getId(), author4.getId());
    }
}
