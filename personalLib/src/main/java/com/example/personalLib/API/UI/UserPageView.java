package com.example.personalLib.API.UI;

import com.example.personalLib.API.Data.ReadBookData;
import com.example.personalLib.API.Data.UserData;
import com.example.personalLib.API.PageComponents;
import com.example.personalLib.Domain.Exceptions.UserNotFoundException;
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
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.personalLib.API.PageComponents.getHeader;
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
            AppLayout appLayout = new AppLayout();
            appLayout = getHeader(readerService);
            VerticalLayout mainLayout = new VerticalLayout();

            userId = Long.valueOf(parameter);
            UserData curUser = UserConverter.convertToUserDTO(readerService.getUserByLogin(UserSecurityUtil.getCurrentUserLogin()));

            if (userId != curUser.getId())
            {
                throw new Exception ("Доступ невозможен!");
            }
            HorizontalLayout links = new HorizontalLayout();
            if (hasUserRole())
            {
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
                        VerticalLayout vl = new VerticalLayout();
                        vl.add(name, text, add);
                        addAuthorDialog.add(name, text, add);

                    }));
                    links.add(addAuthorBut);
                }
            }

            grid = PageComponents.getReadBooksGrid(readerService, userId);

            Button deleteButton = PageComponents.getRemoveFromGridButton(grid, readerService, userId);

            mainLayout.add(links, grid, deleteButton);
            appLayout.setContent(mainLayout);
            add(appLayout);
        } catch ( Exception e) {
            Notification.show(e.getMessage(), 3000, Notification.Position.MIDDLE);
        }
    }
}
