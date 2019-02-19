package com.example.personalLib.Domain.Util;


import com.example.personalLib.API.Data.AuthorData;
import com.example.personalLib.API.Data.EditionData;
import com.example.personalLib.DB.Models.EditionModel;
import com.example.personalLib.Domain.Model.Author;
import com.example.personalLib.Domain.Model.Book;
import com.example.personalLib.Domain.Model.Edition;
import com.example.personalLib.Domain.Model.Publishing;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class EditionConverter {

    public EditionConverter(){}

    /**
     * Преобразует из модели бд в доменный объект
     * @param edition объект бд
     * @return доменный объект
     */

    public static Edition convertToEditionDomain (EditionModel edition){

        return Edition.builder()
                .id(edition.getId())
                .year(edition.getYear())
                .book(BookConverter.convertToBookDomain(edition.getBook()))
                .publishing(PublishingConverter.convertToPublishingDomain(edition.getPublishing()))
                .coverLink(edition.getCoverLink())
                .series(edition.getSeries()).build();
    }

    /**
     * Преобразует список сущностей в список доменных объектов
     * @param editionList список сущностей
     * @return Список доменных объктов
     */

    public static List<Edition> convertToEditionDomainList (List<EditionModel> editionList){
        return editionList.stream()
                .map(EditionConverter :: convertToEditionDomain)
                .collect(Collectors.toList());
    }

    /**
     * Преобразует из доменного объекта в DTO
     * @param edition доменный объект
     * @return DTO
     */
    public static EditionData convertToEditionDTO (Edition edition){

        return EditionData.builder()
                .id(edition.getId())
                .year(edition.getYear())
                .book(BookConverter.convertToBookDTO(edition.getBook()))
                .publishing(PublishingConverter.convertToPublishingDTO(edition.getPublishing()))
                .coverLink(edition.getCoverLink())
                .series(edition.getSeries()).build();
    }

    /**
     * Преобразует доменных объектов в список DTO
     * @param editionList доменных объектов
     * @return Список DTO
     */

    public static List<EditionData> convertToEditionDTOList (List<Edition> editionList){
        return editionList.stream()
                .map(EditionConverter :: convertToEditionDTO)
                .collect(Collectors.toList());
    }
}
