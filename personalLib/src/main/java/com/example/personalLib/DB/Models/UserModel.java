package com.example.personalLib.DB.Models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Users")
public class UserModel {

    public UserModel(){}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private Long id;

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "username", nullable = false)
    private String login;

    @Column(name = "role", nullable = false)
    @ElementCollection(targetClass = Role.class, fetch =  FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "reg_date")
    private LocalDateTime registrationDate;

    private boolean active;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ReadBookModel> userReadList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "User_ToReadBooks",
            joinColumns = { @JoinColumn(name = "User_id") },
            inverseJoinColumns = { @JoinColumn(name = "Book_id") }
    )
    private List<BookModel> userToReadList = new ArrayList<>();

    public List<BookModel> getUserToReadList() {
        return userToReadList;
    }

    public void setUserToReadList(List<BookModel> userToReadList) {
        this.userToReadList = userToReadList;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ReviewModel> userReviews = new ArrayList<>();

    public UserModel(String login, String name, String password, LocalDateTime registrationDate) {

        this.login =login;
        this.name = name;
        this.password = password;
        this.registrationDate = registrationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<ReadBookModel> getUserReadList() {
        return userReadList;
    }

    public void setUserReadList(List<ReadBookModel> userReadList) {
        this.userReadList = userReadList;
    }

    public List<ReviewModel> getUserReviews() {
        return userReviews;
    }

    public void setUserReviews(List<ReviewModel> userReviews) {
        this.userReviews = userReviews;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

   public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getLogin() {
        return login;
    }
}