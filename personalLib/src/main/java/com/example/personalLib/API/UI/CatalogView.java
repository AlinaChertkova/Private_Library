package com.example.personalLib.API.UI;

import com.example.personalLib.API.Data.AuthorData;
import com.example.personalLib.API.Data.BookData;
import com.example.personalLib.API.Data.UserData;
import com.example.personalLib.Domain.Services.Reader.ReaderService;
import com.example.personalLib.Domain.Util.BookConverter;
import com.example.personalLib.Domain.Util.UserConverter;
import com.example.personalLib.Security.UserCheck;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static com.example.personalLib.Security.UserCheck.hasUserRole;

@Route("")
public class CatalogView extends VerticalLayout {

    @Autowired
    private ReaderService readerService;

    private TextField searchField;
    private Button searchButton;

    public CatalogView(ReaderService readerService) {

        HorizontalLayout links = new HorizontalLayout();
        if (hasUserRole())
        {
            Button exit = new Button("Выйти", event -> searchButton.getUI().ifPresent(ui -> {
                    SecurityContextHolder.clearContext();
                    VaadinSession.getCurrent().close();
                    ui.getSession().close();
                    ui.navigate("login/loggedout");
            }));
            UserData curUser = UserConverter.convertToUserDTO(readerService.getUserByLogin(UserCheck.getCurrentUserLogin()));
            Button link = new Button("Мои книги");
            link.addClickListener(b ->  link.getUI().ifPresent(ui -> ui.navigate("mybooks/" + curUser.getId())));
            links.add(exit, link);
        }
        else {
            Button enter = new Button("Войти");
            enter.addClickListener(b ->  enter.getUI().ifPresent(ui -> ui.navigate("login")));
            links.add(enter);
        }

        this.readerService = readerService;
        searchField = new TextField();
        searchField.setLabel("Поиск");

        searchButton = new Button();
        searchButton.setText("Найти");

        searchButton.addClickListener(event -> searchButton.getUI().ifPresent
                    (ui->ui.navigate(String.format("find/%s", searchField.getValue().trim()))));

        searchField.addKeyPressListener(Key.ENTER, listener -> searchButton.getUI().ifPresent
                (ui->ui.navigate(String.format("find/%s", searchField.getValue().trim()))));

        add(links, searchField, searchButton);

        List <BookData> books = BookConverter.convertToBookDTOList(readerService.getAllBooks());

        for(BookData book : books)
        {
            add(setListOfBooks(book));
        }
    }

   private String getAuthors(BookData book) {

       StringJoiner joiner = new StringJoiner(", ");
       book.getBookAuthors().stream().forEach( a -> joiner.add(a.getName()));
        return joiner.toString();
    }

    private HorizontalLayout setListOfBooks(BookData book){

        Label mark = new Label();
        mark.setText(book.getAvgRating().toString());

        Image cover = new Image("frontend/" + book.getCoverLink(), "Image");
        cover.setHeight("128px");
        cover.setWidth("76px");

        VerticalLayout pic = new VerticalLayout();
        Anchor link = new Anchor("book/" + book.getId(), "Просмотреть");
        pic.setWidth("85px");
        pic.add(mark, cover, link);

        HorizontalLayout tit = new HorizontalLayout();

        Label title = new Label();
        title.setText(book.getTitle());

        Label author = new Label();
        author.setText(getAuthors(book));

        tit.add(title, author);

        VerticalLayout info = new VerticalLayout();

        Text dis = new Text(book.getDescription());

        info.add(tit, dis);

        HorizontalLayout layout = new HorizontalLayout();
        layout.add(pic, info);
        layout.setHeight("300px");

        return layout;
    }
}
