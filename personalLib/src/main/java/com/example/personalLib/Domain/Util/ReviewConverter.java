package com.example.personalLib.Domain.Util;

import com.example.personalLib.API.Data.ReviewData;
import com.example.personalLib.DB.Models.PublishingModel;
import com.example.personalLib.DB.Models.ReviewModel;
import com.example.personalLib.Domain.Model.Book;
import com.example.personalLib.Domain.Model.Publishing;
import com.example.personalLib.Domain.Model.Review;
import com.example.personalLib.Domain.Model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewConverter {

    public ReviewConverter(){}

    /**
     * Преобразует из модели бд в доменный объект
     * @param review объект бд
     * @return доменный объект
     */

    public static Review convertToReviewDomain (ReviewModel review){

        return Review.builder()
                .id(review.getId())
                .user(UserConverter.convertToUserDomain(review.getUser()))
                .book(BookConverter.convertToBookDomain(review.getBook()))
                .text(review.getText())
                .publishingDate(review.getPublishingDate())
                .mark(review.getMark()).build();
    }

    /**
     * Преобразует список сущностей в список доменных объектов
     * @param reviewList список сущностей
     * @return Список доменных объктов
     */

    public static List<Review> convertToReviewDomainList (List<ReviewModel> reviewList){
        return reviewList.stream()
                .map(ReviewConverter :: convertToReviewDomain)
                .collect(Collectors.toList());
    }

    /**
     * Преобразует доменный объект в DTO
     * @param review доменный объект
     * @return DTO
     */

    public static ReviewData convertToReviewDTO (Review review){

        return ReviewData.builder()
                .id(review.getId())
                .text(review.getText())
                .user(UserConverter.convertToUserDTO(review.getUser()))
                .book(BookConverter.convertToBookDTO(review.getBook()))
                .publishingDate(review.getPublishingDate())
                .mark(review.getMark()).build();
    }

    /**
     * Преобразует список доменных объектов в список DTO
     * @param reviewList список доменных объектов
     * @return Список DTO
     */

    public static List<ReviewData> convertToReviewDTOList (List<Review> reviewList){
        return reviewList.stream()
                .map(ReviewConverter :: convertToReviewDTO)
                .collect(Collectors.toList());
    }
}
