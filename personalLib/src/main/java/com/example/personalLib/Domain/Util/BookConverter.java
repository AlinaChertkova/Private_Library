package com.example.personalLib.Domain.Util;

import com.example.personalLib.API.Data.BookData;
import com.example.personalLib.DB.Models.BookModel;
import com.example.personalLib.Domain.Model.Book;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookConverter {

    public BookConverter(){}

    /**
     * Преобразует из модели бд в доменный объект
     * @param book объект бд
     * @return доменный объект
     */

    public static Book convertToBookDomain (BookModel book) {

        return Book.builder()
                .id(book.getId())
                .ISBN(book.getISBN())
                .Title(book.getTitle())
                .description(book.getDescription())
                .coverLink(book.getCoverLink())
                .bookAuthors(AuthorConverter.convertToAuthorDomainList(book.getBookAuthors()))
                .avgRating(book.getAvgRating()).build();
    }

    /**
     * Преобразует список сущностей в список доменных объектов
     * @param bookList список сущностей
     * @return Список доменных объктов
     */

    public static List<Book> convertToBookDomainList (List<BookModel> bookList){

        return bookList.stream()
                .map(BookConverter :: convertToBookDomain)
                .collect(Collectors.toList());
    }

    /**
     * Преобразовать из доменного объекта в DTO
     * @param book доменный объект
     * @return DTO
     */
    public static BookData convertToBookDTO (Book book) {

        return BookData.builder()
                .id(book.getId())
                .ISBN(book.getISBN())
                .Title(book.getTitle())
                .description(book.getDescription())
                .coverLink(book.getCoverLink())
                .bookAuthors(AuthorConverter.convertToAuthorDTOList(book.getBookAuthors()))
                .avgRating(book.getAvgRating()).build();
    }

    /**
     * Преобразует из списка доменных объектов в список DTO
     * @param bookList список доменных объектов
     * @return список DTO
     */

    public static List<BookData> convertToBookDTOList (List<Book> bookList){
        return bookList.stream()
                .map(BookConverter :: convertToBookDTO)
                .collect(Collectors.toList());
    }
}
