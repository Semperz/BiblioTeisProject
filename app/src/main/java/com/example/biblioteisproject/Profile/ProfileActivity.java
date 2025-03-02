package com.example.biblioteisproject.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.biblioteisproject.API.models.Book;
import com.example.biblioteisproject.API.models.User;
import com.example.biblioteisproject.API.repository.UserProvider;
import com.example.biblioteisproject.AvailableBooks.BooksListAdapter;
import com.example.biblioteisproject.R;
import com.example.biblioteisproject.login.CredentialsManager;
import com.example.biblioteisproject.login.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvName, tvEmail, tvJoinDate;
    private RecyclerView rvBooks;
    private User currentUser;
    private ProfileActivityVM profileWindowMv;
    private Toolbar TbProfile;
    private CredentialsManager credentialsManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TbProfile = findViewById(R.id.tbProfile);
        credentialsManager = new CredentialsManager(this);

        // Referencias UI
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvJoinDate = findViewById(R.id.tvJoinDate);
        rvBooks = findViewById(R.id.rvBooks);

        // Recuperar el objeto User desde el Intent
        currentUser = UserProvider.getInstance();

        // Inicializar el ViewModel
        profileWindowMv = new ViewModelProvider(this).get(ProfileActivityVM.class);

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
                sortBooksByLendDate(books);
                BookProfileAdapter adapter = new BookProfileAdapter(books);
                rvBooks.setAdapter(adapter);
                adapter.notifyDataSetChanged(); // Asegurar que los cambios se reflejan
            }
        });

        rvBooks.setLayoutManager(new LinearLayoutManager(this));

        setSupportActionBar(TbProfile);

        addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_perfil, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {

                int id= menuItem.getItemId();

                if (id == R.id.mbiCerrarSesion){
                    credentialsManager.clearSavedCredentials();
                    Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                    // asegura que el login se empiece como una nueva tarea, y si ya hay una instancia de la actividad en la pila de actividades, se elimina
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });

    }

    // Método para formatear la fecha
    private String formatDate(String dateStr) {
        try {
            // Suponiendo que la fecha viene en formato "yyyy-MM-dd"
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = inputFormat.parse(dateStr);

            // Ahora formateamos la fecha a "yyyy/MM/dd"
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateStr; // Si ocurre un error, devolvemos la fecha original
        }
    }

    // Método para ordenar los libros por fecha de préstamo
    private void sortBooksByLendDate(List<Book> lendedBooks) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        Collections.sort(lendedBooks, (o1, o2) -> {
            try {
                if (o1.getBookLendings() == null || o2.getBookLendings() == null ||
                        o1.getBookLendings().isEmpty() || o2.getBookLendings().isEmpty()) {
                    return 0;
                }
                Date date1 = sdf.parse(o1.getBookLendings().get(0).getLendDate());
                Date date2 = sdf.parse(o2.getBookLendings().get(0).getLendDate());
                return date1.compareTo(date2); // Más antiguos primero
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        });
    }
}
