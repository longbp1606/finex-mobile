package com.example.finex_mobile.entities;

import java.util.ArrayList;
import java.util.List;

public class User {
    private static List<User> userList;
    public static List<User> getUserList() {
        if (userList == null) {
            userList = new ArrayList<>();
            userList.add(new User("Long"));
        }
        return userList;
    }

    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
