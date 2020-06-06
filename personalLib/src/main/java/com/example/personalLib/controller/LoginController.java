package com.example.personalLib.controller;

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

import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private AuthenticationManager authManager;

    @GetMapping("/login")
    public String greeting(Map<String, Object> model) {
        return "login";
    }

    @PostMapping("/login")
    public String login(String username, String password, Map<String, Object> model) {
        try {
            UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(username, password);

            Authentication auth = authManager.authenticate(authReq);
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
            return "redirect:/";

        } catch (BadCredentialsException ex) {
            String message = "Неверный логин или пароль";
            model.put("message", message);
            return "login";
        } catch (Exception ex) {
            String message = "Ошибка! Пожалуйста, повторите позже";
            model.put("message", message);
            return "login";
        }
    }

//    @GetMapping("/login/loggedout")
//    public String logout(Map<String, Object> model) {
//        model.put("message", "Вы успешно вышли");
//
////        return "redirect:/login";
//        return "login";
//    }
}
