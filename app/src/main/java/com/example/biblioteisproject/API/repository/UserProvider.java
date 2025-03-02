package com.example.biblioteisproject.API.repository;

import com.example.biblioteisproject.API.models.User;

public class UserProvider{

    private static User instance;

    private UserProvider() {
    }
    public static synchronized User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public static void setInstance(User user) {
        instance = user;
    }

    public static void clearInstance() {
        instance = null;
    }
}
