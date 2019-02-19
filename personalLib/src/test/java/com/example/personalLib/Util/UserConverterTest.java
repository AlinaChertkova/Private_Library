package com.example.personalLib.Util;

import com.example.personalLib.API.Data.UserData;
import com.example.personalLib.DB.Models.Role;
import com.example.personalLib.DB.Models.UserModel;
import com.example.personalLib.Domain.Model.User;
import com.example.personalLib.Domain.Util.UserConverter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
public class UserConverterTest {

    @Test
    public void testConvertModelToDomain() {

        final String login = "Alina";
        final String name = "Alina";
        final String password = "12345678";
        final LocalDateTime registrationDate = LocalDateTime.now();

        final UserModel userModel = new UserModel(login, name, password, registrationDate);
        Set<Role> r  = new HashSet<>();
        r.add(Role.USER);
        userModel.setRoles(r);
        userModel.setId(1L);

        userModel.setActive(true);

        final User user = UserConverter.convertToUserDomain(userModel);

        Assert.assertNotNull(user);
        Assert.assertEquals(user.getId(), userModel.getId());
        Assert.assertEquals(user.getName(), userModel.getName());
        Assert.assertEquals(user.getPassword(), userModel.getPassword());
        Assert.assertEquals(user.isActive(), userModel.isActive());
        Assert.assertEquals(user.getRegistrationDate(), userModel.getRegistrationDate());
    }

    @Test
    public void testConvertListModelToListDomain() {

        List<UserModel> models = new ArrayList<>();
        final UserModel userModel = new UserModel("user1", "user1", "user1", LocalDateTime.now());
        Set<Role> r  = new HashSet<>();
        r.add(Role.USER);
        userModel.setRoles(r);

        final UserModel userModel2 = new UserModel("user2", "user2", "user2", LocalDateTime.now());
        Set<Role> r2  = new HashSet<>();
        r2.add(Role.ADMIN);
        userModel2.setRoles(r2);

        models.add(userModel);
        models.add(userModel2);

        List<User> listUser = new ArrayList<>(UserConverter.convertToUserDomainList(models));

        Assert.assertNotNull(listUser);
        Assert.assertFalse(listUser.isEmpty());

        Assert.assertEquals(listUser.get(0).getId(), models.get(0).getId());
        Assert.assertEquals(listUser.get(0).getName(), models.get(0).getName());
        Assert.assertEquals(listUser.get(0).getPassword(), models.get(0).getPassword());
        Assert.assertEquals(listUser.get(0).isActive(), models.get(0).isActive());
        Assert.assertEquals(listUser.get(0).getRegistrationDate(), models.get(0).getRegistrationDate());

        Assert.assertEquals(listUser.get(1).getId(), models.get(1).getId());
        Assert.assertEquals(listUser.get(1).getName(), models.get(1).getName());
        Assert.assertEquals(listUser.get(1).getPassword(), models.get(1).getPassword());
        Assert.assertEquals(listUser.get(1).isActive(), models.get(1).isActive());
        Assert.assertEquals(listUser.get(1).getRegistrationDate(), models.get(1).getRegistrationDate());
    }

    @Test
    public void testConvertDomainToDTO() {

        final String login = "Alina";
        final String name = "Alina";
        final String password = "12345678";
        final LocalDateTime registrationDate = LocalDateTime.now();

        User user = User.builder()
                .login(login)
                .name(name)
                .id(1L)
                .password(password)
                .registrationDate(registrationDate)
                .active(true).build();

        final UserData userData = UserConverter.convertToUserDTO(user);

        Assert.assertNotNull(user);
        Assert.assertEquals(user.getId(), userData.getId());
        Assert.assertEquals(user.getName(), userData.getName());
        Assert.assertEquals(user.getPassword(), userData.getPassword());
        Assert.assertEquals(user.isActive(), userData.isActive());
        Assert.assertEquals(user.getRegistrationDate(), userData.getRegistrationDate());
    }

    @Test
    public void testConvertListDomainTOListDTO() {

        List<User> users = new ArrayList<>();
        users.add(User.builder().id(1L).login("test1").name("test1").password("test1").active(true).registrationDate(LocalDateTime.now()).build());
        users.add(User.builder().id(1L).login("test2").name("test2").password("test2").active(false).registrationDate(LocalDateTime.now()).build());

        List<UserData> listUserData = new ArrayList<>(UserConverter.convertToUserDTOList(users));

        Assert.assertNotNull(listUserData);
        Assert.assertFalse(listUserData.isEmpty());

        Assert.assertEquals(listUserData.get(0).getId(), users.get(0).getId());
        Assert.assertEquals(listUserData.get(0).getLogin(), users.get(0).getLogin());
        Assert.assertEquals(listUserData.get(0).getName(), users.get(0).getName());
        Assert.assertEquals(listUserData.get(0).getRegistrationDate(), users.get(0).getRegistrationDate());
        Assert.assertEquals(listUserData.get(0).isActive(), users.get(0).isActive());

        Assert.assertEquals(listUserData.get(1).getId(), users.get(1).getId());
        Assert.assertEquals(listUserData.get(1).getLogin(), users.get(1).getLogin());
        Assert.assertEquals(listUserData.get(1).getName(), users.get(1).getName());
        Assert.assertEquals(listUserData.get(1).getRegistrationDate(), users.get(1).getRegistrationDate());
        Assert.assertEquals(listUserData.get(1).isActive(), users.get(1).isActive());
    }
}
