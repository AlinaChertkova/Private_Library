package com.example.personalLib.Util;

import com.example.personalLib.DB.Models.BookModel;
import com.example.personalLib.DB.Models.EditionModel;
import com.example.personalLib.DB.Models.PublishingModel;
import com.example.personalLib.Domain.Model.Edition;
import com.example.personalLib.Domain.Util.EditionConverter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class EditionConverterTest {

    @Test
    public void testConvertModelToDomain() {

        final String ISBN = "978-5-389-02467-0";
        final String title = "June";
        final String description = "Drama";
        final String coverLink = "link";
        final double avgRating = 4.1;
        final int markCount = 4;

        final BookModel bookModel = new BookModel(ISBN, title, description, coverLink, avgRating, markCount);

        final String name = "Alpina";
        final String pubDescription = "New pub";

        final PublishingModel publishingModel = new PublishingModel(name, pubDescription);

        final EditionModel editionModel = new EditionModel(publishingModel, 1999, "test_link", "Roza vetrov", bookModel);

        final Edition edition = EditionConverter.convertToEditionDomain(editionModel);

        Assert.assertNotNull(edition);
        Assert.assertEquals(edition.getId(), editionModel.getId());
        Assert.assertEquals(edition.getSeries(), editionModel.getSeries());
        Assert.assertEquals(edition.getCoverLink(), editionModel.getCoverLink());
        Assert.assertEquals(edition.getYear(),  editionModel.getYear());
    }

    @Test
    public void testConvertListModelToListDomain() {

        final BookModel bookModel = new BookModel("book1", "book1", "book1", "book1", 123, 4);
        final BookModel bookModel2 = new BookModel("book2", "book2", "book2", "book2", 12.45, 4);

        final PublishingModel publishingModel = new PublishingModel("publishing1", "publishing1");
        final PublishingModel publishingModel2 = new PublishingModel("publishing2", "publishing2");

        List<EditionModel> models = new ArrayList<>();
        models.add(new EditionModel(publishingModel, 1999, "test_link", "Roza vetrov", bookModel));
        models.add(new EditionModel(publishingModel2, 2010, "test_link2", "Arkadia", bookModel2));

        List<Edition> listEdition = new ArrayList<>(EditionConverter.convertToEditionDomainList(models));

        Assert.assertNotNull(listEdition);
        Assert.assertFalse(listEdition.isEmpty());

        double myPrecision = 0.0001;

        Assert.assertEquals(listEdition.get(0).getYear(), models.get(0).getYear());
        Assert.assertEquals(listEdition.get(0).getSeries(), models.get(0).getSeries());
        Assert.assertEquals(listEdition.get(0).getId(), models.get(0).getId());
        Assert.assertEquals(listEdition.get(0).getCoverLink(), models.get(0).getCoverLink());

        Assert.assertEquals(listEdition.get(1).getYear(), models.get(1).getYear());
        Assert.assertEquals(listEdition.get(1).getSeries(), models.get(1).getSeries());
        Assert.assertEquals(listEdition.get(1).getId(), models.get(1).getId());
        Assert.assertEquals(listEdition.get(1).getCoverLink(), models.get(1).getCoverLink());
    }

//    @Test
//    public void testConvertDomainToDTO() {
//
//        final Book bookModel = new Book("book1", "book1", "book1", "book1", 123);
//        Edition edition = Edition.builder()
//                .id(1L)
//                .year(1994)
//                .coverLink("link")
//                .book(bookModel)
//                .series("NeoClassic").build();
//
//        final EditionData editionData = EditionConverter.convertToEditionDTO(edition);
//
//        Assert.assertNotNull(edition);
//        Assert.assertEquals(edition.getId(), editionData.getId());
//        Assert.assertEquals(edition.getSeries(), editionData.getSeries());
//        Assert.assertEquals(edition.getCoverLink(), editionData.getCoverLink());
//        Assert.assertEquals(edition.getYear(),  editionData.getYear());
//    }
//
//    @Test
//    public void testConvertListDomainTOListDTO() {
//
//        List<Edition> editions = new ArrayList<>();
//        editions.add(Edition.builder().id(1L).year(1994).coverLink("link1").series("NeoClassic").build());
//        editions.add(Edition.builder().id(1L).year(2015).coverLink("link2").series("ExClassic").build());
//
//        List<EditionData> listEditionData = new ArrayList<>(EditionConverter.convertToEditionDTOList(editions));
//
//        Assert.assertNotNull(listEditionData);
//        Assert.assertFalse(listEditionData.isEmpty());
//
//        double myPrecision = 0.0001;
//
//        Assert.assertEquals(listEditionData.get(0).getYear(), editions.get(0).getYear());
//        Assert.assertEquals(listEditionData.get(0).getCoverLink(), editions.get(0).getCoverLink());
//        Assert.assertEquals(listEditionData.get(0).getSeries(), editions.get(0).getSeries());
//        Assert.assertEquals(listEditionData.get(0).getId(), editions.get(0).getId());
//
//        Assert.assertEquals(listEditionData.get(1).getYear(), editions.get(1).getYear());
//        Assert.assertEquals(listEditionData.get(1).getCoverLink(), editions.get(1).getCoverLink());
//        Assert.assertEquals(listEditionData.get(1).getSeries(), editions.get(1).getSeries());
//        Assert.assertEquals(listEditionData.get(1).getId(), editions.get(1).getId());
//    }
}
