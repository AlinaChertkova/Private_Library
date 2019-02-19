package com.example.personalLib.DB.Repository;

import com.example.personalLib.DB.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends JpaRepository<UserModel, Long> {

    UserModel findByLogin(String login);

}
