package com.example.biblioteisproject.details;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.biblioteisproject.API.models.Book;
import com.example.biblioteisproject.API.models.User;
import com.example.biblioteisproject.API.repository.BookLendingRepository;
import com.example.biblioteisproject.API.repository.BookRepository;
import com.example.biblioteisproject.API.repository.UserProvider;
import com.example.biblioteisproject.R;

public class BookDetailActivity extends AppCompatActivity {

    ImageView ImgLibro;
    TextView tvTitle, tvAutor, tvFechaPubli, tvDisponibilidad, tvISBN;
    Button btnPrestar, btnVolver, btnDevolver;
    Book book;
    User user;
    BookLendingRepository bookLendingRepository;

    BookDetailVM bookDetailVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        inicializer();
        bookDetailVM = new ViewModelProvider(this).get(BookDetailVM.class);
        putData();


        updateUI();


        // Botón para prestar el libro
        btnPrestar.setOnClickListener(view -> lendBook());

        // Botón para devolver el libro
        btnDevolver.setOnClickListener(view -> returnBook());

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
        bookDetailVM.getLendings();

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
            btnPrestar.setBackgroundTintList(getResources().getColorStateList(R.color.teal_700));
            btnDevolver.setEnabled(false);
            btnDevolver.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
        } else if (bookDetailVM.isLentTo(user.getId())) {
            btnDevolver.setEnabled(true);
            btnDevolver.setBackgroundTintList(getResources().getColorStateList(R.color.teal_700));
            btnPrestar.setEnabled(false);
            btnPrestar.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
        }else {
            btnPrestar.setEnabled(false);
            btnDevolver.setEnabled(false);
            btnPrestar.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
            btnDevolver.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
        }
    }


    protected void lendBook() {
        bookLendingRepository.lendBook(book.getId(), user.getId(), new BookRepository.ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                book.setAvailable(false);
                updateUI();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    protected void returnBook() {
        bookLendingRepository.returnBook(book.getId(), new BookRepository.ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                book.setAvailable(true);
                updateUI();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
