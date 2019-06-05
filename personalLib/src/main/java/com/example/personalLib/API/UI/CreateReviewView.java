package com.example.personalLib.API.UI;

import com.example.personalLib.API.Data.UserData;
import com.example.personalLib.Domain.Model.Review;
import com.example.personalLib.Domain.Model.User;
import com.example.personalLib.Domain.Services.Reader.ReaderService;
import com.example.personalLib.Domain.Util.UserConverter;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

import static com.example.personalLib.API.PageComponents.getHeader;

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

        AppLayout appLayout = new AppLayout();
        appLayout = getHeader(readerService);
        VerticalLayout mainLayout = new VerticalLayout();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            user = UserConverter.convertToUserDTO(readerService.findUserByLogin(currentUserName));
        }

        Button back = new Button("Назад");
        back.addClickListener(b ->  back.getUI().ifPresent(ui -> ui.navigate("book/" + bookId)));

        text = new TextArea();
        text.setLabel("Введите текст рецензии");

        text.setWidth("500px");
        text.setHeight("500px");

        saveButton = new Button();
        saveButton.setText("Сохранить");

        mark = new RadioButtonGroup<>();
        mark.setItems(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0);
        mark.setLabel("Оценка книги");

        HorizontalLayout buttons = new HorizontalLayout();
        saveButton.addClickListener(e ->
        {
            try {
                if (!text.getValue().isEmpty()) {
                    Double m = mark.getValue();
                    Review saved = readerService.createReview(user.getId(), bookId, text.getValue(), LocalDateTime.now(), m);
                    if (saved != null) {
                        Notification.show("Сохранено", 2000, Notification.Position.MIDDLE);
                        saveButton.getUI().ifPresent(ui -> ui.navigate(String.format("book/%s", bookId.toString())));
                    }
                }
                else {
                    throw new Exception("Напишите тескт рецензии!");
                }
            } catch (Exception e1) {
                Notification.show(e1.getMessage(), 2000, Notification.Position.MIDDLE);
            }
        });

        buttons.add(saveButton, back);
        mainLayout.add(text, mark, buttons);
        appLayout.setContent(mainLayout);
        add(appLayout);
    }
}
