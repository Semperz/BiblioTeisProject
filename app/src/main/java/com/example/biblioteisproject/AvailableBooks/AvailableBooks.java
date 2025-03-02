package com.example.biblioteisproject.AvailableBooks;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.biblioteisproject.Profile.ProfileActivity;
import com.example.biblioteisproject.R;
import com.example.biblioteisproject.details.BookDetailActivity;
import com.example.biblioteisproject.scanner.ScannerQRActivity;
import com.google.android.material.textfield.TextInputEditText;

public class AvailableBooks extends AppCompatActivity {

    TextView tvTitle;
    TextInputEditText etBuscarTitulo, etBuscarAutor;
    Button btnBuscar;
    RecyclerView rvLibros;
    CheckBox chkDisponible;

    Toolbar tbMenuAB;
    private AvailableBooksVM availableBooksVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_books);
        initializeViews();


        availableBooksVM = new ViewModelProvider(this).get(AvailableBooksVM.class);

        availableBooksVM.books.observe(this, books -> {
            rvLibros.setAdapter(new BooksListAdapter(books));
        });

        rvLibros.setLayoutManager(new LinearLayoutManager(this));

        btnBuscar.setOnClickListener((view) -> {
            String titulo = etBuscarTitulo.getText().toString();
            String autor = etBuscarAutor.getText().toString();
            availableBooksVM.getFilteredBooks(titulo, autor);
        });
        //TODO: Implementar la busqueda con las 3 combinaciones, checkbox, titulo y autor
        chkDisponible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String titulo = etBuscarTitulo.getText().toString();
                String autor = etBuscarAutor.getText().toString();
                if (chkDisponible.isChecked()){
                    availableBooksVM.getAvailableBooks();
                }else{
                    availableBooksVM.getFilteredBooks(titulo, autor);
                }
            }
        });

        setSupportActionBar(tbMenuAB);

        addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_principal, menu);
                menu.removeItem(R.id.LibrosDisponibles);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {

                int id= menuItem.getItemId();


                if (id == R.id.Perfil){
                    Intent intent = new Intent(AvailableBooks.this, ProfileActivity.class);
                    startActivity(intent);
                    return true;
                }

                if (id == R.id.Escaner){
                    Intent intent = new Intent(AvailableBooks.this, ScannerQRActivity.class);
                    startActivityForResult(intent, 0);
                    return true;
                }

                return false;
            }
        });

    }



    private void initializeViews() {
        tvTitle = findViewById(R.id.tvTitle);
        etBuscarTitulo = findViewById(R.id.etBuscarTitulo);
        etBuscarAutor = findViewById(R.id.etBuscarAutor);
        btnBuscar = findViewById(R.id.btnBuscar);
        rvLibros = findViewById(R.id.rvLibros);
        chkDisponible = findViewById(R.id.chkDisponible);
        tbMenuAB = findViewById(R.id.tbMenuAB);
    }
}