package com.example.personalLib.controller;

import com.example.personalLib.API.AjaxResponce;
import com.example.personalLib.API.Data.*;
import com.example.personalLib.Domain.Exceptions.BookNotFoundException;
import com.example.personalLib.Domain.Exceptions.ReviewNotFoundException;
import com.example.personalLib.Domain.Exceptions.UserNotFoundException;
import com.example.personalLib.Domain.Model.ReadBook;
import com.example.personalLib.Domain.Services.Reader.ReaderService;
import com.example.personalLib.Domain.Util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
public class BookController {
    @Autowired
    private ReaderService readerService;
    @Autowired
    private ViewResolver viewResolver;

    @GetMapping("/book/{id}")
    public String getBook(@PathVariable String id, Map<String, Object> model) {

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

    @PostMapping("/book/mark")
    @ResponseBody
    public AjaxResponce<String> addToList(Double mark, String bookId, Map<String, Object> model) {
        AjaxResponce<String> responce;
        try {
            UserData user = null;
            Long book = Long.valueOf(bookId);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                String currentUserName = authentication.getName();
                user = UserConverter.convertToUserDTO(readerService.findUserByLogin(currentUserName));
            }
            ReadBook saved = readerService.addToList(user.getId(), book, mark);

            if (saved == null) {
                throw new Exception("Невозможно сохранить");
            }
            responce = new AjaxResponce<>("success", "Добавлено в прочитанное", null);
        } catch (Exception e) {
            responce = new AjaxResponce<>("danger", e.getMessage(), null);
        }
        return responce;
    }

    @PostMapping("/book/getModal")
    @ResponseBody
    public AjaxResponce<String> getModal(HttpServletRequest request, HttpServletResponse response, String id, Double mark, String type,  String bookId, Map<String, Object> model) {
        AjaxResponce<String> responce;
        try {
            Long readBookId = Long.valueOf(id);

            ReadBookData book = ReadBookConverter.convertToReadBookDTO(readerService.getReadBookById(readBookId));
            View view = this.viewResolver.resolveViewName("editMark", Locale.ENGLISH);
            model.put("book", book);
            model.put("title", "Редакторовать оценку");
            ContentCachingResponseWrapper mockResponse = new ContentCachingResponseWrapper(response);
            view.render(model, request, mockResponse);

            byte[] responseArray = mockResponse.getContentAsByteArray();
            String responseStr = new String(responseArray, mockResponse.getCharacterEncoding());
            responce = new AjaxResponce<>("success", null, responseStr);
        } catch (Exception e) {
            responce = new AjaxResponce<>("danger", e.getMessage(), null);
        }
        return responce;
    }

    @PostMapping("/book/update")
    @ResponseBody
    public AjaxResponce<String> updateMark(HttpServletRequest request, HttpServletResponse response, String id, Double mark, String type,  String bookId, Map<String, Object> model) {
        AjaxResponce<String> responce;
        try {
            UserData user = null;
            Long readBookId = Long.valueOf(id);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                String currentUserName = authentication.getName();
                user = UserConverter.convertToUserDTO(readerService.findUserByLogin(currentUserName));
            }

            if (readerService.updateMark(readBookId, mark) == null) {
                throw new Exception("Невозможно сохранить");
            }

            List<ReadBookData> books = ReadBookConverter.convertToReadBookDTOList(readerService.getAllReadBooksByUserId(user.getId()));

            View view = this.viewResolver.resolveViewName("readBooks", Locale.ENGLISH);
            model.put("books", books);
            ContentCachingResponseWrapper mockResponse = new ContentCachingResponseWrapper(response);
            view.render(model, request, mockResponse);

            byte[] responseArray = mockResponse.getContentAsByteArray();
            String responseStr = new String(responseArray, mockResponse.getCharacterEncoding());
            responce = new AjaxResponce<>("success", "Сохранено", responseStr);
        } catch (Exception e) {
            responce = new AjaxResponce<>("danger", e.getMessage(), null);
        }
        return responce;
    }

    @DeleteMapping("/book/delete")
    @ResponseBody
    public AjaxResponce<String> deleteBook(HttpServletRequest request, HttpServletResponse response, String id, Map<String, Object> model) {
        AjaxResponce<String> responce;
        try {
            Long bookId = Long.valueOf(id);
            readerService.deleteReadBook(bookId);

            UserData user = null;
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                String currentUserName = authentication.getName();
                user = UserConverter.convertToUserDTO(readerService.findUserByLogin(currentUserName));
            }

            List<ReadBookData> books = ReadBookConverter.convertToReadBookDTOList(readerService.getAllReadBooksByUserId(user.getId()));
            View view = this.viewResolver.resolveViewName("readBooks", Locale.ENGLISH);
            model.put("books", books);
            ContentCachingResponseWrapper mockResponse = new ContentCachingResponseWrapper(response);
            view.render(model, request, mockResponse);

            byte[] responseArray = mockResponse.getContentAsByteArray();
            String responseStr = new String(responseArray, mockResponse.getCharacterEncoding());
            responce = new AjaxResponce<>("success", "Удалено", responseStr);
        } catch (Exception e) {
            responce = new AjaxResponce<>("danger", e.getMessage(), null);
        }

        return responce;
    }
}
