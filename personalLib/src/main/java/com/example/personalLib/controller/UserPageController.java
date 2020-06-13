package com.example.personalLib.controller;

import com.example.personalLib.API.Data.ReadBookData;
import com.example.personalLib.API.Data.ReviewData;
import com.example.personalLib.API.Data.UserData;
import com.example.personalLib.Domain.Exceptions.BookNotFoundException;
import com.example.personalLib.Domain.Exceptions.UserNotFoundException;
import com.example.personalLib.Domain.Services.Reader.ReaderService;
import com.example.personalLib.Domain.Util.ReadBookConverter;
import com.example.personalLib.Domain.Util.ReviewConverter;
import com.example.personalLib.Domain.Util.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class UserPageController {
    @Autowired
    private ReaderService readerService;

    @GetMapping("/mypage")
    public String greeting(Map<String, Object> model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/";
        }

        try {
            String currentUserName = authentication.getName();
            UserData user = UserConverter.convertToUserDTO(readerService.findUserByLogin(currentUserName));
            List<ReadBookData> books = ReadBookConverter.convertToReadBookDTOList(readerService.getAllReadBooksByUserId(user.getId()));
            model.put("books", books);
            model.put("user", user);
            model.put("bookCount", books.size());

            List<ReviewData> reviews = ReviewConverter.convertToReviewDTOList(readerService.getAllReviewsByUserId(user.getId()));
            model.put("reviews", reviews);
            model.put("reviewCount", reviews.size());

        } catch (UserNotFoundException e) {

        }
        return "userPage";
    }
}
