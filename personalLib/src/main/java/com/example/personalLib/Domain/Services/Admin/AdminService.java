package com.example.personalLib.Domain.Services.Admin;

import com.example.personalLib.Domain.Model.Author;
import com.example.personalLib.Domain.Model.Book;
import javafx.print.Collation;

import java.util.Collection;

public interface AdminService {

    /**
     * Удалить книгу
     * @param id идентификатор книги
     */
    void deleteBook(Long id);

    /**
     * Удалить обзор книги
     * @param id идентификатор обзора
     */
    void deleteReview (Long id);

    /**
     * Добавить автора
     * @param description Описание
     * @param name Имя
     */
    Author addAuthor(String description, String name);

    /**
     * Добавить новую книгу
     * @param isbn код книги
     * @param title Название книги
     * @param description Описаие книги
     * @param coverLink Ссылка на обложку
     * @param authorsIds Список идентификаторов аврторов
     * @return Книга
     */
    Book addBook(String isbn, String title, String description, String coverLink, Collection<Long> authorsIds);

}
