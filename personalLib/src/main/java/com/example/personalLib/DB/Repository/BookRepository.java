package com.example.personalLib.DB.Repository;

import com.example.personalLib.DB.Models.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookModel, Long> {

    List<BookModel> findByIsbn(String Isbn);

    List<BookModel> findDistinctByTitleContainingIgnoreCase(String title);

    List<BookModel> findByBookGenresId(Long id);

    List<BookModel> findDistinctByBookAuthorsNameContainingIgnoreCase(String name);
}
