package com.example.personalLib.Util;

import com.example.personalLib.API.Data.AuthorData;
import com.example.personalLib.DB.Models.AuthorModel;
import com.example.personalLib.Domain.Model.Author;
import com.example.personalLib.Domain.Util.AuthorConverter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class AuthorConverterTest {

    @Test
    public void testConvertModelToDomain() {

        final String name = "Diana Wiynne Jones";
        final String description = "Storytailer";

        final AuthorModel authorModel = new AuthorModel(name, description);
        authorModel.setId(1L);

        final Author author = AuthorConverter.convertToAuthorDomain(authorModel);

        Assert.assertNotNull(author);
        Assert.assertEquals(author.getId(), authorModel.getId());
        Assert.assertEquals(author.getName(), authorModel.getName());
        Assert.assertEquals(author.getDescription(), authorModel.getDescription());
    }

    @Test
    public void testConvertListModelToListDomain() {

        List<AuthorModel> models = new ArrayList<>();
        models.add(new AuthorModel("test1", "test1"));
        models.add(new AuthorModel("test2", "test2"));

        List<Author> listAuthor = new ArrayList<>(AuthorConverter.convertToAuthorDomainList(models));

        Assert.assertNotNull(listAuthor);
        Assert.assertFalse(listAuthor.isEmpty());

        Assert.assertEquals(listAuthor.get(0).getName(), models.get(0).getName());
        Assert.assertEquals(listAuthor.get(0).getDescription(), models.get(0).getDescription());

        Assert.assertEquals(listAuthor.get(1).getName(), models.get(1).getName());
        Assert.assertEquals(listAuthor.get(1).getDescription(), models.get(1).getDescription());
    }

    @Test
    public void testConvertDomainToDTO() {

        final String name = "Diana Wiynne Jones";
        final String description = "Storytailer";

       Author author =  Author.builder()
                .id(1L)
                .name(name)
                .description(description).build();

        final AuthorData authorData = AuthorConverter.convertToAuthorDTO(author);

        Assert.assertNotNull(author);
        Assert.assertEquals(author.getId(), authorData.getId());
        Assert.assertEquals(author.getName(), authorData.getName());
        Assert.assertEquals(author.getDescription(), authorData.getDescription());
    }

    @Test
    public void testConvertListDomainTOListDTO() {

        List<Author> authors = new ArrayList<>();
        authors.add(Author.builder().name("test1").description("test1").build());
        authors.add(Author.builder().name("test2").description("test2").build());

        List<AuthorData> listAuthorData = new ArrayList<>(AuthorConverter.convertToAuthorDTOList(authors));

        Assert.assertNotNull(listAuthorData);
        Assert.assertFalse(listAuthorData.isEmpty());

        Assert.assertEquals(listAuthorData.get(0).getName(), authors.get(0).getName());
        Assert.assertEquals(listAuthorData.get(0).getDescription(), authors.get(0).getDescription());

        Assert.assertEquals(listAuthorData.get(1).getName(), authors.get(1).getName());
        Assert.assertEquals(listAuthorData.get(1).getDescription(), authors.get(1).getDescription());
    }
}
