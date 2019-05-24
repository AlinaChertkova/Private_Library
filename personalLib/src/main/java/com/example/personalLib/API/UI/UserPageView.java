package com.example.personalLib.API.UI;

import com.example.personalLib.API.Data.ReadBookData;
import com.example.personalLib.API.Data.UserData;
import com.example.personalLib.Domain.Exceptions.UserNotFoundException;
import com.example.personalLib.Domain.Services.Admin.AdminService;
import com.example.personalLib.Domain.Services.Reader.ReaderService;
import com.example.personalLib.Domain.Util.ReadBookConverter;
import com.example.personalLib.Domain.Util.UserConverter;
import com.example.personalLib.Security.UserSecurityUtil;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import static com.example.personalLib.Security.UserSecurityUtil.hasAdminRole;
import static com.example.personalLib.Security.UserSecurityUtil.hasUserRole;

@Route("mybooks")
public class UserPageView extends VerticalLayout implements HasUrlParameter<String> {

    @Autowired
    private ReaderService readerService;
    @Autowired
    private AdminService adminService;

    private Long userId;
    private Grid<ReadBookData> grid;

    @Override
    public void setParameter(BeforeEvent event, String parameter) {

        try {
            userId = Long.valueOf(parameter);
            UserData curUser = UserConverter.convertToUserDTO(readerService.getUserByLogin(UserSecurityUtil.getCurrentUserLogin()));
            if (userId != curUser.getId())
            {
                throw new Exception ("Доступ невозможен!");
            }
            HorizontalLayout links = new HorizontalLayout();
            if (hasUserRole())
            {
                Button exit = new Button("Выйти");
                exit.addClickListener( e -> exit.getUI().ifPresent(ui -> {
                        SecurityContextHolder.clearContext();
                        VaadinSession.getCurrent().close();
                        ui.getSession().close();
                        ui.navigate("login/loggedout");
                }));
                links.add(exit);
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
                        add.addClickListener(b ->  adminService.addAuthor(text.getValue(), name.getValue()));
                        VerticalLayout vl = new VerticalLayout();
                        vl.add(name, text, add);
                        addAuthorDialog.add(name, text, add);
                    }));
                    links.add(addAuthorBut);
                }
            }
            else {
                Button enter = new Button("Войти");
                enter.addClickListener(b ->  enter.getUI().ifPresent(ui -> ui.navigate("login")));
                links.add(enter);
            }

            Button catalog = new Button("Каталог");
            catalog.addClickListener(b ->  catalog.getUI().ifPresent(ui -> ui.navigate("")));
            links.add(catalog);

            grid = new Grid<>();

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
                    saveButton.addClickListener(ev -> {
                        try {
                            if (readerService.updateMark(clickedItem.getId(), mark.getValue()) != null) {
                                Notification.show("Сохранено");
                                updateMarkDialog.close();
                                setListOfBooks();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        updateMarkDialog.close();
                    });
                    updateMarkDialog.add(mark, saveButton);
                }));

            grid.setSelectionMode(Grid.SelectionMode.MULTI);

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
                            setListOfBooks();
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

            setListOfBooks();

            add(links, grid, deleteButton);
        } catch ( Exception e) {
            Notification.show(e.getMessage(), 3000, Notification.Position.MIDDLE);
        }
    }

    private void setListOfBooks() throws UserNotFoundException {

        grid.setItems(ReadBookConverter.convertToReadBookDTOList(readerService.getAllReadBooksByUserId(userId)));
    }

    private String getAuthors(ReadBookData book) {
        StringJoiner joiner = new StringJoiner(", ");
        book.getBook().getBookAuthors().stream().forEach( a -> joiner.add(a.getName()));
        return joiner.toString();
    }
}
