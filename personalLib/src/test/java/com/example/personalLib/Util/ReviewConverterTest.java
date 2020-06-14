package com.example.personalLib.Util;

import com.example.personalLib.API.Data.ReviewData;
import com.example.personalLib.DB.Models.BookModel;
import com.example.personalLib.DB.Models.ReviewModel;
import com.example.personalLib.DB.Models.Role;
import com.example.personalLib.DB.Models.UserModel;
import com.example.personalLib.Domain.Model.Author;
import com.example.personalLib.Domain.Model.Book;
import com.example.personalLib.Domain.Model.Review;
import com.example.personalLib.Domain.Model.User;
import com.example.personalLib.Domain.Util.ReviewConverter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.*;

@RunWith(SpringRunner.class)
public class ReviewConverterTest {
    @Test
    public void testConvertModelToDomain() {

        final String ISBN = "978-5-389-02467-0";
        final String title = "June";
        final String description = "Drama";
        final String coverLink = "link";
        final double avgRating = 4.1;

        final BookModel bookModel = new BookModel(ISBN, title, description, coverLink, avgRating, 2);

        final String login = "Alina";
        final String name = "Alina";
        final String password = "12345678";
        final LocalDateTime registrationDate = LocalDateTime.now();

        final UserModel userModel = new UserModel(login, name, password, registrationDate);
        Set<Role> r  = new HashSet<>();
        r.add(Role.USER);
        userModel.setRoles(r);

        final ReviewModel reviewModel = new ReviewModel(userModel, bookModel, "text", LocalDateTime.now(), 12.36);

        final Review review = ReviewConverter.convertToReviewDomain(reviewModel);

        double myPrecision = 0.0001;

        Assert.assertNotNull(review);
        Assert.assertEquals(review.getId(), reviewModel.getId());
        Assert.assertEquals(review.getPublishingDate(), reviewModel.getPublishingDate());
        Assert.assertEquals(review.getText(), reviewModel.getText());
        Assert.assertEquals(review.getMark(), myPrecision, reviewModel.getMark());

        Assert.assertEquals(review.getBook().getId(), bookModel.getId());
        Assert.assertEquals(review.getBook().getISBN(), bookModel.getISBN());
        Assert.assertEquals(review.getBook().getTitle(), bookModel.getTitle());
        Assert.assertEquals(review.getBook().getDescription(), bookModel.getDescription());
        Assert.assertEquals(review.getBook().getCoverLink(), bookModel.getCoverLink());
        Assert.assertEquals(review.getBook().getAvgRating(), myPrecision,  bookModel.getAvgRating());

        Assert.assertEquals(review.getUser().getId(), userModel.getId());
        Assert.assertEquals(review.getUser().getName(), userModel.getName());
        Assert.assertEquals(review.getUser().getPassword(), userModel.getPassword());
        Assert.assertEquals(review.getUser().isActive(), userModel.isActive());
        Assert.assertEquals(review.getUser().getRegistrationDate(), userModel.getRegistrationDate());
    }

    @Test
    public void testConvertListModelToListDomain() {

        final int markCount = 4;
        final BookModel bookModel = new BookModel("book1", "book1", "book1", "book1", 123, markCount);
        final BookModel bookModel2 = new BookModel("book2", "book2", "book2", "book2", 12.45, markCount);

        final UserModel userModel = new UserModel("user1", "user1", "user1", LocalDateTime.now());
        Set<Role> r  = new HashSet<>();
        r.add(Role.USER);
        userModel.setRoles(r);

        final UserModel userModel2 = new UserModel("user2", "user2", "user2", LocalDateTime.now());
        Set<Role> r2  = new HashSet<>();
        r2.add(Role.ADMIN);
        userModel2.setRoles(r2);

        List<ReviewModel> models = new ArrayList<>();
        models.add(new ReviewModel(userModel, bookModel, "text1", LocalDateTime.now(), 12.45));
        models.add(new ReviewModel(userModel2, bookModel2,"text2", LocalDateTime.now(), 4.36));

        List<Review> listReview = new ArrayList<>(ReviewConverter.convertToReviewDomainList(models));

        Assert.assertNotNull(listReview);
        Assert.assertFalse(listReview.isEmpty());

        double myPrecision = 0.0001;

        Assert.assertEquals(listReview.get(0).getId(), models.get(0).getId());
        Assert.assertEquals(listReview.get(0).getPublishingDate(), models.get(0).getPublishingDate());
        Assert.assertEquals(listReview.get(0).getText(), models.get(0).getText());
        Assert.assertEquals(listReview.get(0).getMark(), myPrecision, models.get(0).getMark());

        Assert.assertEquals(listReview.get(1).getId(), models.get(1).getId());
        Assert.assertEquals(listReview.get(1).getPublishingDate(), models.get(1).getPublishingDate());
        Assert.assertEquals(listReview.get(1).getText(), models.get(1).getText());
        Assert.assertEquals(listReview.get(1).getMark(), myPrecision, models.get(1).getMark());

        Assert.assertEquals(listReview.get(0).getBook().getId(), bookModel.getId());
        Assert.assertEquals(listReview.get(0).getBook().getISBN(), bookModel.getISBN());
        Assert.assertEquals(listReview.get(0).getBook().getTitle(), bookModel.getTitle());
        Assert.assertEquals(listReview.get(0).getBook().getDescription(), bookModel.getDescription());
        Assert.assertEquals(listReview.get(0).getBook().getCoverLink(), bookModel.getCoverLink());
        Assert.assertEquals(listReview.get(0).getBook().getAvgRating(), myPrecision,  bookModel.getAvgRating());

        Assert.assertEquals(listReview.get(1).getBook().getId(), bookModel2.getId());
        Assert.assertEquals(listReview.get(1).getBook().getISBN(), bookModel2.getISBN());
        Assert.assertEquals(listReview.get(1).getBook().getTitle(), bookModel2.getTitle());
        Assert.assertEquals(listReview.get(1).getBook().getDescription(), bookModel2.getDescription());
        Assert.assertEquals(listReview.get(1).getBook().getCoverLink(), bookModel2.getCoverLink());
        Assert.assertEquals(listReview.get(1).getBook().getAvgRating(), myPrecision,  bookModel2.getAvgRating());

        Assert.assertEquals(listReview.get(0).getUser().getId(), userModel.getId());
        Assert.assertEquals(listReview.get(0).getUser().getName(), userModel.getName());
        Assert.assertEquals(listReview.get(0).getUser().getPassword(), userModel.getPassword());
        Assert.assertEquals(listReview.get(0).getUser().isActive(), userModel.isActive());
        Assert.assertEquals(listReview.get(0).getUser().getRegistrationDate(), userModel.getRegistrationDate());

        Assert.assertEquals(listReview.get(1).getUser().getId(), userModel2.getId());
        Assert.assertEquals(listReview.get(1).getUser().getName(), userModel2.getName());
        Assert.assertEquals(listReview.get(1).getUser().getPassword(), userModel2.getPassword());
        Assert.assertEquals(listReview.get(1).getUser().isActive(), userModel2.isActive());
        Assert.assertEquals(listReview.get(1).getUser().getRegistrationDate(), userModel2.getRegistrationDate());
    }

    @Test
    public void testConvertDomainToDTO() {

        final String nameA = "Diana Wiynne Jones";
        final String descriptionA = "Storytailer";

        Author author =  Author.builder()
                .id(1L)
                .name(nameA)
                .description(descriptionA).build();

        final String ISBN = "978-5-389-02467-0";
        final String title = "June";
        final String description = "Drama";
        final String coverLink = "link";
        final double avgRating = 4.1;

        Book book =  Book.builder()
                .id(1L)
                .ISBN(ISBN)
                .Title(title)
                .description(description)
                .coverLink(coverLink)
                .avgRating(avgRating)
                .bookAuthors(Arrays.asList(author)).build();

        final String login = "Alina";
        final String name = "Alina";
        final String password = "12345678";
        final LocalDateTime registrationDate = LocalDateTime.now();

        User user = User.builder()
                .login(login)
                .name(name)
                .id(1L)
                .password(password)
                .registrationDate(registrationDate)
                .active(true).build();

        Review review = Review.builder()
                .id(1L)
                .mark(12.23)
                .publishingDate(LocalDateTime.now())
                .text("text")
                .book(book)
                .user(user).build();

        final ReviewData reviewData = ReviewConverter.convertToReviewDTO(review);

        double myPrecision = 0.0001;

        Assert.assertNotNull(review);
        Assert.assertEquals(review.getId(), reviewData.getId());
        Assert.assertEquals(review.getPublishingDate(), reviewData.getPublishingDate());
        Assert.assertEquals(review.getText(), reviewData.getText());
        Assert.assertEquals(review.getMark(), myPrecision, reviewData.getMark());

        Assert.assertEquals(review.getBook().getId(), book.getId());
        Assert.assertEquals(review.getBook().getISBN(), book.getISBN());
        Assert.assertEquals(review.getBook().getTitle(), book.getTitle());
        Assert.assertEquals(review.getBook().getDescription(), book.getDescription());
        Assert.assertEquals(review.getBook().getCoverLink(), book.getCoverLink());
        Assert.assertEquals(review.getBook().getAvgRating(), myPrecision,  book.getAvgRating());

        Assert.assertEquals(review.getUser().getId(), user.getId());
        Assert.assertEquals(review.getUser().getName(), user.getName());
        Assert.assertEquals(review.getUser().getPassword(), user.getPassword());
        Assert.assertEquals(review.getUser().isActive(), user.isActive());
        Assert.assertEquals(review.getUser().getRegistrationDate(), user.getRegistrationDate());
    }

    @Test
    public void testConvertListDomainTOListDTO() {

        final String nameA = "Diana Wiynne Jones";
        final String descriptionA = "Storytailer";

        Author author =  Author.builder()
                .id(1L)
                .name(nameA)
                .description(descriptionA).build();

        Book book =  Book.builder()
                .id(1L)
                .ISBN("test1")
                .Title("test1")
                .description("test1")
                .coverLink("test1")
                .bookAuthors(Arrays.asList(author))
                .avgRating(3.2).build();

        Book book2 =  Book.builder()
                .id(2L)
                .ISBN("test2")
                .Title("test2")
                .description("test2")
                .coverLink("test2")
                .bookAuthors(Arrays.asList(author))
                .avgRating(5.3).build();

        User user = User.builder()
                .login("test1")
                .name("test1")
                .id(1L)
                .password("test1")
                .registrationDate(LocalDateTime.now())
                .active(true).build();

        User user2 = User.builder()
                .login("test2")
                .name("test2")
                .id(2L)
                .password("test2")
                .registrationDate(LocalDateTime.now())
                .active(true).build();

        List<Review> review = new ArrayList<>();
        review.add(Review.builder().id(1L).mark(78.0).publishingDate(LocalDateTime.now())
                .book(book).user(user).text("text1").build());
        review.add(Review.builder().id(1L).mark(12.96).publishingDate(LocalDateTime.now())
                .book(book2).user(user2).text("text2").build());

        List<ReviewData> listReviewData = new ArrayList<>(ReviewConverter.convertToReviewDTOList(review));

        Assert.assertNotNull(listReviewData);
        Assert.assertFalse(listReviewData.isEmpty());

        double myPrecision = 0.0001;

        Assert.assertEquals(listReviewData.get(0).getId(), review.get(0).getId());
        Assert.assertEquals(listReviewData.get(0).getPublishingDate(), review.get(0).getPublishingDate());
        Assert.assertEquals(listReviewData.get(0).getText(), review.get(0).getText());
        Assert.assertEquals(listReviewData.get(0).getMark(), myPrecision, review.get(0).getMark());

        Assert.assertEquals(listReviewData.get(1).getId(), review.get(1).getId());
        Assert.assertEquals(listReviewData.get(1).getPublishingDate(), review.get(1).getPublishingDate());
        Assert.assertEquals(listReviewData.get(1).getText(), review.get(1).getText());
        Assert.assertEquals(listReviewData.get(1).getMark(), myPrecision, review.get(1).getMark());

        Assert.assertEquals(listReviewData.get(0).getBook().getId(), book.getId());
        Assert.assertEquals(listReviewData.get(0).getBook().getISBN(), book.getISBN());
        Assert.assertEquals(listReviewData.get(0).getBook().getTitle(), book.getTitle());
        Assert.assertEquals(listReviewData.get(0).getBook().getDescription(), book.getDescription());
        Assert.assertEquals(listReviewData.get(0).getBook().getCoverLink(), book.getCoverLink());
        Assert.assertEquals(listReviewData.get(0).getBook().getAvgRating(), myPrecision,  book.getAvgRating());

        Assert.assertEquals(listReviewData.get(1).getBook().getId(), book2.getId());
        Assert.assertEquals(listReviewData.get(1).getBook().getISBN(), book2.getISBN());
        Assert.assertEquals(listReviewData.get(1).getBook().getTitle(), book2.getTitle());
        Assert.assertEquals(listReviewData.get(1).getBook().getDescription(), book2.getDescription());
        Assert.assertEquals(listReviewData.get(1).getBook().getCoverLink(), book2.getCoverLink());
        Assert.assertEquals(listReviewData.get(1).getBook().getAvgRating(), myPrecision,  book2.getAvgRating());

        Assert.assertEquals(listReviewData.get(0).getUser().getId(), user.getId());
        Assert.assertEquals(listReviewData.get(0).getUser().getLogin(), user.getLogin());
        Assert.assertEquals(listReviewData.get(0).getUser().getName(), user.getName());
        Assert.assertEquals(listReviewData.get(0).getUser().getRegistrationDate(), user.getRegistrationDate());
        Assert.assertEquals(listReviewData.get(0).getUser().isActive(), user.isActive());

        Assert.assertEquals(listReviewData.get(1).getUser().getId(), user2.getId());
        Assert.assertEquals(listReviewData.get(1).getUser().getLogin(), user2.getLogin());
        Assert.assertEquals(listReviewData.get(1).getUser().getName(), user2.getName());
        Assert.assertEquals(listReviewData.get(1).getUser().getRegistrationDate(), user2.getRegistrationDate());
        Assert.assertEquals(listReviewData.get(1).getUser().isActive(), user2.isActive());
    }
}
