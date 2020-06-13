package com.example.personalLib.controller;

import com.example.personalLib.DB.Models.Role;
import com.example.personalLib.DB.Models.UserModel;
import com.example.personalLib.DB.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepositiry;

    @GetMapping("/registration")
    public String index(Map<String, Object> model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(String username, String password, String name, Map<String, Object> model) {
        try {
            UserModel user1 = userRepositiry.findByLogin(username);
            if (user1 != null)
            {
                String message = "Пользователь в таким логином уже существует!";
                throw new Exception(message);
            }

            UserModel user = new UserModel();
            user.setActive(true);
            user.setPassword(password);
            user.setLogin(username);
            user.setName(name);
            Set<Role> r  = new HashSet<>();
            r.add(Role.USER);
            user.setRoles(r);
            user.setRegistrationDate(LocalDateTime.now());
            userRepositiry.save(user);

            return "login";
        } catch (Exception e) {
            String message = e.getMessage();
            model.put("message", message);
            return "registration";
        }
    }
}
