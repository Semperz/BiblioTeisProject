package com.example.biblioteisproject.login;

import android.content.Intent;
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
import com.example.biblioteisproject.API.repository.UserRepository;
import com.example.biblioteisproject.menu.MenuWindow;
import com.example.biblioteisproject.R;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private TextView tvErrorMessage;
    private Button btnLogin;
    private boolean loginSuccess = false;
    MainActivityVM vm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        vm = new ViewModelProvider(this).get(MainActivityVM.class);
        vm.user.observe(this, (User user) -> {
            Intent intent = new Intent(this, MenuWindow.class);
            intent.putExtra("user", user);
            startActivity(intent);
        });
        btnLogin.setOnClickListener(view -> {
            if (validarCredenciales()) {
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
            }
        });





      /*  MainActivityVM vm = new ViewModelProvider(this).get(MainActivityVM.class);

        vm.book.observe(this, (Book book) -> {
            ((TextView)findViewById(R.id.name)).setText(book.getTitle());
            ((TextView)findViewById(R.id.Autor)).setText(book.getAuthor());
        });

        BookRepository br = new BookRepository();

        br.getBookById(1, new BookRepository.ApiCallback<Book>() {
            @Override
            public void onSuccess(Book result) {
                vm.book.setValue(result);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });*/

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

    private void initializeViews() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        tvErrorMessage = findViewById(R.id.tvErrorMessage);
        btnLogin = findViewById(R.id.btnLogin);
    }
}