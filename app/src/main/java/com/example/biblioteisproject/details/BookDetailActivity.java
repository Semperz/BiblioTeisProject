package com.example.biblioteisproject.details;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.biblioteisproject.API.models.Book;
import com.example.biblioteisproject.API.models.User;
import com.example.biblioteisproject.API.repository.BookLendingRepository;
import com.example.biblioteisproject.API.repository.UserProvider;
import com.example.biblioteisproject.R;

public class BookDetailActivity extends AppCompatActivity {

    ImageView ImgLibro;
    TextView tvTitle, tvAutor, tvFechaPubli, tvDisponibilidad, tvISBN;
    Button btnPrestar, btnVolver, btnDevolver;
    Book book;
    User user;
    BookLendingRepository bookLendingRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        inicializer();
        putData();
        updateUI();

/*        // Botón para prestar el libro
        btnPrestar.setOnClickListener(view -> lendBook());

        // Botón para devolver el libro
        btnDevolver.setOnClickListener(view -> returnBook());*/

        // Botón para volver
        btnVolver.setOnClickListener(view -> finish());


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
        bookLendingRepository = new BookLendingRepository();
    }

    protected void putData() {
        book = (Book) getIntent().getSerializableExtra("book");
        user = UserProvider.getInstance();

        ImgLibro.setImageResource(R.drawable.imagen_no_disponible);
        tvTitle.setText(book.getTitle());
        tvAutor.setText(book.getAuthor());
        tvFechaPubli.setText(book.getPublishedDate());
        tvDisponibilidad.setText(book.isAvailable() ? "Disponible" : "No disponible");
        tvISBN.setText(book.getIsbn());
    }


    @SuppressLint("UseCompatLoadingForColorStateLists")
    protected void updateUI() {
        boolean available = book.isAvailable();

        if (available) {
            btnPrestar.setEnabled(true);
            btnDevolver.setEnabled(false);
            btnDevolver.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
        } else {
            btnDevolver.setEnabled(true);
            btnPrestar.setEnabled(false);
            btnPrestar.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
        }

    }

    /** 🔹 Prestar un libro *//*
    protected void lendBook() {
        bookLendingRepository.lendBook(user.getId(), book.getId(), (response) -> {

            if (response) {
                Toast.makeText(this, "Libro prestado con éxito", Toast.LENGTH_SHORT).show();
                book.setAvailable(false);
                updateUI();
            } else {
                Toast.makeText(this, "Error al prestar el libro", Toast.LENGTH_SHORT).show();
            }
        });
    }

    *//** 🔹 Devolver un libro *//*
    protected void returnBook() {
        bookLendingRepository.returnBook(book.getId(), (response) -> {
            runOnUiThread(() -> {
                if (response) {
                    Toast.makeText(this, "Libro devuelto con éxito", Toast.LENGTH_SHORT).show();
                    book.setAvailable(true);
                    updateUI();
                } else {
                    Toast.makeText(this, "Error al devolver el libro", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }*/
}
