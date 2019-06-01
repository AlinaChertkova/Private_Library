package com.example.personalLib.API.UI;

import com.example.personalLib.API.Data.BookData;
import com.example.personalLib.Domain.Services.Reader.ReaderService;
import com.example.personalLib.Domain.Util.BookConverter;
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
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static com.example.personalLib.API.PageComponents.getHeader;

@Route("")

public class CatalogView extends VerticalLayout {

    @Autowired
    private ReaderService readerService;

    private AppLayout appLayout;

    public CatalogView(ReaderService readerService) throws InterruptedException {

        appLayout = getHeader(readerService);
        VerticalLayout mainLayout = new VerticalLayout();

        List <BookData> books = BookConverter.convertToBookDTOList(readerService.getAllBooks());

        for(BookData book : books)
        {
            mainLayout.add(setListOfBooks(book));
        }

        appLayout.setContent(mainLayout);
        add(appLayout);
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
