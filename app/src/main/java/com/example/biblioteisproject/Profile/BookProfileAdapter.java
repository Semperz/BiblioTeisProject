package com.example.biblioteisproject.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.biblioteisproject.API.models.Book;
import com.example.biblioteisproject.AvailableBooks.AvailableBooks;
import com.example.biblioteisproject.AvailableBooks.BooksListAdapter;
import com.example.biblioteisproject.R;

import java.util.List;

public class BookProfileAdapter extends BooksListAdapter {


    public BookProfileAdapter(List<Book> books) {
        super(books);
    }
}