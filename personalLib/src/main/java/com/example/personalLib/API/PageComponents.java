package com.example.personalLib.API;

import com.example.personalLib.API.Data.BookData;
import com.example.personalLib.API.Data.ReadBookData;
import com.example.personalLib.API.Data.ReviewData;
import com.example.personalLib.API.Data.UserData;
import com.example.personalLib.Domain.Exceptions.BookNotFoundException;
import com.example.personalLib.Domain.Exceptions.ReviewNotFoundException;
import com.example.personalLib.Domain.Exceptions.UserNotFoundException;
import com.example.personalLib.Domain.Services.Reader.ReaderService;
import com.example.personalLib.Domain.Util.ReadBookConverter;
import com.example.personalLib.Domain.Util.ReviewConverter;
import com.example.personalLib.Domain.Util.UserConverter;
import com.example.personalLib.Security.UserSecurityUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
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
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import static com.example.personalLib.Security.UserSecurityUtil.hasAdminRole;
import static com.example.personalLib.Security.UserSecurityUtil.hasUserRole;

public class PageComponents extends VerticalLayout {

    public static AppLayout getHeader(ReaderService readerService){

        AppLayout appLayout = new AppLayout();

        AppLayoutMenu menu = appLayout.createMenu();

        Image img = new Image("frontend/logo.png", "Logo");
        img.setHeight("44px");
        appLayout.setBranding(img);

        menu.addMenuItem(new AppLayoutMenuItem("Каталог", ""));
        menu.addMenuItem(new AppLayoutMenuItem("Поиск", "find"));

        if (hasUserRole())
        {

            UserData curUser = UserConverter.convertToUserDTO(readerService.findUserByLogin(UserSecurityUtil.getCurrentUserLogin()));

            AppLayoutMenuItem myPageItem = new AppLayoutMenuItem("Моя страница");
            myPageItem.addMenuItemClickListener(
                    b ->  myPageItem.getUI().ifPresent(ui -> ui.navigate("mybooks/" + curUser.getId())));

            AppLayoutMenuItem item = new AppLayoutMenuItem("Выйти");
            item.addMenuItemClickListener(ev -> item.getUI().ifPresent(ui ->
            {
                SecurityContextHolder.clearContext();
                VaadinSession.getCurrent().close();
                ui.getSession().close();
                ui.navigate("login/loggedout");
            }));
            menu.addMenuItems(myPageItem, item);


        }
        else{
            menu.addMenuItem( new AppLayoutMenuItem("Войти", "login"));
        }

        //AppLayoutMenuItem search = new AppLayoutMenuItem(searchField);
        /*TextField searchField = new TextField();
        searchField.setPlaceholder("Поиск");

        searchField.addKeyPressListener(Key.ENTER, listener -> searchField.getUI().ifPresent
                (ui->ui.navigate(String.format("find/%s", searchField.getValue().trim()))));

        menu.addMenuItem(searchField);*/

        return appLayout;
    }

    public static Dialog getAddToListDialog(ReaderService readerService, Long bookId){

        Dialog addToListDialog = new Dialog();
        addToListDialog.setCloseOnEsc(true);
        addToListDialog.setCloseOnOutsideClick(true);
        Button saveButton = new Button();
        saveButton.setText("Сохранить");

        UserData curUser = UserConverter.convertToUserDTO(readerService.findUserByLogin(UserSecurityUtil.getCurrentUserLogin()));


        RadioButtonGroup<Double> mark = new RadioButtonGroup<>();
        mark.setItems(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0);
        mark.setLabel("Оценка книги");


        saveButton.addClickListener(ev -> {
            try {
                if (readerService.addToList(curUser.getId(), bookId, mark.getValue()) != null) {
                    Notification.show("Сохранено");
                }

            } catch (UserNotFoundException | BookNotFoundException e1)
            {
                Notification.show(e1.getMessage(), 2000, Notification.Position.MIDDLE);
            }
            addToListDialog.close();
        });
        return addToListDialog;
    }

    public static Grid getReadBooksGrid (ReaderService readerService, Long userId) throws UserNotFoundException {
        Grid<ReadBookData> grid = new Grid<>();

        grid.addColumn(book -> book.getBook().getTitle()).setHeader("Название");
        grid.addColumn(ReadBookData::getMark).setHeader("Оценка");
        grid.addColumn(book -> getAuthors(book)).setHeader("Автор").setWidth("259px");

        grid.addColumn(
                new NativeButtonRenderer<>("Изменить", clickedItem -> {
                    Dialog updateMarkDialog = new Dialog();
                    updateMarkDialog.open();
                    updateMarkDialog.setCloseOnEsc(true);
                    updateMarkDialog.setCloseOnOutsideClick(true);
                    Button saveButton = new Button();
                    saveButton.setText("Сохранить");


                    RadioButtonGroup<Double> mark = new RadioButtonGroup<>();
                    mark.setItems(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0);
                    mark.setLabel("Новая оценка");
                    mark.setValue(clickedItem.getMark());
                    saveButton.addClickListener(ev -> {
                        try {
                            if (readerService.updateMark(clickedItem.getId(), mark.getValue()) != null) {
                                Notification.show("Сохранено");
                                updateMarkDialog.close();
                                setListOfBooks(grid, readerService, userId);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        updateMarkDialog.close();
                    });
                    updateMarkDialog.add(mark, saveButton);
                }));

        grid.setSelectionMode(Grid.SelectionMode.MULTI);

        setListOfBooks(grid, readerService, userId);
        return grid;
    }

    public static Grid getReviewsGrid (ReaderService readerService, Long userId, @Nullable BookData book) throws UserNotFoundException, BookNotFoundException {
        Grid<ReviewData> reviewGrid = new Grid<>();

        reviewGrid.addColumn(review -> review.getUser().getLogin()).setHeader("Отзыв");
        reviewGrid.addColumn(ReviewData::getText).setHeader("Отзыв");
        reviewGrid.addColumn(ReviewData::getMark).setHeader("Оценка").setWidth("12px");
        reviewGrid.addColumn(r -> dateFormat(r.getPublishingDate())).setHeader("Дата");

        reviewGrid.addColumn(getEditReviewButton(readerService, userId, book, reviewGrid)).setHeader("Редактировать");

        reviewGrid.addItemClickListener(e -> {
            ReviewData selected = e.getItem();
            reviewGrid.getUI().ifPresent(ui -> ui.navigate(String.format("review/%s", selected.getId().toString())));
        });

        //reviewGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        setListOfReviews(reviewGrid, readerService, book, userId);
        return reviewGrid;
    }


    public static NativeButtonRenderer getEditReviewButton(ReaderService readerService, Long userId, @Nullable BookData book, Grid<ReviewData> reviewsGrid){
        NativeButtonRenderer<ReviewData> button = new NativeButtonRenderer<>("Изменить", clickedItem -> {
            try {
                if (userId == null) {
                    throw new Exception("Войдите, чтобы продолжить!");
                }
                if (clickedItem.getUser().getId().equals(userId) || hasAdminRole()) {
                    Dialog updateDialog = new Dialog();
                    updateDialog.open();
                    updateDialog.setCloseOnEsc(true);
                    updateDialog.setCloseOnOutsideClick(true);
                    Button saveButton = new Button();
                    saveButton.setText("Сохранить");

                    Button cancelButton = new Button();
                    cancelButton.setText("Отмена");
                    cancelButton.addClickListener(e -> updateDialog.close());

                    Button deleteButton = new Button();
                    deleteButton.setText("Удалить рецензию");
                    deleteButton.addClickListener(deleteButtonEvent -> {
                        Dialog deleteDialog = new Dialog();
                        deleteDialog.open();
                        deleteDialog.setCloseOnEsc(true);
                        deleteDialog.setCloseOnOutsideClick(true);

                        Button continueButton = new Button();
                        continueButton.setText("Удалить");
                        continueButton.addClickListener(continueButtonEvent -> {
                            try {
                                readerService.deleteReview(clickedItem.getId());
                                Notification.show("Рецензия удалена", 2000, Notification.Position.MIDDLE);
                                deleteDialog.close();
                                updateDialog.close();
                                setListOfReviews(reviewsGrid, readerService, book, userId);
                            } catch (ReviewNotFoundException | UserNotFoundException | BookNotFoundException e) {
                                Notification.show(e.getMessage(), 2000, Notification.Position.MIDDLE);
                            }
                        });

                        Button cancelDeleteButton = new Button();
                        cancelDeleteButton.setText("Отмена");
                        cancelDeleteButton.addClickListener(ev -> deleteDialog.close());

                        deleteDialog.add(new Label("Удалить рецензию?"));
                        HorizontalLayout deleteDialogButtons = new HorizontalLayout();
                        deleteDialogButtons.add(continueButton, cancelDeleteButton);
                        deleteDialog.add(deleteDialogButtons);
                    });

                    TextArea text = new TextArea("Текст рецензии");
                    text.setValue(clickedItem.getText());
                    text.setSizeUndefined();
                    text.setWidth("100%");
                    text.setHeight("300px");

                    RadioButtonGroup<Double> mark = new RadioButtonGroup<>();
                    mark.setItems(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0);
                    mark.setLabel("Новая оценка");

                    saveButton.addClickListener(ev -> {
                        try {
                            Double newMark;
                            if (mark.isEmpty()) {
                                newMark = clickedItem.getMark();
                            } else {
                                newMark = mark.getValue();
                            }
                            if (readerService.updateReview(clickedItem.getId(), text.getValue(), newMark) != null) {
                                Notification.show("Сохранено", 2000, Notification.Position.MIDDLE);
                                updateDialog.close();
                                setListOfReviews(reviewsGrid, readerService, book, userId);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        updateDialog.close();
                    });

                    HorizontalLayout updateDialogButtons = new HorizontalLayout();
                    updateDialogButtons.add(saveButton, cancelButton, deleteButton);
                    updateDialog.add(mark, text, updateDialogButtons);
                } else {
                    Notification.show("Невозможно изменить", 2000, Notification.Position.MIDDLE);
                }
            } catch (Exception e) {
                Notification.show(e.getMessage(), 2000, Notification.Position.MIDDLE);
            }
        });

        return button;
    }

    public static Button getRemoveFromBooksGridButton(Grid<ReadBookData> grid, ReaderService readerService, Long userId){
        Button deleteButton = new Button("Удалить");
        deleteButton.addClickListener(e -> {
            try {
                if (grid.getSelectedItems().size() == 0)
                {
                    throw new Exception("Не выбрано ни одной записи!");
                }
                Dialog deleteDialog = new Dialog();
                deleteDialog.open();
                deleteDialog.setCloseOnEsc(true);
                deleteDialog.setCloseOnOutsideClick(true);
                Button continueButton = new Button();
                continueButton.setText("Удалить");

                continueButton.addClickListener(ev -> {
                    try {
                        List<Long> list = new ArrayList();
                        grid.getSelectedItems().forEach(book -> list.add(book.getId()));
                        readerService.deleteReadBooks(list);
                        setListOfBooks(grid, readerService, userId);
                        deleteDialog.close();
                    }
                    catch(Exception ex)
                    {
                        Notification.show(ex.getMessage(), 2000, Notification.Position.MIDDLE);
                    }
                });

                Button cancelButton = new Button();
                cancelButton.setText("Отмена");
                cancelButton.addClickListener(cancelEvent -> deleteDialog.close());
                HorizontalLayout deleteDialogButtons = new HorizontalLayout();
                deleteDialogButtons.add(continueButton, cancelButton);
                deleteDialog.add(deleteDialogButtons);
            } catch (Exception e2)
            {
                Notification.show(e2.getMessage(), 2000, Notification.Position.MIDDLE);
            }
        });
        return deleteButton;
    }

    public static Button getRemoveFromReviewsGridButton(Grid<ReviewData>  reviews, ReaderService readerService, Long userId){
        Button deleteButton = new Button("Удалить");
        deleteButton.addClickListener(e -> {
            try {
                if (reviews.getSelectedItems().size() == 0)
                {
                    throw new Exception("Не выбрано ни одной записи!");
                }
                Dialog deleteDialog = new Dialog();
                deleteDialog.open();
                deleteDialog.setCloseOnEsc(true);
                deleteDialog.setCloseOnOutsideClick(true);
                Button continueButton = new Button();
                continueButton.setText("Удалить");

                continueButton.addClickListener(ev -> {
                    try {
                        List<Long> list = new ArrayList();
                        reviews.getSelectedItems().forEach(review -> list.add(review.getId()));
                        readerService.deleteReadBooks(list);
                        setListOfReviews(reviews, readerService, null, userId);
                        deleteDialog.close();
                    }
                    catch(Exception ex)
                    {
                        Notification.show(ex.getMessage(), 2000, Notification.Position.MIDDLE);
                    }
                });

                Button cancelButton = new Button();
                cancelButton.setText("Отмена");
                cancelButton.addClickListener(cancelEvent -> deleteDialog.close());
                HorizontalLayout deleteDialogButtons = new HorizontalLayout();
                deleteDialogButtons.add(continueButton, cancelButton);
                deleteDialog.add(deleteDialogButtons);
            } catch (Exception e2)
            {
                Notification.show(e2.getMessage(), 2000, Notification.Position.MIDDLE);
            }
        });
        return deleteButton;
    }

    private void setMenuItem(AppLayoutMenu menu, AppLayoutMenuItem menuItem) {
        menuItem.getElement().setAttribute("theme", "icon-on-top");
        menu.addMenuItem(menuItem);
    }

    private static String getAuthors(ReadBookData book) {
        StringJoiner joiner = new StringJoiner(", ");
        book.getBook().getBookAuthors().stream().forEach( a -> joiner.add(a.getName()));
        return joiner.toString();
    }

    private static void setListOfBooks(Grid<ReadBookData> grid, ReaderService readerService, Long userId) throws UserNotFoundException {

        grid.setItems(ReadBookConverter.convertToReadBookDTOList(readerService.getAllReadBooksByUserId(userId)));
    }

    public static void setListOfReviews(Grid<ReviewData> reviews, ReaderService readerService, @Nullable BookData currentBook, @Nullable Long userId ) throws BookNotFoundException, UserNotFoundException {

        if(currentBook == null)
        {
            reviews.setItems(ReviewConverter.convertToReviewDTOList(readerService.getAllReviewsByUserId(userId)));
        } else
            reviews.setItems(ReviewConverter.convertToReviewDTOList(readerService.getAllReviewsByBookId(currentBook.getId())));
    }

    public static String dateFormat(LocalDateTime publishingDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = publishingDate.format(formatter);
        return formatDateTime;
    }
}
