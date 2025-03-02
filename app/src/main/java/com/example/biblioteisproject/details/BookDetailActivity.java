package com.example.biblioteisproject.details;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.lifecycle.ViewModelProvider;
import com.example.biblioteisproject.API.models.Book;
import com.example.biblioteisproject.API.models.User;
import com.example.biblioteisproject.API.repository.BookLendingRepository;
import com.example.biblioteisproject.API.repository.BookRepository;
import com.example.biblioteisproject.API.repository.UserProvider;
import com.example.biblioteisproject.AvailableBooks.AvailableBooks;
import com.example.biblioteisproject.Profile.ProfileActivity;
import com.example.biblioteisproject.R;

import com.example.biblioteisproject.scanner.ScannerQRActivity;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import okhttp3.ResponseBody;

public class BookDetailActivity extends AppCompatActivity {

    ImageView ImgLibro;
    TextView tvTitle, tvAutor, tvFechaPubli, tvDisponibilidad, tvISBN;
    Button btnPrestar, btnDevolver;
    User user;
    BookLendingRepository bookLendingRepository;
    BookDetailVM bookDetailVM;

    Toolbar tbMenuBD;

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

        if (_book != -1) {
            bookDetailVM.fetchBook(_book);
        }
        bookDetailVM.getBook().observe(this, book -> {
            updateUI(book);
        });

        // Botón para prestar el libro
        btnPrestar.setOnClickListener(view ->
                lendBook());

        // Botón para devolver el libro
        btnDevolver.setOnClickListener(view ->
                returnBook());

        setSupportActionBar(tbMenuBD);

        addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_principal, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {

                int id= menuItem.getItemId();

                if (id == R.id.LibrosDisponibles){
                    Intent intent = new Intent(BookDetailActivity.this, AvailableBooks.class);
                    startActivity(intent);
                    return true;
                }

                if (id == R.id.Perfil){
                    Intent intent = new Intent(BookDetailActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    return true;
                }

                if (id == R.id.Escaner){
                    Intent intent = new Intent(BookDetailActivity.this, ScannerQRActivity.class);
                    startActivityForResult(intent, 0);
                    return true;
                }

                return false;
            }
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
        tbMenuBD = findViewById(R.id.tbMenuBD);
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

            if(book.getBookPicture()!= null && !book.getBookPicture().isEmpty()){
                cargarImagenLibro();
            }
        }
    }


    private void cargarImagenLibro() {
        bookDetailVM.getBookImage().observe(this, bitmap -> {
            if (bitmap != null) {
                ImgLibro.setImageBitmap(bitmap);
            } else {
                ImgLibro.setImageResource(R.drawable.imagen_no_disponible);
            }
        });
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
                if(!result){
                    Toast.makeText(BookDetailActivity.this, "No se ha podido prestar el libro.", Toast.LENGTH_SHORT).show();
                    return;
                }
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
                //El result de returnBook es si se ha realizado el retorno del libro con exito o no.
                if(!result){
                    Toast.makeText(BookDetailActivity.this, "No se ha podido devolver el libro.", Toast.LENGTH_SHORT).show();
                    return;
                }

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
