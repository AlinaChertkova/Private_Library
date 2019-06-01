package com.example.personalLib.DB.Repository;

import com.example.personalLib.DB.Models.UserModel;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepository extends JpaRepository<UserModel, Long> {

    UserModel findByLogin(String login);
}
