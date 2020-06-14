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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.*;

import static com.example.personalLib.Security.UserSecurityUtil.hasAdminRole;

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
        ReviewData currentReview = null;
        try {
            if (!id.isEmpty()) {
                reviewId = Long.valueOf(id);
                currentReview = ReviewConverter.convertToReviewDTO(readerService.getReviewById(reviewId));
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
                path = "/book/mark";
            }

            if (type.equals("edit") || type.equals("user-edit")) {
                if (UserSecurityUtil.hasUserRole()) {
                    UserData curUser = UserConverter.convertToUserDTO(readerService.findUserByLogin(UserSecurityUtil.getCurrentUserLogin()));
                    Long userId = curUser.getId();

                    if (!currentReview.getUser().getId().equals(userId) && !UserSecurityUtil.hasAdminRole()) {
                        throw new Exception("Невозможно изменить");
                    }
                } else {
                    throw new Exception("Войдите, чтобы продолжить");
                }
                title = "Редактировать рецензию пользователя";
                path = "/review/update";
                view = this.viewResolver.resolveViewName("editReviewModalContent", Locale.ENGLISH);
                model.put("type", type);
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
    public AjaxResponce<String> updateReview(HttpServletRequest request, HttpServletResponse response, String id, String text, Double mark, String type, String bookId, Map<String, Object> model) {
        AjaxResponce<String> responce;
        try {
            Long reviewId = Long.valueOf(id);
            Long book = Long.valueOf(bookId);
            if (readerService.updateReview(reviewId, text, mark) == null) {
               throw new Exception("Невозможно обновить");
            }
            List<ReviewData> reviews = null;

            String viewName = "reviewBlock";
            if (type.equals("user-edit")) {
                viewName = "userReviews";
                UserData user = null;
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (!(authentication instanceof AnonymousAuthenticationToken)) {
                    String currentUserName = authentication.getName();
                    user = UserConverter.convertToUserDTO(readerService.findUserByLogin(currentUserName));
                }

                reviews = ReviewConverter.convertToReviewDTOList(readerService.getAllReviewsByUserId(user.getId()));
            } else if (type.equals("edit")) {
                reviews = ReviewConverter.convertToReviewDTOList(readerService.getAllReviewsByBookId(book));
            }
            View view = this.viewResolver.resolveViewName(viewName, Locale.ENGLISH);

            model.put("reviews", reviews);

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

    @PostMapping("/review/add")
    @ResponseBody
    public AjaxResponce<String> addReview(HttpServletRequest request, HttpServletResponse response, String text, Double mark, String bookId, Map<String, Object> model) {
        AjaxResponce<String> responce;
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

            View view = this.viewResolver.resolveViewName("reviewBlock", Locale.ENGLISH);
            List<ReviewData> reviews = ReviewConverter.convertToReviewDTOList(readerService.getAllReviewsByBookId(book));
            model.put("reviews", reviews);

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

    @DeleteMapping("/review/delete")
    @ResponseBody
    public AjaxResponce<String> deleteReview(HttpServletRequest request, HttpServletResponse response, String id, String bookId, Map<String, Object> model) {
        AjaxResponce<String> responce;
        try {
            Long reviewId = Long.valueOf(id);
            Long book = Long.valueOf(bookId);
            readerService.deleteReview(reviewId);

            String viewName = "reviewBlock";
            List<ReviewData> reviews = ReviewConverter.convertToReviewDTOList(readerService.getAllReviewsByBookId(book));;

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
}
