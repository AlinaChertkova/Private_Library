package com.example.personalLib.Util;

import com.example.personalLib.API.Data.PublishingData;
import com.example.personalLib.DB.Models.PublishingModel;
import com.example.personalLib.Domain.Model.Publishing;
import com.example.personalLib.Domain.Util.PublishingConverter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class PublishingConverterTest {

    @Test
    public void testConvertModelToDomain() {

        final String name = "Alpina";
        final String description = "New pub";

        final PublishingModel publishingModel = new PublishingModel(name, description);
        publishingModel.setId(1L);

        final Publishing publishing = PublishingConverter.convertToPublishingDomain(publishingModel);

        Assert.assertNotNull(publishing);
        Assert.assertEquals(publishing.getId(), publishingModel.getId());
        Assert.assertEquals(publishing.getName(), publishingModel.getName());
        Assert.assertEquals(publishing.getDescription(), publishingModel.getDescription());
    }

    @Test
    public void testConvertListModelToListDomain() {

        List<PublishingModel> models = new ArrayList<>();
        models.add(new PublishingModel("test1", "test1"));
        models.add(new PublishingModel("test2", "test2"));

        List<Publishing> listPublishing = new ArrayList<>(PublishingConverter.convertToPublishingDomainList(models));

        Assert.assertNotNull(listPublishing);
        Assert.assertFalse(listPublishing.isEmpty());

        Assert.assertEquals(listPublishing.get(0).getName(), models.get(0).getName());
        Assert.assertEquals(listPublishing.get(0).getId(), models.get(0).getId());
        Assert.assertEquals(listPublishing.get(0).getDescription(), models.get(0).getDescription());

        Assert.assertEquals(listPublishing.get(1).getName(), models.get(1).getName());
        Assert.assertEquals(listPublishing.get(1).getId(), models.get(1).getId());
        Assert.assertEquals(listPublishing.get(1).getDescription(), models.get(1).getDescription());
    }

    @Test
    public void testConvertDomainToDTO() {

        final String name = "Alpina";
        final String description = "New pub";

        Publishing publishing = Publishing.builder()
                .id(1L)
                .name(name)
                .description(description).build();

        final PublishingData publishingData = PublishingConverter.convertToPublishingDTO(publishing);

        Assert.assertNotNull(publishing);
        Assert.assertEquals(publishing.getId(), publishingData.getId());
        Assert.assertEquals(publishing.getName(), publishingData.getName());
        Assert.assertEquals(publishing.getDescription(), publishingData.getDescription());
    }

    @Test
    public void testConvertListDomainTOListDTO() {

        List<Publishing> publishings = new ArrayList<>();
        publishings.add(Publishing.builder().name("test1").id(1L).description("test1").build());
        publishings.add(Publishing.builder().name("test2").id(2L).description("test2").build());

        List<PublishingData> listPublishingData = new ArrayList<>(PublishingConverter.convertToPublishingDTOList(publishings));

        Assert.assertNotNull(listPublishingData);
        Assert.assertFalse(listPublishingData.isEmpty());

        Assert.assertEquals(listPublishingData.get(0).getName(), publishings.get(0).getName());
        Assert.assertEquals(listPublishingData.get(0).getId(), publishings.get(0).getId());
        Assert.assertEquals(listPublishingData.get(0).getDescription(), publishings.get(0).getDescription());

        Assert.assertEquals(listPublishingData.get(1).getName(), publishings.get(1).getName());
        Assert.assertEquals(listPublishingData.get(1).getId(), publishings.get(1).getId());
        Assert.assertEquals(listPublishingData.get(1).getDescription(), publishings.get(1).getDescription());
    }
}
