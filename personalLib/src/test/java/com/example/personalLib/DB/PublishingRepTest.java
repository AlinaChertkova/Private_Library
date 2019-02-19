package com.example.personalLib.DB;

import com.example.personalLib.DB.Models.PublishingModel;
import com.example.personalLib.DB.Repository.PublishingRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PublishingRepTest {

    @Autowired
    private PublishingRepository publishingRepository;

    @Test
    public void testSavePublishing() {

        final String name = "Alpina";
        final String description = "New pub";

        final PublishingModel publishingModel = new PublishingModel(name, description);

        final PublishingModel saved = publishingRepository.save(publishingModel);

        Assert.assertNotNull(saved.getId());
        Assert.assertEquals(saved.getName(), publishingModel.getName());
        Assert.assertEquals(saved.getDescription(), publishingModel.getDescription());

        Optional<PublishingModel> optSelectedValue = publishingRepository.findById(saved.getId());

        Assert.assertTrue(optSelectedValue.isPresent());

        final PublishingModel selectedValue = optSelectedValue.get();

        Assert.assertNotNull(selectedValue.getId());
        Assert.assertEquals(selectedValue.getId(), saved.getId());
        Assert.assertEquals(selectedValue.getName(), publishingModel.getName());
        Assert.assertEquals(selectedValue.getDescription(), publishingModel.getDescription());
    }

    @Test
    public void testUpdatePublishing() {

        final String name = "Alpina";
        final String description = "New pub";

        final PublishingModel publishingModel = new PublishingModel(name, description);

        final PublishingModel saved = publishingRepository.save(publishingModel);

        Optional<PublishingModel> optSelectedValue = publishingRepository.findById(saved.getId());

        Assert.assertTrue(optSelectedValue.isPresent());

        final PublishingModel selectedValue = optSelectedValue.get();

        selectedValue.setName("test_name");
        selectedValue.setDescription("test_description");

        final PublishingModel updated = publishingRepository.save(publishingModel);

        Assert.assertEquals(selectedValue.getId(), updated.getId());
        Assert.assertEquals(selectedValue.getName(), updated.getName());
        Assert.assertEquals(selectedValue.getDescription(), updated.getDescription());
    }

    @Test
    public void testDeletePublishing() {

        final String name = "Alpina";
        final String description = "New pub";

        final PublishingModel publishingModel = new PublishingModel(name, description);

        final PublishingModel saved = publishingRepository.save(publishingModel);

        Optional<PublishingModel> optSelectedValue = publishingRepository.findById(saved.getId());

        Assert.assertTrue(optSelectedValue.isPresent());

        final PublishingModel selectedValue = optSelectedValue.get();

        publishingRepository.delete(selectedValue);

        Optional<PublishingModel> optSelectedValue2 = publishingRepository.findById(saved.getId());

        Assert.assertFalse(optSelectedValue2.isPresent());
    }
}
