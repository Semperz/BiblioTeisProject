    package com.example.biblioteisproject.Profile;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.biblioteisproject.API.models.User;
import com.example.biblioteisproject.R;

public class ProfileActivity extends AppCompatActivity {
    private TextView tvName, tvEmail, tvJoinDate;
    private ImageView ivProfilePicture;
    private RecyclerView rvBooks;
    private BookProfileAdapter bookAdapter;
    private User currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Referencias UI
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvJoinDate = findViewById(R.id.tvJoinDate);
        rvBooks = findViewById(R.id.rvBooks);

        // Obtener datos del usuario (debería venir de una API o BD, aquí es simulado)
        currentUser = getUserData();

        // Establecer datos en la UI
        tvName.setText("Nombre: " + currentUser.getName());
        tvEmail.setText("Email: " + currentUser.getEmail());
        tvJoinDate.setText("Fecha de unión: " + currentUser.getDateJoined());


        // Configurar RecyclerView
        rvBooks.setLayoutManager(new LinearLayoutManager(this));

    }

    // Simulación de obtención de datos del usuario (sustituir con API real)
    private User getUserData() {
        User user = new User();
        user.setId(1);
        user.setName("Juan Pérez");
        user.setEmail("juanperez@email.com");
        user.setDateJoined("12/03/2022");
        user.setProfilePicture("https://example.com/profile.jpg");


        user.setBookLendings(null); // Aquí puedes asignar la lista de préstamos reales si la tienes
        return user;
    }

}
