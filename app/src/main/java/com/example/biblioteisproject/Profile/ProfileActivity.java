package com.example.biblioteisproject.Profile;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.biblioteisproject.API.models.User;
import com.example.biblioteisproject.AvailableBooks.BooksListAdapter;
import com.example.biblioteisproject.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvName, tvEmail, tvJoinDate;
    private RecyclerView rvBooks;
    private User currentUser;
    private ProfileActivityMV profileWindowMv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Referencias UI
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvJoinDate = findViewById(R.id.tvJoinDate);
        rvBooks = findViewById(R.id.rvBooks);

        // Recuperar el objeto User desde el Intent
        currentUser = (User) getIntent().getSerializableExtra("user");

        // Inicializar el ViewModel
        profileWindowMv = new ViewModelProvider(this).get(ProfileActivityMV.class);

        if (currentUser != null) {
            // Establecer los datos del usuario en la UI
            tvName.setText("Nombre: " + currentUser.getName());
            tvEmail.setText("Email: " + currentUser.getEmail());

            // Formatear la fecha de unión
            String formattedDate = formatDate(currentUser.getDateJoined());
            tvJoinDate.setText("Fecha de unión: " + formattedDate);

            // Obtener los libros prestados por el usuario
            profileWindowMv.getLendedBooks(currentUser.getId());
        }

        // Configurar el RecyclerView
        profileWindowMv.getBooks().observe(this, books -> {
            if (books != null && !books.isEmpty()) {
                // Configurar el adaptador y actualizar el RecyclerView
                BooksListAdapter adapter = new BooksListAdapter(books);
                rvBooks.setAdapter(adapter);
            }
        });

        rvBooks.setLayoutManager(new LinearLayoutManager(this));
    }

    // Método para formatear la fecha
    private String formatDate(String dateStr) {
        try {
            // Suponiendo que la fecha viene en formato "yyyy-MM-dd"
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = inputFormat.parse(dateStr);

            // Ahora formateamos la fecha a "yyyy/MM/dd"
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd");
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateStr; // Si ocurre un error, devolvemos la fecha original
        }
    }
}
