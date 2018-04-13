package com.example.demo.Domain;

import java.sql.Date;

public class User {
    private int userID;
    private String name;
    private String email;
    private String password;
    private Date birthday;

    public User(String name, String email, String password, Date birthday, int userID) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.userID=userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserID() {
        return userID;
    }

    public Date getBirthday() {
        return birthday;
    }
}
