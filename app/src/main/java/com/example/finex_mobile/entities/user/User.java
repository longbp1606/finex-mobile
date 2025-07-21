package com.example.finex_mobile.entities.user;

import java.util.ArrayList;
import java.util.List;

public class User {
    private static List<User> userList;
    private static List<User> accountList;
    public static List<User> getUserList() {
        if (userList == null) {
            userList = new ArrayList<>();
            userList.add(new User("Long"));
        }
        return userList;
    }

    public static List<User> getAccountList() {
        if(accountList == null) {
            accountList = new ArrayList<>();
            accountList.add(new User("Phan Long","phanlong","123"));
            accountList.add(new User("Thành Long","thanhlong","123"));
            accountList.add(new User("Huyền Trang","huyentrang","123"));
            accountList.add(new User("Như Nguyên","nhunguyen","123"));
            accountList.add(new User("Lê Nguyên","lenguyen","123"));
        }
        return accountList;
    }

    private String name;
    private String username;
    private String password;

    public User(String name) {
        this.name = name;
    }

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
