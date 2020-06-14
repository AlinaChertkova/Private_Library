package com.example.personalLib.controller;

import com.example.personalLib.API.Data.BookData;
import com.example.personalLib.Domain.Services.Reader.ReaderService;
import com.example.personalLib.Domain.Util.BookConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MainController {
    @Autowired
    private ReaderService readerService;

    @GetMapping("/")
    public String main(Map<String, Object> model) {
        List<BookData> books = BookConverter.convertToBookDTOList(readerService.getAllBooks());

        model.put("books", books);
        return "main";
    }

    @GetMapping("/search")
    public String login(String searchParam, Map<String, Object> model) {
        List<BookData> books = BookConverter.convertToBookDTOList(readerService.getAllBooks());
        searchParam = searchParam.trim();
        if (searchParam != null && !searchParam.isEmpty()) {
            books = BookConverter.convertToBookDTOList(readerService.getBooksByTitle(searchParam));
            List<BookData> books2 = BookConverter.convertToBookDTOList(readerService.getBooksByAuthorName(searchParam));

            for (BookData book2 : books2){
                if (!books.contains(book2))
                    books.add(book2);
            }

            books.addAll(BookConverter.convertToBookDTOList(readerService.getBookByISBN(searchParam)));
        }
        model.put("books", books);
        return "search";
    }
}
