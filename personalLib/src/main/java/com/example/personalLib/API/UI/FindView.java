package com.example.personalLib.API.UI;

import com.example.personalLib.API.Data.BookData;
import com.example.personalLib.API.Data.UserData;
import com.example.personalLib.Domain.Services.Reader.ReaderService;
import com.example.personalLib.Domain.Util.BookConverter;
import com.example.personalLib.Domain.Util.UserConverter;
import com.example.personalLib.Security.UserSecurityUtil;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.StringJoiner;

import static com.example.personalLib.API.PageComponents.getHeader;
import static com.example.personalLib.Security.UserSecurityUtil.hasUserRole;

@Route("find")
public class FindView extends VerticalLayout implements HasUrlParameter<String> {

    @Autowired
    private ReaderService readerService;

    private TextField searchField;
    private Button searchButton;
    private VerticalLayout bookList = new VerticalLayout();

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {

        AppLayout appLayout = new AppLayout();
        appLayout = getHeader(readerService);

        bookList.removeAll();

        searchField = new TextField();
        searchField.setWidth("50%");
        searchField.setPlaceholder("Поиск");

        HorizontalLayout search = new HorizontalLayout();
        searchButton = new Button();
        searchButton.setText("Найти");

        search.add(searchField, searchButton);

        if (parameter == null)
        {
            Image image = new Image("frontend/404.png", "Image");
            image.setWidth("100%");

            searchButton.addClickListener(e -> searchButton.getUI().ifPresent
                    (ui->ui.navigate(String.format("find/%s", searchField.getValue().trim()))));

            searchField.addKeyPressListener(Key.ENTER, listener ->
                    searchButton.getUI().ifPresent(ui -> ui.navigate(String.format("find/%s", searchField.getValue().trim()))));

            bookList.add(searchField, searchButton, image);
            appLayout.setContent(bookList);
            add(appLayout);
        }
        else{
            String searchParam = parameter;

            bookList.add(searchField, searchButton);

            searchField.setValue(searchParam);
            searchButton.addClickListener(e -> searchButton.getUI().ifPresent
                    (ui->ui.navigate(String.format("find/%s", searchField.getValue().trim()))));

            searchField.addKeyPressListener(Key.ENTER, listener ->
                    searchButton.getUI().ifPresent(ui -> ui.navigate(String.format("find/%s", searchField.getValue().trim()))));

            List<BookData> books = BookConverter.convertToBookDTOList(readerService.getBooksByTitle(searchParam));
            books.addAll(BookConverter.convertToBookDTOList(readerService.getBooksByAuthorName(searchParam)));
            books.addAll(BookConverter.convertToBookDTOList(readerService.getBookByISBN(searchParam)));

            if (books.isEmpty())
            {
                Image image = new Image("frontend/404.png", "Image");
                image.setWidth("100%");
                bookList.add(image);
            }

            for(BookData book : books)
            {
                bookList.add(setListOfBooks(book));
            }
            appLayout.setContent(bookList);
            add(appLayout);
        }

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
