package com.example.biblioteisproject.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.biblioteisproject.API.models.Book;
import com.example.biblioteisproject.API.models.User;

public class MainActivityVM extends ViewModel {

    MutableLiveData<Book> book;
    MutableLiveData<User> user;

    public MainActivityVM(){
        book = new MutableLiveData<>();
        user = new MutableLiveData<>();
    }



}
