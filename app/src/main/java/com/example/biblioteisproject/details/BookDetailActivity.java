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
import com.example.biblioteisproject.API.models.BookLending;
import com.example.biblioteisproject.API.models.User;
import com.example.biblioteisproject.API.repository.BookLendingRepository;
import com.example.biblioteisproject.API.repository.BookRepository;
import com.example.biblioteisproject.API.repository.UserProvider;
import com.example.biblioteisproject.R;

import java.util.List;

public class BookDetailActivity extends AppCompatActivity {

    ImageView ImgLibro;
    TextView tvTitle, tvAutor, tvFechaPubli, tvDisponibilidad, tvISBN;
    Button btnPrestar, btnDevolver;
    User user;
    BookLendingRepository bookLendingRepository;
    BookDetailVM bookDetailVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        inicializer();
        bookDetailVM = new ViewModelProvider(this).get(BookDetailVM.class);
        bookDetailVM.getLendings().observe(this, bookLendings -> {
            updateUI(bookDetailVM.book.getValue());
        });
        
        int _book = getIntent().getIntExtra("book", -1);

        assert _book != -1;
        bookDetailVM.fetchBook(_book);
        bookDetailVM.getBook().observe(this, book -> {
            updateUI(book);
        });

        // Botón para prestar el libro
        btnPrestar.setOnClickListener(view ->
                lendBook());

        // Botón para devolver el libro
        btnDevolver.setOnClickListener(view ->
                returnBook());



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
        bookLendingRepository = new BookLendingRepository();
    }


    protected void putData() {

        Book book = bookDetailVM.book.getValue();

        user = UserProvider.getInstance();

        ImgLibro.setImageResource(R.drawable.imagen_no_disponible);

        if(book != null) {
            tvTitle.setText(book.getTitle());
            tvAutor.setText(book.getAuthor());
            tvFechaPubli.setText(book.getPublishedDate());
            tvDisponibilidad.setText(book.isAvailable() ? "Disponible" : "No disponible");
            tvISBN.setText(book.getIsbn());
        }
    }


    @SuppressLint("UseCompatLoadingForColorStateLists")
    protected void updateUI(Book book) {

        if(book == null)
            return;
        putData();
        boolean available = book.isAvailable();

        if (available) {
            tvDisponibilidad.setText("Disponible");
            btnPrestar.setEnabled(true);
            btnPrestar.setBackgroundTintList(getResources().getColorStateList(R.color.teal_700));
            btnDevolver.setEnabled(false);
            btnDevolver.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
        } else if (bookDetailVM.isLentTo(user.getId(), book.getId())) {
            tvDisponibilidad.setText("Prestado");
            btnDevolver.setEnabled(true);
            btnDevolver.setBackgroundTintList(getResources().getColorStateList(R.color.teal_700));
            btnPrestar.setEnabled(false);
            btnPrestar.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
        }else {
            tvDisponibilidad.setText("No disponible");
            btnPrestar.setEnabled(false);
            btnDevolver.setEnabled(false);
            btnPrestar.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
            btnDevolver.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
        }
    }


    protected void lendBook() {
        Book book = bookDetailVM.book.getValue();
        if(book == null)
            return;
        bookLendingRepository.lendBook(user.getId(), book.getId(), new BookRepository.ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                book.setAvailable(false);
                bookDetailVM.fetchLendings();
                updateUI(book);
                Toast.makeText(BookDetailActivity.this, "Se ha prestado el libro", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Toast.makeText(BookDetailActivity.this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void returnBook() {
        Book book = bookDetailVM.book.getValue();
        if(book == null)
            return;
        bookLendingRepository.returnBook(book.getId(), new BookRepository.ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                book.setAvailable(true);
                bookDetailVM.fetchLendings();
                updateUI(book);
                Toast.makeText(BookDetailActivity.this, "Se ha devuelto el libro", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(BookDetailActivity.this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
