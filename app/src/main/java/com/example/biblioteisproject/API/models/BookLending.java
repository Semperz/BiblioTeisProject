package com.example.biblioteisproject.API.models;

import java.io.Serializable;

public class BookLending implements Serializable {

    private int bookId;
    private int userId;

    // Getters & Setters

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}