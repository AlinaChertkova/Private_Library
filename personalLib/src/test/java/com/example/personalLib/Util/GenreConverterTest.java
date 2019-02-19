package com.example.personalLib.Util;

import com.example.personalLib.API.Data.GenreData;
import com.example.personalLib.DB.Models.GenreModel;
import com.example.personalLib.Domain.Model.Genre;
import com.example.personalLib.Domain.Util.GenreConverter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class GenreConverterTest {

    @Test
    public void testConvertModelToDomain() {

        final String name = "Humor";

        final GenreModel genreModel = new GenreModel(name);
        genreModel.setId(1L);

        final Genre author = GenreConverter.convertToGenreDomain(genreModel);

        Assert.assertNotNull(author);
        Assert.assertEquals(author.getId(), genreModel.getId());
        Assert.assertEquals(author.getName(), genreModel.getName());
    }

    @Test
    public void testConvertListModelToListDomain() {

        List<GenreModel> models = new ArrayList<>();
        models.add(new GenreModel("test1"));
        models.add(new GenreModel("test2"));

        List<Genre> listGenre = new ArrayList<>(GenreConverter.convertToGenreDomainList(models));

        Assert.assertNotNull(listGenre);
        Assert.assertFalse(listGenre.isEmpty());

        Assert.assertEquals(listGenre.get(0).getName(), models.get(0).getName());
        Assert.assertEquals(listGenre.get(0).getId(), models.get(0).getId());

        Assert.assertEquals(listGenre.get(1).getName(), models.get(1).getName());
        Assert.assertEquals(listGenre.get(1).getId(), models.get(1).getId());
    }

    @Test
    public void testConvertDomainToDTO() {

        final String name = "Humor";

        Genre genre =  Genre.builder()
                .id(1L)
                .name(name).build();

        final GenreData genreData = GenreConverter.convertToGenreDTO(genre);

        Assert.assertNotNull(genre);
        Assert.assertEquals(genre.getId(), genreData.getId());
        Assert.assertEquals(genre.getName(), genreData.getName());
    }

    @Test
    public void testConvertListDomainTOListDTO() {

        List<Genre> genres = new ArrayList<>();
        genres.add(Genre.builder().name("test1").id(1L).build());
        genres.add(Genre.builder().name("test2").id(2L).build());

        List<GenreData> listAuthorData = new ArrayList<>(GenreConverter.convertToGenreDTOList(genres));

        Assert.assertNotNull(listAuthorData);
        Assert.assertFalse(listAuthorData.isEmpty());

        Assert.assertEquals(listAuthorData.get(0).getName(), genres.get(0).getName());
        Assert.assertEquals(listAuthorData.get(0).getId(), genres.get(0).getId());

        Assert.assertEquals(listAuthorData.get(1).getName(), genres.get(1).getName());
        Assert.assertEquals(listAuthorData.get(1).getId(), genres.get(1).getId());
    }
}
