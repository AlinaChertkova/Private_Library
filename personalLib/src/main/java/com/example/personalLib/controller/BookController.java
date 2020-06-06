package com.example.personalLib.controller;

import com.example.personalLib.API.Data.BookData;
import com.example.personalLib.API.Data.GenreData;
import com.example.personalLib.API.Data.ReviewData;
import com.example.personalLib.Domain.Exceptions.BookNotFoundException;
import com.example.personalLib.Domain.Exceptions.ReviewNotFoundException;
import com.example.personalLib.Domain.Exceptions.UserNotFoundException;
import com.example.personalLib.Domain.Services.Reader.ReaderService;
import com.example.personalLib.Domain.Util.BookConverter;
import com.example.personalLib.Domain.Util.GenreConverter;
import com.example.personalLib.Domain.Util.ReviewConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Controller
public class BookController {
    @Autowired
    private ReaderService readerService;

    @GetMapping("/book/{id}")
    public String book(@PathVariable String id, Map<String, Object> model) {

        try {
            Long bookId = Long.valueOf(id);
            BookData currentBook = BookConverter.convertToBookDTO(readerService.getBookById(bookId));

            List<ReviewData> reviews = ReviewConverter.convertToReviewDTOList(readerService.getAllReviewsByBookId(currentBook.getId()));
            List<GenreData> genres = GenreConverter.convertToGenreDTOList(readerService.getAllGenresByBookId(currentBook.getId()));

            model.put("reviews", reviews);
            model.put("book", currentBook);
            model.put("genres", genres);

            return "bookPage";
        } catch (BookNotFoundException | UserNotFoundException e) {

        }
        List<BookData> books = BookConverter.convertToBookDTOList(readerService.getAllBooks());

        model.put("books", books);
        return "main";
    }
}
