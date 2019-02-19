package com.example.personalLib.Domain.Util;

import com.example.personalLib.API.Data.UserData;
import com.example.personalLib.DB.Models.PublishingModel;
import com.example.personalLib.DB.Models.UserModel;
import com.example.personalLib.Domain.Model.Publishing;
import com.example.personalLib.Domain.Model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {

    public UserConverter(){}

    /**
     * Преобразует из модели бд в доменный объект
     * @param user объект бд
     * @return доменный объект
     */

    public static User convertToUserDomain (UserModel user){

        return User.builder()
                .id(user.getId())
                .name(user.getName())
                .login(user.getLogin())
                .active(user.isActive())
                .password(user.getPassword())
                .registrationDate(user.getRegistrationDate()).build();
    }

    /**
     * Преобразует список сущностей в список доменных объектов
     * @param userList список сущностей
     * @return Список доменных объктов
     */

    public static List<User> convertToUserDomainList (List<UserModel> userList){
        return userList.stream()
                .map(UserConverter :: convertToUserDomain)
                .collect(Collectors.toList());
    }

    /**
     * Преобразует из модели бд в доменный объект
     * @param user объект бд
     * @return доменный объект
     */

    public static UserData convertToUserDTO (User user){

        return UserData.builder()
                .id(user.getId())
                .name(user.getName())
                .login(user.getLogin())
                .password(user.getPassword())
                .registrationDate(user.getRegistrationDate())
                .active(user.isActive()).build();
    }

    /**
     * Преобразует список сущностей в список доменных объектов
     * @param userList список сущностей
     * @return Список доменных объктов
     */

    public static List<UserData> convertToUserDTOList (List<User> userList){
        return userList.stream()
                .map(UserConverter :: convertToUserDTO)
                .collect(Collectors.toList());
    }
}
