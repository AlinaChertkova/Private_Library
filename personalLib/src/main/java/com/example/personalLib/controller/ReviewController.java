package com.example.personalLib.controller;

import com.example.personalLib.API.AjaxResponce;
import com.example.personalLib.API.Data.ReviewData;
import com.example.personalLib.Domain.Exceptions.ReviewNotFoundException;
import com.example.personalLib.Domain.Services.Reader.ReaderService;
import com.example.personalLib.Domain.Util.ReviewConverter;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import java.util.*;

@Controller
public class ReviewController {
    @Autowired
    private ReaderService readerService;

    @Autowired
    private ViewResolver viewResolver;

//    @PostMapping("/review/getReview")
//    public AjaxResponce<String> book(String id, Map<String, Object> model) {
//        Long reviewId = Long.valueOf(id);
//        String resp = getReviewModalBlock(id, model);
//        AjaxResponce<String> responce = new AjaxResponce<>("secces", resp);
//        return responce;
////        return "reviewModalContent";
//    }

    @PostMapping("/review/getReview")
    @ResponseBody
    public AjaxResponce<String> book(HttpServletRequest request, HttpServletResponse response, String id, Map<String, Object> model) {
        Long reviewId = Long.valueOf(id);
//        List<String> a = new ArrayList<String>();
//        a.add("qwe");
//        a.add("qwe2");
//        return a;

        AjaxResponce<String> responce = new HashMap<>();
        try {
            ReviewData currentReview = ReviewConverter.convertToReviewDTO(readerService.getReviewById(reviewId));
            model.put("review", currentReview);
//            ModelAndView a = new ModelAndView(model, "reviewModalContent.ftl");

            View view = this.viewResolver.resolveViewName("reviewModalContent", Locale.ENGLISH);

            ContentCachingResponseWrapper mockResponse = new ContentCachingResponseWrapper(response);

            view.render(model, request, mockResponse);

            byte[] responseArray = mockResponse.getContentAsByteArray();
            String responseStr = new String(responseArray, mockResponse.getCharacterEncoding());

            qwe.put("html", responseStr);
            return qwe;

            //return "reviewModalContent";
//            ReviewNotFoundException |
        } catch ( Exception e) {
//            return "notification";
            qwe.put("error", e.getMessage());
        }
        return qwe;
//        return "reviewModalContent";
//        return responce;
        //return "reviewModalContent";
    }

    public String getReviewModalBlock(String id, Map<String, Object> model) {
        Long reviewId = Long.valueOf(id);
//        AjaxResponce<String> responce;
        try {
            ReviewData currentReview = ReviewConverter.convertToReviewDTO(readerService.getReviewById(reviewId));
            model.put("review", currentReview);
        } catch (ReviewNotFoundException e) {
//            model.put("message", e.getMessage());
//            responce  = new AjaxResponce<String>("fail", "notification");
        }
//        return responce;
        return "reviewModalContent";
    }
}
