package com.example.personalLib.DB.Repository;

import com.example.personalLib.DB.Models.GenreModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GenreRepository extends CrudRepository<GenreModel, Long> {

    List<GenreModel> findByBooksOfGenreId(Long id);
}
