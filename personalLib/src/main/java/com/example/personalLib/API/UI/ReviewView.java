package com.example.personalLib.API.UI;

import com.example.personalLib.API.Data.BookData;
import com.example.personalLib.API.Data.ReviewData;
import com.example.personalLib.API.Data.UserData;
import com.example.personalLib.Domain.Exceptions.ReviewNotFoundException;
import com.example.personalLib.Domain.Services.Reader.ReaderService;
import com.example.personalLib.Domain.Util.ReviewConverter;
import com.example.personalLib.Domain.Util.UserConverter;
import com.example.personalLib.Security.UserSecurityUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.StringJoiner;

import static com.example.personalLib.API.PageComponents.getHeader;
import static com.example.personalLib.Security.UserSecurityUtil.hasUserRole;

@Route("review")
public class ReviewView extends VerticalLayout implements HasUrlParameter<String> {
    @Autowired
    private ReaderService readerService;

    private Long reviewId;
    private ReviewData currentReview;
    private Label reviewAuthorLabel;
    private Label bookAuthorLabel;
    private Label bookTitleLabel;
    private Label markLabel;
    private TextArea reviewText;

    @Override
    public void setParameter(BeforeEvent event, String parameter) {

        reviewId = Long.valueOf(parameter);

        AppLayout appLayout = new AppLayout();
        appLayout = getHeader(readerService);
        VerticalLayout mainLayout = new VerticalLayout();

        try {
            currentReview = ReviewConverter.convertToReviewDTO(readerService.getReviewById(reviewId));
            reviewAuthorLabel = new Label();
            reviewAuthorLabel.setText("Автор рецензии: " + currentReview.getUser().getLogin());

            bookTitleLabel = new Label();
            bookTitleLabel.setText(currentReview.getBook().getTitle());

            bookAuthorLabel = new Label();
            bookAuthorLabel.setText(getAuthors(currentReview.getBook()));

            markLabel = new Label();
            if (currentReview.getMark() == null)
                markLabel.setText("Без оценки");
            else
                markLabel.setText("Оценка: " + currentReview.getMark() + "/10");

            reviewText = new TextArea();
            reviewText.setValue(currentReview.getText());
            reviewText.setWidth("80%");
            reviewText.setReadOnly(true);

            mainLayout.add(reviewAuthorLabel, bookTitleLabel, bookAuthorLabel, markLabel, reviewText);
            appLayout.setContent(mainLayout);
            add(appLayout);
        } catch (ReviewNotFoundException e) {
            Notification.show(e.getMessage(), 2000, Notification.Position.MIDDLE);
        }
    }

    private String getAuthors(BookData book) {
        StringJoiner joiner = new StringJoiner(", ");
        book.getBookAuthors().stream().forEach( a -> joiner.add(a.getName()));
        return joiner.toString();
    }
}
