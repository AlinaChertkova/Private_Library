package com.example.personalLib.Domain.Util;

import com.example.personalLib.API.Data.GenreData;
import com.example.personalLib.DB.Models.GenreModel;
import com.example.personalLib.Domain.Model.Genre;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GenreConverter {

    public GenreConverter(){}

    /**
     * Преобразует из модели бд в доменный объект
     * @param genre объект бд
     * @return доменный объект
     */

    public static Genre convertToGenreDomain (GenreModel genre){

        return Genre.builder()
                .id(genre.getId())
                .name(genre.getName()).build();
    }

    /**
     * Преобразует список сущностей в список доменных объектов
     * @param genreList список сущностей
     * @return Список доменных объктов
     */

    public static List<Genre> convertToGenreDomainList (List<GenreModel> genreList){
        return genreList.stream()
                .map(GenreConverter:: convertToGenreDomain)
                .collect(Collectors.toList());
    }

    /**
     * Преобразует доменный объект в DTO
     * @param genre доменный объект
     * @return DTO
     */

    public static GenreData convertToGenreDTO (Genre genre){

        return GenreData.builder()
                .id(genre.getId())
                .name(genre.getName()).build();
    }

    /**
     * Преобразует список доменных объектов в список DTO
     * @param genreList список доменных объектов
     * @return Список DTO
     */

    public static List<GenreData> convertToGenreDTOList (List<Genre> genreList){
        return genreList.stream()
                .map(GenreConverter:: convertToGenreDTO)
                .collect(Collectors.toList());
    }
}
