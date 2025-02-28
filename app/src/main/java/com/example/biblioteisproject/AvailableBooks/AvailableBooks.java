package com.example.biblioteisproject.AvailableBooks;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.biblioteisproject.R;
import com.google.android.material.textfield.TextInputEditText;

public class AvailableBooks extends AppCompatActivity {

    TextView tvTitle;
    TextInputEditText etBuscarTitulo, etBuscarAutor;
    Button btnBuscar;
    RecyclerView rvLibros;
    CheckBox chkDisponible;
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


    }



    private void initializeViews() {
        tvTitle = findViewById(R.id.tvTitle);
        etBuscarTitulo = findViewById(R.id.etBuscarTitulo);
        etBuscarAutor = findViewById(R.id.etBuscarAutor);
        btnBuscar = findViewById(R.id.btnBuscar);
        rvLibros = findViewById(R.id.rvLibros);
        chkDisponible = findViewById(R.id.chkDisponible);
    }
}