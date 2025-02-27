package com.example.biblioteisproject.login;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.biblioteisproject.API.models.User;
import com.example.biblioteisproject.API.repository.BookRepository;
import com.example.biblioteisproject.API.repository.UserProvider;
import com.example.biblioteisproject.API.repository.UserRepository;
import com.example.biblioteisproject.menu.MenuWindow;
import com.example.biblioteisproject.R;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private TextView tvErrorMessage;
    private Button btnLogin;
    private boolean loginSuccess = false;
    MainActivityVM vm;
    private CredentialsManager credentialsManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        vm = new ViewModelProvider(this).get(MainActivityVM.class);
        vm.user.observe(this, (User user) -> {
            Intent intent = new Intent(this, MenuWindow.class);
            UserProvider.setInstance(user);
            startActivity(intent);
        });

        if (credentialsManager.hasSavedCredentials()) {
            String email = credentialsManager.getSavedEmail();
            String passwordHash = credentialsManager.getSavedPasswordHash();
            loginWithSavedCredentials(email, passwordHash);
        } else {
            btnLogin.setOnClickListener(view -> {
                if (validarCredenciales()) {
                    Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }


    private boolean validarCredenciales() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        UserRepository userRepository = new UserRepository();
        userRepository.getUsers(new BookRepository.ApiCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> result) {
                for (User user : result) {
                    if (user.getEmail().equals(email) && user.getPasswordHash().equals(password)) {
                        loginSuccess = true;
                        vm.user.postValue(user);
                        credentialsManager.saveCredentials(email, password);
                        break;
                    }
                }
                if (!loginSuccess) {
                    tvErrorMessage.setVisibility(View.VISIBLE);
                }

            }
            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(MainActivity.this, "Fallo conexion a la API", Toast.LENGTH_LONG).show();
            }
        });

        return loginSuccess;
    }

    private void loginWithSavedCredentials(String email, String passwordHash) {
        UserRepository userRepository = new UserRepository();
        userRepository.getUsers(new BookRepository.ApiCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> result) {
                for (User user : result) {
                    if (user.getEmail().equals(email) && user.getPasswordHash().equals(passwordHash)) {
                        loginSuccess = true;
                        vm.user.postValue(user);
                        break;
                    }
                }
                if (!loginSuccess) {
                    tvErrorMessage.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(MainActivity.this, "Fallo conexion a la API", Toast.LENGTH_LONG).show();
            }
        });
    }




    private void initializeViews() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        tvErrorMessage = findViewById(R.id.tvErrorMessage);
        btnLogin = findViewById(R.id.btnLogin);
    }
}