package com.example.personalLib.Domain.Services.Admin;

import com.example.personalLib.Domain.Exceptions.AuthorNotFoundException;
import com.example.personalLib.Domain.Exceptions.BookNotFoundException;
import com.example.personalLib.Domain.Exceptions.ReviewNotFoundException;
import com.example.personalLib.Domain.Model.Author;
import com.example.personalLib.Domain.Model.Genre;

import java.util.List;

public interface AdminService {

    /**
     * Удалить книгу
     * @param id идентификатор книги
     */
    void deleteBook(Long id) throws BookNotFoundException;

    /**
     * Удалить обзор книги
     * @param id идентификатор обзора
     */
    void deleteReview (Long id) throws ReviewNotFoundException;

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
     * @param authors Список аврторов
     * @return Книга
     */
    //Book addBook(String isbn, String title, String description, String coverLink, List<AuthorModel> authors);

    /**
     * Обновить информацию о книге
     * @param isbn код книги
     * @param title название книги
     * @param description описание книги
     * @param coverLink ссылка на обложку
     * @param authors список авторов
     * @param avgRating новый средний рейтинг
     * @return книга
     */
    //Book updateBook(Long id, String isbn, String title, String description, String coverLink, List<AuthorModel> authors, double avgRating) throws BookNotFoundException;

    /**
     * Обновить информацию об авторе
     * @param description описане автора
     * @param name имя автора
     * @return автор
     */
    Author updateAuthor(Long id, String description, String name) throws AuthorNotFoundException;

    /**
     * Удалит автора
     * @param id иденитификатор автора
     */
    void deleteAuthor(Long id) throws AuthorNotFoundException;

    /**
     * Доавить жанр
     * @param name название жанра
     * @return жанр
     */
    Genre addGenre (String name);
}
