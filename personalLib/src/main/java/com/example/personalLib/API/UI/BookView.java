package com.example.personalLib.API.UI;

import com.example.personalLib.API.Data.*;
import com.example.personalLib.API.PageComponents;
import com.example.personalLib.Domain.Exceptions.BookNotFoundException;
import com.example.personalLib.Domain.Exceptions.UserNotFoundException;
import com.example.personalLib.Domain.Services.Reader.ReaderService;
import com.example.personalLib.Domain.Util.BookConverter;
import com.example.personalLib.Domain.Util.GenreConverter;
import com.example.personalLib.Domain.Util.ReviewConverter;
import com.example.personalLib.Domain.Util.UserConverter;
import com.example.personalLib.Security.UserSecurityUtil;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.StringJoiner;

import static com.example.personalLib.API.PageComponents.getHeader;

@Route("book")
public class BookView extends VerticalLayout implements HasUrlParameter<String> {

    @Autowired
    private ReaderService readerService;

    private Long bookId;
    private BookData currentBook;
    private Label titleLabel;
    private Label authorLabel;
    private Label isbnLabel;
    private Image cover;
    private Label ratingLabel;
    private Text description;
    private Button addToListButton;
    private Button writeReviewButton;
    private Grid<ReviewData> reviews;
    private Dialog addToListDialog;
    private boolean isAuthorised;
    private UserData curUser;
    private Long userId;

    @Override
    public void setParameter(BeforeEvent event, String parameter) {

        AppLayout appLayout = new AppLayout();
        appLayout = getHeader(readerService);
        VerticalLayout mainLayout = new VerticalLayout();
        bookId = Long.valueOf(parameter);

       // HorizontalLayout links = new HorizontalLayout();
        HorizontalLayout coverLayout = new HorizontalLayout();
        VerticalLayout info = new VerticalLayout();
        HorizontalLayout buttons = new HorizontalLayout();

        try {
            currentBook = BookConverter.convertToBookDTO(readerService.getBookById(bookId));
            isAuthorised = UserSecurityUtil.hasUserRole();

            if (isAuthorised)
            {
                curUser = UserConverter.convertToUserDTO(readerService.findUserByLogin(UserSecurityUtil.getCurrentUserLogin()));
                userId = curUser.getId();
            }

            titleLabel = new Label();
            titleLabel.setText(currentBook.getTitle());

            authorLabel = new Label();
            authorLabel.setText(getAuthors(currentBook));

            isbnLabel = new Label();
            isbnLabel.setText(currentBook.getISBN());

            cover = new Image("frontend/" + currentBook.getCoverLink(), "Обложка книги");
            cover.setHeight("256px");
            cover.setWidth("151px");

            description = new Text(currentBook.getDescription());

            TextArea genres = new TextArea();
            genres.setValue(setGenres());
            genres.setReadOnly(true);

            addToListButton = new Button();
            addToListButton.setText("Добавить в прочитанное");

            addToListButton.addClickListener(e -> {
                if (isAuthorised) {
                    curUser = UserConverter.convertToUserDTO(readerService.findUserByLogin(UserSecurityUtil.getCurrentUserLogin()));
                    addToListDialog = new Dialog();
                    addToListDialog.open();
                    addToListDialog.setCloseOnEsc(true);
                    addToListDialog.setCloseOnOutsideClick(true);
                    Button saveButton = new Button();
                    saveButton.setText("Сохранить");


                    RadioButtonGroup<Double> mark = new RadioButtonGroup<>();
                    mark.setItems(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0);
                    mark.setLabel("Оценка книги");

                    saveButton.addClickListener(ev -> {
                        try {
                            if (mark.getValue()!= null)
                            {
                                if (readerService.addToList(curUser.getId(), bookId, mark.getValue()) != null) {
                                    Notification.show("Сохранено");
                                    addToListDialog.close();
                                }
                            }
                            else {
                                Notification.show("Оценка не выбрана");
                            }

                        } catch (UserNotFoundException | BookNotFoundException e1) {
                            Notification.show(e1.getMessage(), 2000, Notification.Position.MIDDLE);
                        }
                    });
                    addToListDialog.add(mark, saveButton);
                } else {
                    Notification.show("Войдите, чтобы продолжить");
                }
            });

            writeReviewButton = new Button();
            writeReviewButton.setText("Написать рецензию");

            writeReviewButton.addClickListener(e -> {
                if (isAuthorised) {
                    writeReviewButton.getUI().ifPresent(ui -> ui.navigate(String.format("create/%s", bookId.toString())));
                } else {
                    Notification.show("Войдите, чтобы продолжить");
                }
            });

            reviews = PageComponents.getReviewsGrid( readerService, userId, currentBook);

            try {
                PageComponents.setListOfReviews( reviews, readerService, currentBook, userId);
            } catch (BookNotFoundException | UserNotFoundException e) {
                Notification.show(e.getMessage(), 2000, Notification.Position.MIDDLE);
            }

            Button editBookButton = new Button("Редактировать книгу");
            editBookButton.addClickListener(editEv -> {});

            info.add(isbnLabel, titleLabel, authorLabel, description);
            coverLayout.add(cover, info);
            buttons.add(addToListButton, writeReviewButton);

            if (curUser != null && UserSecurityUtil.hasAdminRole())
            {
                buttons.add(editBookButton);
            }

            mainLayout.add(coverLayout, buttons, genres, reviews);
            appLayout.setContent(mainLayout);
            add(appLayout);

        }
        catch (BookNotFoundException e) {
            Notification.show(e.getMessage());
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String dateFormat(LocalDateTime publishingDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = publishingDate.format(formatter);
        return formatDateTime;
    }

    private String setGenres() {

        StringJoiner joiner = new StringJoiner(", ");
        List<GenreData> genres = GenreConverter.convertToGenreDTOList(readerService.getAllGenresByBookId(bookId));
        genres.stream().forEach( g -> joiner.add(g.getName()));
        return joiner.toString();
    }

    private String getUserLogin(ReviewData review) {
        return review.getUser().getLogin();
    }

    private void setListOfReviews() throws BookNotFoundException, UserNotFoundException {

        reviews.setItems(ReviewConverter.convertToReviewDTOList(readerService.getAllReviewsByBookId(currentBook.getId())));
    }

    private String getAuthors(BookData currentBook) {
        StringJoiner joiner = new StringJoiner(", ");
        currentBook.getBookAuthors().stream().forEach( a -> joiner.add(a.getName()));
        return joiner.toString();
    }
}
