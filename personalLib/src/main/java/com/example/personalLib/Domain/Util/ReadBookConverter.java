package com.example.personalLib.Domain.Util;

import com.example.personalLib.API.Data.ReadBookData;
import com.example.personalLib.DB.Models.PublishingModel;
import com.example.personalLib.DB.Models.ReadBookModel;
import com.example.personalLib.Domain.Model.Book;
import com.example.personalLib.Domain.Model.Publishing;
import com.example.personalLib.Domain.Model.ReadBook;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ReadBookConverter {

    public ReadBookConverter(){}

    /**
     * Преобразует из модели бд в доменный объект
     * @param readBook объект бд
     * @return доменный объект
     */

    public static ReadBook convertToReadBookDomain (ReadBookModel readBook){

        return ReadBook.builder()
                .id(readBook.getId())
                .book(BookConverter.convertToBookDomain(readBook.getBook()))
                .mark(readBook.getMark()).build();
    }

    /**
     * Преобразует список сущностей в список доменных объектов
     * @param readBookList список сущностей
     * @return Список доменных объктов
     */

    public static List<ReadBook> convertToReadBookDomainList (List<ReadBookModel> readBookList){
        return readBookList.stream()
                .map(ReadBookConverter :: convertToReadBookDomain)
                .collect(Collectors.toList());
    }

    /**
     * Преобразует доменный объект в DTO
     * @param readBook доменный объект
     * @return DTO
     */

    public static ReadBookData convertToReadBookDTO (ReadBook readBook){

        return ReadBookData.builder()
                .book(BookConverter.convertToBookDTO(readBook.getBook()))
                .id(readBook.getId())
                .mark(readBook.getMark()).build();
    }

    /**
     * Преобразует список доменных объектов в список DTO
     * @param readBookList список доменных объектов
     * @return Список DTO
     */

    public static List<ReadBookData> convertToReadBookDTOList (List<ReadBook> readBookList){
        return readBookList.stream()
                .map(ReadBookConverter :: convertToReadBookDTO)
                .collect(Collectors.toList());
    }
}
