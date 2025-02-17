package com.example.biblioteisproject.details;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.biblioteisproject.API.models.Book;
import com.example.biblioteisproject.AvailableBooks.AvailableBooks;
import com.example.biblioteisproject.R;

public class BookDetailActivity extends AppCompatActivity {

    ImageView ImgLibro;
    TextView tvTitle, tvAutor, tvFechaPubli, tvDisponibilidad,tvISBN;
    Button btnPrestar, btnVolver, btnDevolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        inicializer();
        putData();

        btnVolver.setOnClickListener(view -> {
            finish();
        });
    }

    protected void inicializer() {
        ImgLibro = findViewById(R.id.ImgLibro);
        tvTitle = findViewById(R.id.tvTitle);
        tvAutor = findViewById(R.id.tvAutor);
        tvFechaPubli = findViewById(R.id.tvFechaPubli);
        tvDisponibilidad = findViewById(R.id.tvDisponibilidad);
        tvISBN = findViewById(R.id.tvISBN);
        btnPrestar = findViewById(R.id.btnPrestar);
        btnDevolver = findViewById(R.id.btnDevolver);
        btnVolver = findViewById(R.id.btnVolver);
    }

    protected void putData() {
        Book book = (Book) getIntent().getSerializableExtra("book");
        ImgLibro.setImageResource(R.drawable.imagen_no_disponible);
        tvTitle.setText(book.getTitle());
        tvAutor.setText(book.getAuthor());
        tvFechaPubli.setText(book.getPublishedDate());
        tvDisponibilidad.setText(book.isAvailable() ? "Disponible" : "No disponible");
        tvISBN.setText(book.getIsbn());
    }


}