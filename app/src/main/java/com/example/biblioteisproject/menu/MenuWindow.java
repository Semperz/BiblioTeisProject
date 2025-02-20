package com.example.biblioteisproject.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.biblioteisproject.API.models.User;
import com.example.biblioteisproject.API.repository.UserProvider;
import com.example.biblioteisproject.AvailableBooks.AvailableBooks;
import com.example.biblioteisproject.AvailableBooks.AvailableBooksMV;
import com.example.biblioteisproject.AvailableBooks.BooksListAdapter;
import com.example.biblioteisproject.Profile.ProfileActivity;
import com.example.biblioteisproject.R;
import com.example.biblioteisproject.details.BookDetailActivity;

public class MenuWindow extends AppCompatActivity {

    RecyclerView recommendedBooks;
    RecyclerView newBooks;
    private MenuWindowMV menuWindowMV;
    Toolbar TbMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_window);
        User user = UserProvider.getInstance();

        TbMenu = findViewById(R.id.TbMainMenu);

        menuWindowMV = new ViewModelProvider(this).get(MenuWindowMV.class);


        // Configura los RecyclerViews
        recommendedBooks = findViewById(R.id.recommendedBooks);
        newBooks = findViewById(R.id.newBooks);

        menuWindowMV.books.observe(this, books -> {
            recommendedBooks.setAdapter(new BooksListAdapter(books));
        });
        recommendedBooks.setLayoutManager(new LinearLayoutManager(this));


        menuWindowMV.books.observe(this, books -> {
            newBooks.setAdapter(new BooksListAdapter(books));
        });
        newBooks.setLayoutManager(new LinearLayoutManager(this));


        setSupportActionBar(TbMenu);

        addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_principal, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {

                int id= menuItem.getItemId();

                if (id == R.id.LibrosDisponibles){
                    Intent intent = new Intent(MenuWindow.this, AvailableBooks.class);
                    startActivity(intent);
                    return true;
                }

                if (id == R.id.Perfil){
                    Intent intent = new Intent(MenuWindow.this, ProfileActivity.class);
                    startActivity(intent);
                    return true;
                }

                return false;
            }
        });

    }

}
