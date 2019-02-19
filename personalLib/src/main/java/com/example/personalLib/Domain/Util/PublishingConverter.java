package com.example.personalLib.Domain.Util;

import com.example.personalLib.API.Data.PublishingData;
import com.example.personalLib.DB.Models.BookModel;
import com.example.personalLib.DB.Models.PublishingModel;
import com.example.personalLib.Domain.Model.Book;
import com.example.personalLib.Domain.Model.Publishing;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PublishingConverter {

    public PublishingConverter(){}

    /**
     * Преобразует из модели бд в доменный объект
     * @param publishing объект бд
     * @return доменный объект
     */

    public static Publishing convertToPublishingDomain (PublishingModel publishing){

        return Publishing.builder()
                .id(publishing.getId())
                .name(publishing.getName())
                .description(publishing.getDescription()).build();
    }

    /**
     * Преобразует список сущностей в список доменных объектов
     * @param publishingList список сущностей
     * @return Список доменных объктов
     */

    public static List<Publishing> convertToPublishingDomainList (List<PublishingModel> publishingList){
        return publishingList.stream()
                .map(PublishingConverter :: convertToPublishingDomain)
                .collect(Collectors.toList());
    }

    /**
     * Преобразует доменный объект в DTO
     * @param publishing доменный объект
     * @return DTO
     */

    public static PublishingData convertToPublishingDTO (Publishing publishing){

        return PublishingData.builder()
                .id(publishing.getId())
                .name(publishing.getName())
                .description(publishing.getDescription()).build();
    }

    /**
     * Преобразует список доменных объектов в список DTO
     * @param publishingList список доменных объектов
     * @return Список DTO
     */

    public static List<PublishingData> convertToPublishingDTOList (List<Publishing> publishingList){
        return publishingList.stream()
                .map(PublishingConverter :: convertToPublishingDTO)
                .collect(Collectors.toList());
    }
}
