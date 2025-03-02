package com.example.biblioteisproject.API.models;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    private int id;
    private String name;
    private String email;
    private String passwordHash;
    private String dateJoined;
    private List<BookLending> bookLendings;
    private String profilePicture;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;
    }

    public List<BookLending> getBookLendings() {
        return bookLendings;
    }

    public void setBookLendings(List<BookLending> bookLendings) {
        this.bookLendings = bookLendings;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }



    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
