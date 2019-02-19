package com.example.personalLib.API.UI;

import com.example.personalLib.API.Data.UserData;
import com.example.personalLib.Domain.Exceptions.BookNotFoundException;
import com.example.personalLib.Domain.Exceptions.UserNotFoundException;
import com.example.personalLib.Domain.Model.Review;
import com.example.personalLib.Domain.Services.Reader.ReaderService;
import com.example.personalLib.Domain.Util.UserConverter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

@Route("create")
public class CreateReviewView extends VerticalLayout implements HasUrlParameter<String> {
    @Autowired
    private ReaderService readerService;

    private Long bookId;
    private UserData user;
    private TextArea text;
    private Button saveButton;
    private RadioButtonGroup<Double> mark;
    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        bookId = Long.valueOf(parameter);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            user = UserConverter.convertToUserDTO(readerService.getUserByLogin(currentUserName));
        }

        Button back = new Button("Назад");
        back.addClickListener(b ->  back.getUI().ifPresent(ui -> ui.navigate("book/" + bookId)));

        text = new TextArea();
        text.setLabel("Введите текст рецензии");

        text.setWidth("900px");
        text.setHeight("500px");

        saveButton = new Button();
        saveButton.setText("Сохранить");

        mark = new RadioButtonGroup<>();
        mark.setItems(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0);
        mark.setLabel("Оценка книги");

        saveButton.addClickListener(e ->
        {
            try {
                Double m = mark.getValue();
                Review saved = readerService.createReview(user.getId(), bookId, text.getValue(), LocalDateTime.now(), m);

            if (saved!= null)
            {
                Notification.show("Сохранено", 2000, Notification.Position.MIDDLE);
                saveButton.getUI().ifPresent(ui -> ui.navigate(String.format("book/%s", bookId.toString())));
            }
            } catch (UserNotFoundException | BookNotFoundException e1) {
                Notification.show("Не удалось сохранить", 2000, Notification.Position.MIDDLE);
            }
        });

        add(back, text, mark, saveButton);
    }
}
