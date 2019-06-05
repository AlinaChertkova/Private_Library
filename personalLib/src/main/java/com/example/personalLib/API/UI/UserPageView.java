package com.example.personalLib.API.UI;

import com.example.personalLib.API.Data.ReadBookData;
import com.example.personalLib.API.Data.ReviewData;
import com.example.personalLib.API.Data.UserData;
import com.example.personalLib.API.PageComponents;
import com.example.personalLib.DB.Models.UserModel;
import com.example.personalLib.Domain.Exceptions.UserNotFoundException;
import com.example.personalLib.Domain.Model.ReadBook;
import com.example.personalLib.Domain.Model.Review;
import com.example.personalLib.Domain.Model.User;
import com.example.personalLib.Domain.Services.Admin.AdminService;
import com.example.personalLib.Domain.Services.Reader.ReaderService;
import com.example.personalLib.Domain.Util.ReadBookConverter;
import com.example.personalLib.Domain.Util.UserConverter;
import com.example.personalLib.Security.UserSecurityUtil;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.personalLib.API.PageComponents.getHeader;
import static com.example.personalLib.Security.UserSecurityUtil.*;

@Route("mybooks")
public class UserPageView extends VerticalLayout implements HasUrlParameter<String> {

    @Autowired
    private ReaderService readerService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private AuthenticationManager authManager;

    private Long userId;
    private Grid<ReadBookData> grid;
    private UserData curUser;

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        try {
            AppLayout appLayout;
            appLayout = getHeader(readerService);
            VerticalLayout mainLayout = new VerticalLayout();

            userId = Long.valueOf(parameter);
            curUser = UserConverter.convertToUserDTO(readerService.getUserByLogin(UserSecurityUtil.getCurrentUserLogin()));

            if (userId != curUser.getId())
            {
                throw new Exception ("Доступ невозможен!");
            }

            Tab tab1 = new Tab("Мое прочитанное");
            VerticalLayout myBooksPage = new VerticalLayout();
            //page1.setText("Page#1");

            Grid <ReadBookData> readBooksGrid = PageComponents.getReadBooksGrid(readerService, userId);
            Button deleteReadBook = PageComponents.getRemoveFromBooksGridButton(readBooksGrid, readerService, userId);
            myBooksPage.add(readBooksGrid, deleteReadBook);

            Tab tab2 = new Tab("Мои рецензии");
            VerticalLayout myReviewsPage = new VerticalLayout();

            Grid <ReviewData> reviewGrid = new Grid<>();

            reviewGrid.addColumn(review -> review.getBook().getTitle()).setHeader("Название книги");
            reviewGrid.addColumn(ReviewData::getText).setHeader("Отзыв");
            reviewGrid.addColumn(ReviewData::getMark).setHeader("Оценка").setWidth("12px");
            reviewGrid.addColumn(r -> PageComponents.dateFormat(r.getPublishingDate())).setHeader("Дата");
            reviewGrid.addColumn(PageComponents.getEditReviewButton(readerService, userId, null, reviewGrid)).setHeader("Редактировать");

            reviewGrid.addItemClickListener(e -> {
                ReviewData selected = e.getItem();
                reviewGrid.getUI().ifPresent(ui -> ui.navigate(String.format("review/%s", selected.getId().toString())));
            });
            reviewGrid.setSelectionMode(Grid.SelectionMode.MULTI);
            PageComponents.setListOfReviews(reviewGrid, readerService, null, userId);

            Button deleteReviewButton = PageComponents.getRemoveFromReviewsGridButton(reviewGrid, readerService, userId);

            myReviewsPage.add(reviewGrid, deleteReviewButton);
            myReviewsPage.setVisible(false);

            Tab tab3 = new Tab("Личная страница");
            VerticalLayout myInfoPage = new VerticalLayout();

            HorizontalLayout links = new HorizontalLayout();

            if (hasAdminRole())
            {
                Button addAuthorBut = new Button("Добавить автора");
                addAuthorBut.addClickListener( e -> addAuthorBut.getUI().ifPresent(ui -> {
                    Dialog addAuthorDialog = new Dialog();
                    addAuthorDialog.open();
                    addAuthorDialog.setCloseOnEsc(true);
                    addAuthorDialog.setCloseOnOutsideClick(true);
                    TextField name = new TextField("Имя автора");
                    TextArea text = new TextArea("Описание автора");
                    text.setSizeUndefined();
                    text.setWidth("100%");
                    text.setHeight("300px");
                    Button add = new Button("Сохранить");
                    add.addClickListener(b -> {
                        adminService.addAuthor(text.getValue(), name.getValue());
                        addAuthorDialog.close();
                    });

                    Button cancel = new Button("Отмена");
                    cancel.addClickListener(click -> addAuthorDialog.close());

                    VerticalLayout vl = new VerticalLayout();
                    HorizontalLayout buttons = new HorizontalLayout(add, cancel);
                    vl.add(name, text, buttons);
                    addAuthorDialog.add(name, text, buttons);

                }));
                links.add(addAuthorBut);
            }

            Button editInfoButton = new Button("Редактировать данные");
            editInfoButton.addClickListener(editInfoEv -> editInfoButton.getUI().ifPresent(ui -> {
                Dialog editInfoDialog = new Dialog();
                editInfoDialog.open();
                editInfoDialog.setCloseOnEsc(true);
                editInfoDialog.setCloseOnOutsideClick(true);

                String password = curUser.getPassword();
                TextField login = new TextField("Логин");
                login.setValue(curUser.getLogin());
                TextField name = new TextField(" Имя");
                name.setValue(curUser.getName());

                Button cancel = new Button("Отмена");
                cancel.addClickListener(cancelEv -> editInfoDialog.close());

                Button saveEdit = new Button("Сохранить");
                saveEdit.addClickListener(saveEv -> {
                    try {
                        if (login.isEmpty() || name.isEmpty()) {
                            throw new Exception("Заполнены не все обязательные поля!");
                        }
                        User user1 = readerService.getUserByLogin(login.getValue());
                        if (!(login.getValue()).equals(curUser.getLogin()) && user1 != null)
                        {
                            login.setValue("");
                            throw new Exception("Пользователь в таким логином уже существует!");
                        }
                        readerService.updateUser(curUser.getId(), login.getValue(), name.getValue(), password);
                        if (!(curUser.getLogin()).equals(login.getValue()))
                        {
                            SecurityContextHolder.clearContext();
                            UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(login.getValue(), password);

                            Authentication auth = authManager.authenticate(authReq);
                            SecurityContext sc = SecurityContextHolder.getContext();
                            sc.setAuthentication(auth);
                        }

                        curUser = UserConverter.convertToUserDTO(readerService.getUserByLogin(UserSecurityUtil.getCurrentUserLogin()));
                        Notification.show("Сохранено", 2000, Notification.Position.MIDDLE);
                        editInfoDialog.close();
                    }catch (Exception e)
                    {
                        Notification.show(e.getMessage(), 2000, Notification.Position.MIDDLE);
                    }
                });

                Button editPassword = new Button("Изменить пароль");
                editPassword.addClickListener(editPassEv -> {
                    Dialog editPasswordDialog = new Dialog();
                    editPasswordDialog.open();
                    editPasswordDialog.setCloseOnEsc(true);
                    editPasswordDialog.setCloseOnOutsideClick(true);

                    PasswordField oldPassword = new PasswordField("Пароль");
                    PasswordField newPassword = new PasswordField("Новый пароль");
                    PasswordField newPasswordRep = new PasswordField("Повторите новый пароль");

                    Button savePassword = new Button("Сохранить");
                    savePassword.addClickListener(savePasswordEv -> {
                        try {
                            if (!oldPassword.getValue().equals(curUser.getPassword())) {
                                oldPassword.clear();
                                throw new Exception("Неверный пероль!");
                            }
                            else if (!(newPassword.getValue()).equals(newPasswordRep.getValue()))
                            {
                                newPasswordRep.clear();
                                throw new Exception("Пароль не совпал!");
                            }
                            else {
                                readerService.updateUser(curUser.getId(), curUser.getLogin(), curUser.getName(), newPassword.getValue());
                                curUser = UserConverter.convertToUserDTO(readerService.getUserByLogin(UserSecurityUtil.getCurrentUserLogin()));
                                Notification.show("Сохранено", 2000, Notification.Position.MIDDLE);
                                editPasswordDialog.close();
                            }
                        }catch (Exception e) {
                            Notification.show(e.getMessage(), 2000, Notification.Position.MIDDLE);
                        }
                    });

                    Button exitButton = new Button("Отмена");
                    exitButton.addClickListener(e -> editPasswordDialog.close());
                    VerticalLayout passwordFields = new VerticalLayout();
                    passwordFields.add(oldPassword, newPassword, newPasswordRep);
                    editPasswordDialog.add(passwordFields, savePassword, exitButton);
                });
                VerticalLayout fields = new VerticalLayout();
                fields.add(login, name);

                HorizontalLayout buttons = new HorizontalLayout();
                buttons.add(saveEdit, cancel);

                editInfoDialog.add(fields, editPassword, buttons);

            }));

            links.add(editInfoButton);

            Label login = new Label();
            login.setText("Логин: " + curUser.getLogin());

            Label name = new Label();
            name.setText("Имя: " + curUser.getName());

            Label dateOfReg = new Label();
            dateOfReg.setText("Дата регистрации: " + PageComponents.dateFormat(curUser.getRegistrationDate()));

            myInfoPage.add(links, login, name, dateOfReg);

            myInfoPage.setVisible(false);

            Map<Tab, Component> tabsToPages = new HashMap<>();
            tabsToPages.put(tab1, myBooksPage);
            tabsToPages.put(tab2, myReviewsPage);
            tabsToPages.put(tab3, myInfoPage);
            Tabs tabs = new Tabs(tab1, tab2, tab3);
            VerticalLayout pages = new VerticalLayout (myBooksPage, myReviewsPage, myInfoPage);
            Set<Component> pagesShown = Stream.of(myBooksPage)
                    .collect(Collectors.toSet());

            tabs.addSelectedChangeListener(event1 -> {
                pagesShown.forEach(page -> page.setVisible(false));
                pagesShown.clear();
                Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
                selectedPage.setVisible(true);
                pagesShown.add(selectedPage);
            });

            mainLayout.add(tabs, pages);
            appLayout.setContent(mainLayout);
            add(appLayout);
        } catch ( Exception e) {
            Notification.show(e.getMessage(), 3000, Notification.Position.MIDDLE);
        }
    }
}
