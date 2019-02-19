package com.example.personalLib.Domain.Model;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Builder
public class User {

    private Long id;
    private String login;
    private String name;
    private String password;
    private boolean active;
    private LocalDateTime registrationDate;
}
