package com.example.a5teamproject;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties

public class User {

    public String id;
    public String password;
    public String name;
    public String birth;

    public User() {

    }

    public User(String id, String password, String name, String birth) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.birth = birth;
    }
    public String getID() {
        return id;
    }

    public void setID(String ID) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

}

