package com.example.personalLib.DB;

import com.example.personalLib.DB.Models.Role;
import com.example.personalLib.DB.Models.UserModel;
import com.example.personalLib.DB.Repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class UserRepTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {

        final String login = "Alina";
        final String role = "Admin";
        final String name = "Alina";
        final String password = "12345678";
        final LocalDateTime registrationDate = LocalDateTime.now();

        final UserModel userModel = new UserModel(login, name, password, registrationDate);
        userModel.setRoles(Collections.singleton(Role.USER));

        final UserModel saved = userRepository.save(userModel);

        Assert.assertNotNull(saved.getId());
        Assert.assertEquals(saved.getName(), userModel.getName());
        Assert.assertEquals(saved.getLogin(), userModel.getLogin());
        Assert.assertEquals(saved.getRoles(), userModel.getRoles());
        Assert.assertEquals(saved.getPassword(), userModel.getPassword());
        Assert.assertEquals(saved.getRegistrationDate(), userModel.getRegistrationDate());

        Optional<UserModel> optSelectedValue = userRepository.findById(saved.getId());

        Assert.assertTrue(optSelectedValue.isPresent());

        final UserModel selectedValue = optSelectedValue.get();

        Assert.assertNotNull(selectedValue.getId());
        Assert.assertEquals(selectedValue.getId(), saved.getId());
        Assert.assertEquals(selectedValue.getName(), userModel.getName());
        Assert.assertEquals(selectedValue.getLogin(), userModel.getLogin());
        Assert.assertEquals(selectedValue.getRoles(), userModel.getRoles());
        Assert.assertEquals(selectedValue.getPassword(), userModel.getPassword());
        Assert.assertEquals(selectedValue.getRegistrationDate(), userModel.getRegistrationDate());
    }

    @Test
    public void testUpdateUser() {

        final String login = "Alina";
        final String role = "Admin";
        final String name = "Alina";
        final String password = "12345678";
        final LocalDateTime registrationDate = LocalDateTime.now();

        final UserModel userModel = new UserModel(login, name, password, registrationDate);
        Set<Role> r  = new HashSet<>();
        r.add(Role.USER);
        userModel.setRoles(r);

        final UserModel saved = userRepository.save(userModel);

        Optional<UserModel> optSelectedValue = userRepository.findById(saved.getId());

        Assert.assertTrue(optSelectedValue.isPresent());

        final UserModel selectedValue = optSelectedValue.get();
        //selectedValue.getRoles().clear();


        selectedValue.setLogin("test_login");
        Set<Role> r1  = new HashSet<>();
        r1.add(Role.ADMIN);
        selectedValue.setRoles(r1);
        selectedValue.setName("test_name");
        selectedValue.setPassword("test_password");

        final UserModel updated = userRepository.save(userModel);

        Assert.assertEquals(selectedValue.getId(), updated.getId());
        Assert.assertEquals(selectedValue.getName(), updated.getName());
        Assert.assertEquals(selectedValue.getLogin(), updated.getLogin());
        Assert.assertEquals(selectedValue.getRoles(), updated.getRoles());
        Assert.assertEquals(selectedValue.getPassword(), updated.getPassword());
        Assert.assertEquals(selectedValue.getRegistrationDate(), updated.getRegistrationDate());
    }

    @Test
    public void testDeleteUser() {

        final String login = "Alina";
        final String role = "Admin";
        final String name = "Alina";
        final String password = "12345678";
        final LocalDateTime registrationDate = LocalDateTime.now();

        final UserModel userModel = new UserModel(login, name, password, registrationDate);
        userModel.setRoles(Collections.singleton(Role.USER));

        final UserModel saved = userRepository.save(userModel);

        Optional<UserModel> optSelectedValue = userRepository.findById(saved.getId());

        Assert.assertTrue(optSelectedValue.isPresent());

        final UserModel selectedValue = optSelectedValue.get();

        userRepository.delete(selectedValue);

        Optional<UserModel> optSelectedValue2 = userRepository.findById(saved.getId());

        Assert.assertFalse(optSelectedValue2.isPresent());
    }
}
