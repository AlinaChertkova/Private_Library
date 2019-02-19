package com.example.personalLib.DB.Repository;

import com.example.personalLib.DB.Models.BookModel;
import com.example.personalLib.DB.Models.EditionModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EditionRepository extends CrudRepository<EditionModel, Long> {
    List<EditionModel> findByBookId(Long id);
}
