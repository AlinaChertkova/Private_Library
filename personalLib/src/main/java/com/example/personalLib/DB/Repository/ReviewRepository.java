package com.example.personalLib.DB.Repository;

import com.example.personalLib.DB.Models.ReadBookModel;
import com.example.personalLib.DB.Models.ReviewModel;
import com.example.personalLib.Domain.Model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends CrudRepository<ReviewModel, Long> {

    List<ReviewModel> findByBookId(Long id);
}
