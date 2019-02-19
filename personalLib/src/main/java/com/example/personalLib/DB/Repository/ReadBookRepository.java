package com.example.personalLib.DB.Repository;

import com.example.personalLib.DB.Models.EditionModel;
import com.example.personalLib.DB.Models.ReadBookModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReadBookRepository extends CrudRepository<ReadBookModel, Long> {

    List<ReadBookModel> findByUserId(Long id);
}
