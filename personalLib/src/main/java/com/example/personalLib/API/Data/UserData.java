package com.example.personalLib.API.Data;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserData {

    private Long id;
    private String login;
    private String name;
    private String password;
    private boolean active;
    private LocalDateTime registrationDate;
}
