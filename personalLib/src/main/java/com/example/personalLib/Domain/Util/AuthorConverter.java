package com.example.personalLib.Domain.Util;

import com.example.personalLib.API.Data.AuthorData;
import com.example.personalLib.DB.Models.AuthorModel;
import com.example.personalLib.Domain.Model.Author;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorConverter {

    public AuthorConverter() {}

    /**
     * Преобразует из сущности в доменный объект
     * @param author БД
     * @return доменный объект
     */

    public static Author convertToAuthorDomain (AuthorModel author){

        return Author.builder()
                .id(author.getId())
                .name(author.getName())
                .description(author.getDescription()).build();
    }

    /**
     * Преобразует список сущностей в список доменных объектов
     * @param authorList список сущностей
     * @return Список доменных объктов
     */

    public static List<Author> convertToAuthorDomainList (List<AuthorModel> authorList){
        return authorList.stream()
                .map(AuthorConverter :: convertToAuthorDomain)
                .collect(Collectors.toList());
    }

    /**
     * Преобразует из доменного объекта в DTO
     * @param author доменный объект
     * @return DTO
     */
    public static AuthorData convertToAuthorDTO (Author author){

        return AuthorData.builder()
                .id(author.getId())
                .name(author.getName())
                .description(author.getDescription()).build();
    }

    /**
     * Преобразует доменных объектов в список DTO
     * @param authorList доменных объектов
     * @return Список DTO
     */

    public static List<AuthorData> convertToAuthorDTOList (List<Author> authorList){
        return authorList.stream()
                .map(AuthorConverter :: convertToAuthorDTO)
                .collect(Collectors.toList());
    }

}
