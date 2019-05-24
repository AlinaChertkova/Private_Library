package com.example.personalLib.Domain.Services.Reader;

import com.example.personalLib.DB.Models.Role;
import com.example.personalLib.DB.Models.UserModel;
import com.example.personalLib.Domain.Exceptions.BookNotFoundException;
import com.example.personalLib.Domain.Exceptions.ReadBookNotFoundException;
import com.example.personalLib.Domain.Exceptions.ReviewNotFoundException;
import com.example.personalLib.Domain.Exceptions.UserNotFoundException;
import com.example.personalLib.Domain.Model.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface ReaderService {

    /**
     * Найти книгу по идентификатору
     * @param id идентификатор книги
     * @return книга
     */
    Book getBookById (Long id) throws BookNotFoundException;

    /**
     * Получить список всех книг
     * @return Список всех книг
     */
    List<Book> getAllBooks();

    /**
     * Добавить обзор на книгу
     * @param userId идентификатор пользователя
     * @param bookId идентификатор книги
     * @param text Текст отзыва
     * @param date Дата публикации
     * @param mark Оценка книги
     * @return Обзор на книгу
     */
    Review createReview(Long userId, Long bookId, String text, LocalDateTime date, Double mark) throws UserNotFoundException, BookNotFoundException;

    /**
     * Найти книги по названию
     * @param title название книги
     * @return список книга
     */
    List<Book> getBooksByTitle(String title);

    /**
     * Найти книги по имени автора
     * @param name имя автора
     * @return Список книг
     */

    List<Book> getBooksByAuthorName(String name);

    /**
     * Найти книгу по ISBN
     * @param isbn код книги
     * @return книга
     */
    List<Book> getBookByISBN(String isbn);

    /**
     * Добавить книгу в список прочитанных
     * @param userId идентификатор пользователя
     * @param bookId идентификатор книги
     * @param mark оценка книги
     * @return Запись в списке
     */
    ReadBook addToList (Long userId, Long bookId, Double mark) throws UserNotFoundException, BookNotFoundException;

    /**
     * Получить все обзоры на книгу
     * @param bookId идентифмкатор книги
     * @return список рецензий
     */
    List<Review> getAllReviewsByBookId(Long bookId) throws BookNotFoundException, UserNotFoundException;

    /**
     * Получить рецензию по идентификатору
     * @param id идентификатор рецензии
     * @return рецензия
     */
    Review getReviewById(Long id) throws ReviewNotFoundException;

    /**
     * Получить список прочитанных книг пользователя
     * @param userId идентификатор пользователя
     * @return Список прочитанных книг
     */
    List<ReadBook> getAllReadBooksByUserId(Long userId) throws UserNotFoundException;

    /**
     * Получить пользователя по логину
     * @param login логин полозователя
     * @return пользователь
     */
    User getUserByLogin(String login);

    /**
     * Получить список жанров
     * @param id идентификатор книги
     * @return список жанров
     */
    List<Genre> getAllGenresByBookId(Long id);

    /**
     * Удалить книги из прочитанного
     * @param ids список идентификаторов записей
     */
    void deleteReadBooks (List<Long> ids) throws Exception;

    /**
     * Обновить оценку книги
     * @param mark новая оценка
     * @return обновленная запись
     */
    ReadBook updateMark(Long id, Double mark) throws ReadBookNotFoundException;

    /**
     * Обновить рецензию
     * @param id идентификатор рецензии
     * @param text новый текст рецензии
     * @param newMark новая оценка
     * @return обновленная рецензия
     * @throws ReviewNotFoundException
     */
    Review updateReview(Long id, String text, Double newMark) throws ReviewNotFoundException;

    /**
     * Удалить рецензию
     * @param id идентификатор рецензии
     * @throws ReviewNotFoundException
     */
    void deleteReview(Long id) throws ReviewNotFoundException;

    /**
     * Получить все рецензии пользователя по идентиф
     * @param userId идентификатор пользователя
     * @return список рецензий
     * @throws UserNotFoundException
     */
    List<Review> getAllReviewsByUserId(Long userId) throws UserNotFoundException;

    /**
     * Обновить данные пользователя
     * @param id идентификатор пользователя
     * @param login новый логин
     * @param name новое имя
     * @param password новый пароль
     * @return нового пользователя
     * @throws UserNotFoundException
     */
    User updateUser(Long id, String login, String name, String password) throws UserNotFoundException;

    /**
     * Удалить пользователя
     * @param id идентификатор пользователя
     * @throws UserNotFoundException
     */
    void deleteUser(Long id) throws UserNotFoundException;
}
