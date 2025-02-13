package com.example.biblioteisproject.AvailableBooks;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.biblioteisproject.R;
import com.example.biblioteisproject.books.BooksListAdapter;
import com.google.android.material.textfield.TextInputEditText;

public class AvailableBooks extends AppCompatActivity {

    TextView tvTitle;
    TextInputEditText etBuscarTitulo, etBuscarAutor;
    Button btnBuscar, btnVolver;
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
        btnVolver = findViewById(R.id.btnVolver);
        rvLibros = findViewById(R.id.rvLibros);
    }
}