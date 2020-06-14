package com.example.personalLib.controller;

import com.example.personalLib.API.AjaxResponce;
import com.example.personalLib.API.Data.ReadBookData;
import com.example.personalLib.API.Data.ReviewData;
import com.example.personalLib.API.Data.UserData;
import com.example.personalLib.Domain.Exceptions.BookNotFoundException;
import com.example.personalLib.Domain.Exceptions.UserNotFoundException;
import com.example.personalLib.Domain.Services.Reader.ReaderService;
import com.example.personalLib.Domain.Util.ReadBookConverter;
import com.example.personalLib.Domain.Util.ReviewConverter;
import com.example.personalLib.Domain.Util.UserConverter;
import com.example.personalLib.Security.UserSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class UserPageController {
    @Autowired
    private ReaderService readerService;

    @Autowired
    private ViewResolver viewResolver;

    @Autowired
    private AuthenticationManager authManager;

    @GetMapping("/mypage")
    public String getMypage(Map<String, Object> model) {
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

    @DeleteMapping("/user/review/delete")
    @ResponseBody
    public AjaxResponce<String> deleteReview(HttpServletRequest request, HttpServletResponse response, String id, Map<String, Object> model) {
        AjaxResponce<String> responce;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            Long reviewId = Long.valueOf(id);
            readerService.deleteReview(reviewId);

            String currentUserName = authentication.getName();
            UserData user = UserConverter.convertToUserDTO(readerService.findUserByLogin(currentUserName));

            String viewName = "userReviews";
            List<ReviewData> reviews = ReviewConverter.convertToReviewDTOList(readerService.getAllReviewsByUserId(user.getId()));

            View view = this.viewResolver.resolveViewName(viewName, Locale.ENGLISH);

            model.put("reviews", reviews);

            ContentCachingResponseWrapper mockResponse = new ContentCachingResponseWrapper(response);
            view.render(model, request, mockResponse);

            byte[] responseArray = mockResponse.getContentAsByteArray();
            String responseStr = new String(responseArray, mockResponse.getCharacterEncoding());
            responce = new AjaxResponce<>("success", "Рецензия успешно удалена", responseStr);
        } catch (Exception e) {
            responce = new AjaxResponce<>("danger", e.getMessage(), null);
        }

        return responce;
    }

    @PutMapping("/user/update")
    @ResponseBody
    public AjaxResponce<String> updateUser(HttpServletRequest request, HttpServletResponse response, String name, String username, String password, Map<String, Object> model) {
        AjaxResponce<String> responce;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            String currentUserName = authentication.getName();
            UserData user = UserConverter.convertToUserDTO(readerService.findUserByLogin(currentUserName));
            if (!username.equals(user.getLogin()) && readerService.existUserByLogin(username))
            {
                throw new Exception("Пользователь в таким логином уже существует");
            }

            if (password == null) {
                password = user.getPassword();
            }
            readerService.updateUser(user.getId(), username, name, password);
            user = UserConverter.convertToUserDTO(readerService.findUserByLogin(UserSecurityUtil.getCurrentUserLogin()));

            View view = this.viewResolver.resolveViewName("userInfo", Locale.ENGLISH);
            model.put("user", user);
            ContentCachingResponseWrapper mockResponse = new ContentCachingResponseWrapper(response);
            view.render(model, request, mockResponse);
            byte[] responseArray = mockResponse.getContentAsByteArray();
            String responseStr = new String(responseArray, mockResponse.getCharacterEncoding());
            responce = new AjaxResponce<>("success", "Успешно сохранено", responseStr);
        } catch (Exception e) {
            responce = new AjaxResponce<>("danger", e.getMessage(), null);
        }
        return responce;
    }
}
