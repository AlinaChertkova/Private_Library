package com.example.personalLib.controller;

import com.example.personalLib.API.AjaxResponce;
import com.example.personalLib.API.Data.ReviewData;
import com.example.personalLib.API.Data.UserData;
import com.example.personalLib.DB.Models.Role;
import com.example.personalLib.DB.Models.UserModel;
import com.example.personalLib.Domain.Exceptions.ReviewNotFoundException;
import com.example.personalLib.Domain.Model.ReadBook;
import com.example.personalLib.Domain.Model.Review;
import com.example.personalLib.Domain.Services.Reader.ReaderService;
import com.example.personalLib.Domain.Util.ReviewConverter;
import com.example.personalLib.Domain.Util.UserConverter;
import com.example.personalLib.Security.UserSecurityUtil;
import com.vaadin.flow.component.notification.Notification;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class ReviewController {
    @Autowired
    private ReaderService readerService;

    @Autowired
    private ViewResolver viewResolver;

    @PostMapping("/review/getReview")
    @ResponseBody
    public AjaxResponce<String> getModalContent(HttpServletRequest request, HttpServletResponse response, String id, String type, String bookId, Map<String, Object> model) {
        Long reviewId;
        AjaxResponce<String> responce;
        try {
            if (!id.isEmpty()) {
                reviewId = Long.valueOf(id);
                ReviewData currentReview = ReviewConverter.convertToReviewDTO(readerService.getReviewById(reviewId));
                model.put("review", currentReview);
            }
            View view = this.viewResolver.resolveViewName("reviewModalContent", Locale.ENGLISH);
            String title = "Новая рецензия";
            String path = "/review/add";
            if (type.equals("add")) {
                if (!UserSecurityUtil.hasUserRole()) {
                    throw new Exception("Войдите, чтобы продолжить");
                }
                view = this.viewResolver.resolveViewName("addReviewModal", Locale.ENGLISH);
            }

            if (type.equals("mark")) {
                if (!UserSecurityUtil.hasUserRole()) {
                    throw new Exception("Войдите, чтобы продолжить");
                }
                view = this.viewResolver.resolveViewName("markModal", Locale.ENGLISH);
                title = "Добавить в прочитанное";
                path = "/review/mark";
            }

            if (type.equals("edit")) {
                if (!UserSecurityUtil.hasUserRole()) {
                    view = this.viewResolver.resolveViewName("notification", Locale.ENGLISH);
                    throw new Exception("Войдите, чтобы продолжить");
                }
                title = "Редактировать рецензию пользователя";
                path = "/review/update";
                view = this.viewResolver.resolveViewName("editReviewModalContent", Locale.ENGLISH);
            }
            model.put("bookId", bookId);
            model.put("title", title);
            model.put("path", path);
            ContentCachingResponseWrapper mockResponse = new ContentCachingResponseWrapper(response);

            view.render(model, request, mockResponse);

            byte[] responseArray = mockResponse.getContentAsByteArray();
            String responseStr = new String(responseArray, mockResponse.getCharacterEncoding());

            responce = new AjaxResponce<>("success", null, responseStr);
        } catch ( Exception e) {
            responce = new AjaxResponce<>("danger", e.getMessage(), null);
        }
        return responce;
    }

    @PostMapping("/review/update")
    @ResponseBody
    public AjaxResponce<String> updateReview(HttpServletRequest request, HttpServletResponse response, String id, String text, Double mark, String bookId, Map<String, Object> model) {
        AjaxResponce<String> responce;
        try {
            Long reviewId = Long.valueOf(id);
            Long book = Long.valueOf(bookId);
            if (readerService.updateReview(reviewId, text, mark) == null) {
               throw new Exception("Невозможно обновить");
            }

            View view = this.viewResolver.resolveViewName("reviewBlock", Locale.ENGLISH);
            List<ReviewData> reviews = ReviewConverter.convertToReviewDTOList(readerService.getAllReviewsByBookId(book));
            model.put("reviews", reviews);

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

    @PostMapping("/review/add")
    public String addReview(String text, Double mark, String bookId, Map<String, Object> model) {
        try {
            UserData user = null;
            Long book = Long.valueOf(bookId);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                String currentUserName = authentication.getName();
                user = UserConverter.convertToUserDTO(readerService.findUserByLogin(currentUserName));
            }
            Review saved = readerService.createReview(user.getId(), book, text, LocalDateTime.now(), mark);

            if (saved == null) {
                throw new Exception("Невозможно сохранить");
            }
        } catch (Exception e) {
            String message = e.getMessage();
            model.put("message", message);
            return "registration";
        }
        return "redirect:/book/" + bookId;
    }

    @PostMapping("/review/delete")
    public String deleteReview(String id, String bookId, Map<String, Object> model) {
        try {
            Long reviewId = Long.valueOf(id);
            readerService.deleteReview(reviewId);
        } catch (Exception e) {
            String message = e.getMessage();
            model.put("message", message);
            return "registration";
        }
        return "redirect:/book/" + bookId;
    }

    @PostMapping("/review/mark")
    public String addToList(Double mark, String bookId, Map<String, Object> model) {
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
        } catch (Exception e) {
            String message = e.getMessage();
            model.put("message", message);
            return "registration";
        }
        return "redirect:/book/" + bookId;
    }
}
