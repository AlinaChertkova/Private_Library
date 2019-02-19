package com.example.personalLib.DB.Repository;

import com.example.personalLib.DB.Models.AuthorModel;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<AuthorModel, Long> {

}
