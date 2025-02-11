package com.example.biblioteisproject.books;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.biblioteisproject.R;
import com.google.android.material.textfield.TextInputEditText;

public class AvailableBooks extends AppCompatActivity {

    TextView tvTitle;
    TextInputEditText etBuscarTitulo, etBuscarAutor;
    Button btnBuscar, btnVolver;
    RecyclerView rvLibros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_books);
        initializeViews();


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