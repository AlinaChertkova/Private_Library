package com.example.personalLib.DB;

import com.example.personalLib.DB.Models.AuthorModel;
import com.example.personalLib.DB.Repository.AuthorRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class AuthorRepTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void testSaveAuthor() {

        final String name = "Diana Wiynne Jones";
        final String description = "Storytailer";

        final AuthorModel authorModel = new AuthorModel(name, description);

        final AuthorModel saved = authorRepository.save(authorModel);

        Assert.assertNotNull(saved.getId());
        Assert.assertEquals(saved.getName(), authorModel.getName());
        Assert.assertEquals(saved.getDescription(), authorModel.getDescription());

        Optional<AuthorModel> optSelectedValue = authorRepository.findById(saved.getId());

        Assert.assertTrue(optSelectedValue.isPresent());

        final AuthorModel selectedValue = optSelectedValue.get();

        Assert.assertNotNull(selectedValue.getId());
        Assert.assertEquals(selectedValue.getId(), saved.getId());
        Assert.assertEquals(selectedValue.getName(), authorModel.getName());
        Assert.assertEquals(selectedValue.getDescription(), authorModel.getDescription());
    }

    @Test
    public void testUpdateAuthor() {

        final String name = "Diana Wiynne Jones";
        final String description = "Storytailer";

        final AuthorModel authorModel = new AuthorModel(name, description);

        final AuthorModel saved = authorRepository.save(authorModel);

        Optional<AuthorModel> optSelectedValue = authorRepository.findById(saved.getId());

        Assert.assertTrue(optSelectedValue.isPresent());

        final AuthorModel selectedValue = optSelectedValue.get();

        selectedValue.setName("test_name");
        selectedValue.setDescription("test_description");

        final AuthorModel updated = authorRepository.save(authorModel);

        Assert.assertEquals(selectedValue.getId(), updated.getId());
        Assert.assertEquals(selectedValue.getName(), updated.getName());
        Assert.assertEquals(selectedValue.getDescription(), updated.getDescription());
    }

    @Test
    public void testDeleteAuthor() {

        final String name = "Diana Wiynne Jones";
        final String description = "Storytailer";

        final AuthorModel authorModel = new AuthorModel(name, description);

        final AuthorModel saved = authorRepository.save(authorModel);

        Optional<AuthorModel> optSelectedValue = authorRepository.findById(saved.getId());

        Assert.assertTrue(optSelectedValue.isPresent());

        final AuthorModel selectedValue = optSelectedValue.get();

        authorRepository.delete(selectedValue);

        Optional<AuthorModel> optSelectedValue2 = authorRepository.findById(saved.getId());

        Assert.assertFalse(optSelectedValue2.isPresent());
    }
}

