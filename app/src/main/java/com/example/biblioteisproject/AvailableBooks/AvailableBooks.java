package com.example.biblioteisproject.AvailableBooks;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.biblioteisproject.API.models.User;
import com.example.biblioteisproject.R;
import com.example.biblioteisproject.menu.MenuWindow;
import com.google.android.material.textfield.TextInputEditText;

public class AvailableBooks extends AppCompatActivity {

    TextView tvTitle;
    TextInputEditText etBuscarTitulo, etBuscarAutor;
    Button btnBuscar;
    RecyclerView rvLibros;
    private AvailableBooksMV availableBooksMV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_books);
        initializeViews();


        availableBooksMV = new ViewModelProvider(this).get(AvailableBooksMV.class);

        availableBooksMV.books.observe(this, books -> {
            rvLibros.setAdapter(new BooksListAdapter(books));
        });

        rvLibros.setLayoutManager(new LinearLayoutManager(this));

        btnBuscar.setOnClickListener((view) -> {
            String titulo = etBuscarTitulo.getText().toString();
            String autor = etBuscarAutor.getText().toString();
            availableBooksMV.getFilteredBooks(titulo, autor);
        });





    }



    private void initializeViews() {
        tvTitle = findViewById(R.id.tvTitle);
        etBuscarTitulo = findViewById(R.id.etBuscarTitulo);
        etBuscarAutor = findViewById(R.id.etBuscarAutor);
        btnBuscar = findViewById(R.id.btnBuscar);
        rvLibros = findViewById(R.id.rvLibros);
    }
}