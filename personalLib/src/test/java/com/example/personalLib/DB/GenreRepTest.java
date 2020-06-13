package com.example.personalLib.DB;

import com.example.personalLib.DB.Models.BookModel;
import com.example.personalLib.DB.Models.GenreModel;
import com.example.personalLib.DB.Repository.BookRepository;
import com.example.personalLib.DB.Repository.GenreRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class GenreRepTest {

    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testSaveGenre() {

        final String name = "Humor";

        final GenreModel genreModel = new GenreModel(name);

        final GenreModel saved = genreRepository.save(genreModel);

        Assert.assertNotNull(saved.getId());
        Assert.assertEquals(saved.getName(), genreModel.getName());

        Optional<GenreModel> optSelectedValue = genreRepository.findById(saved.getId());

        Assert.assertTrue(optSelectedValue.isPresent());

        final GenreModel selectedValue = optSelectedValue.get();

        Assert.assertNotNull(selectedValue.getId());
        Assert.assertEquals(selectedValue.getId(), saved.getId());
        Assert.assertEquals(selectedValue.getName(), genreModel.getName());
    }

    @Test
    public void testUpdateGenre() {

        final String name = "Humor";

        final GenreModel genreModel = new GenreModel(name);

        final GenreModel saved = genreRepository.save(genreModel);

        Optional<GenreModel> optSelectedValue = genreRepository.findById(saved.getId());

        Assert.assertTrue(optSelectedValue.isPresent());

        final GenreModel selectedValue = optSelectedValue.get();

        selectedValue.setName("Detective");

        final GenreModel updated = genreRepository.save(genreModel);

        Assert.assertEquals(selectedValue.getId(), updated.getId());
        Assert.assertEquals(selectedValue.getName(), updated.getName());
    }

    @Test
    public void testDeleteGenre() {

        final String name = "Humor";

        final GenreModel genreModel = new GenreModel(name);

        final GenreModel saved = genreRepository.save(genreModel);

        Optional<GenreModel> optSelectedValue = genreRepository.findById(saved.getId());

        Assert.assertTrue(optSelectedValue.isPresent());

        final GenreModel selectedValue = optSelectedValue.get();

        genreRepository.delete(selectedValue);

        Optional<GenreModel> optSelectedValue2 = genreRepository.findById(saved.getId());

        Assert.assertFalse(optSelectedValue2.isPresent());
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
        GenreModel sevedGenre1 = genreRepository.save(genre1);

        final GenreModel genre2 = new GenreModel( "NonFiction");
        GenreModel sevedGenre2 = genreRepository.save(genre2);

        bookModel.setBookgenres(Arrays.asList(genre1, genre2));
        bookModel2.setBookgenres(Arrays.asList(genre2));

        BookModel savedBook = bookRepository.save(bookModel);
        BookModel savedBook2 = bookRepository.save(bookModel2);

        Assert.assertEquals(genreRepository.findByBooksOfGenreId(savedBook.getId()).size(), 2);
        Assert.assertEquals(genreRepository.findByBooksOfGenreId(savedBook2.getId()).size(), 1);
    }
}
