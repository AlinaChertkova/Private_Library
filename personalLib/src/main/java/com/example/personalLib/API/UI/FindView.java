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
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.StringJoiner;

import static com.example.personalLib.Security.UserCheck.hasUserRole;

@Route("find")
public class FindView extends VerticalLayout implements HasUrlParameter<String> {

    @Autowired
    private ReaderService readerService;

    private TextField searchField;
    private Button searchButton;
    private VerticalLayout bookList = new VerticalLayout();

    @Override
    public void setParameter(BeforeEvent event, String parameter) {

        bookList.removeAll();

        HorizontalLayout links = new HorizontalLayout();
        if (hasUserRole())
        {
            Button exit = new Button("Выйти", e -> searchButton.getUI().ifPresent(ui -> {
                    SecurityContextHolder.clearContext();
                    VaadinSession.getCurrent().close();
                    ui.getSession().close();
                    ui.navigate("login/loggedout");
            }));
            UserData curUser = UserConverter.convertToUserDTO(readerService.getUserByLogin(UserCheck.getCurrentUserLogin()));
            Button linkToMyBooks = new Button("Прочитанное");
            linkToMyBooks.addClickListener(b ->  linkToMyBooks.getUI().ifPresent(ui -> ui.navigate(String.format("mybooks/%s", curUser.getId().toString()))));
            links.add(exit, linkToMyBooks);
        }
        else {
            Button enter = new Button("Войти");
            enter.addClickListener(b ->  enter.getUI().ifPresent(ui -> ui.navigate("login")));
           // Anchor enter = new Anchor("login", "Войти");
            links.add(enter);
        }
        Button catalog = new Button("Каталог");
        catalog.addClickListener(b ->  catalog.getUI().ifPresent(ui -> ui.navigate("")));
        //Anchor catalog = new Anchor("", "Каталог");
        links.add(catalog);

        String searchParam = parameter;

        searchField = new TextField();
        searchField.setLabel("Поиск");

        searchButton = new Button();
        searchButton.setText("Найти");

        bookList.add(links);
        bookList.add(searchField, searchButton);

        searchButton.addClickListener(e -> searchButton.getUI().ifPresent
                    (ui->ui.navigate(String.format("find/%s", searchField.getValue().trim()))));

        searchField.addKeyPressListener(Key.ENTER, listener ->
            searchButton.getUI().ifPresent(ui -> ui.navigate(String.format("find/%s", searchField.getValue().trim()))));

        List<BookData> books = BookConverter.convertToBookDTOList(readerService.getBooksByTitle(searchParam));
        books.addAll(BookConverter.convertToBookDTOList(readerService.getBooksByAuthorName(searchParam)));
        books.addAll(BookConverter.convertToBookDTOList(readerService.getBookByISBN(searchParam)));

        for(BookData book : books)
        {
            bookList.add(setListOfBooks(book));
        }

        add(bookList);
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

    private String getAuthors(BookData book) {
        StringJoiner joiner = new StringJoiner(", ");
        book.getBookAuthors().stream().forEach( a -> joiner.add(a.getName()));
        return joiner.toString();
    }
}
