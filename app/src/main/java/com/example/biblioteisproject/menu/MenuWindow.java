package com.example.biblioteisproject.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.biblioteisproject.API.models.User;
import com.example.biblioteisproject.AvailableBooks.AvailableBooks;
import com.example.biblioteisproject.AvailableBooks.AvailableBooksMV;
import com.example.biblioteisproject.AvailableBooks.BooksListAdapter;
import com.example.biblioteisproject.Profile.ProfileActivity;
import com.example.biblioteisproject.R;
import com.example.biblioteisproject.details.BookDetailActivity;

public class MenuWindow extends AppCompatActivity {

    RecyclerView recommendedBooks;
    RecyclerView newBooks;
    Button buttonL, buttonP;
    private MenuWindowMV menuWindowMV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_window);

        User user = (User) getIntent().getSerializableExtra("user");

        // Inicializa los botones
        buttonL = findViewById(R.id.buttonLibros);
        buttonP = findViewById(R.id.buttonPerfil);

        menuWindowMV = new ViewModelProvider(this).get(MenuWindowMV.class);



        buttonL.setOnClickListener(view -> {
            Intent intent = new Intent(this, AvailableBooks.class);
            intent.putExtra("user", user);
            startActivity(intent);
        });

        buttonP.setOnClickListener(view -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);


        });


        // Configura los RecyclerViews
        recommendedBooks = findViewById(R.id.recommendedBooks);
        newBooks = findViewById(R.id.newBooks);

        menuWindowMV.books.observe(this, books -> {
            recommendedBooks.setAdapter(new BooksListAdapter(books));
        });
        recommendedBooks.setLayoutManager(new LinearLayoutManager(this));


        menuWindowMV.books.observe(this, books -> {
            newBooks.setAdapter(new BooksListAdapter(books));
        });
        newBooks.setLayoutManager(new LinearLayoutManager(this));
    }

}
