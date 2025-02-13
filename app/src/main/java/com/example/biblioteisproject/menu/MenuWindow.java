package com.example.biblioteisproject.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.biblioteisproject.API.models.User;
import com.example.biblioteisproject.BookDetail.BookDetail;
import com.example.biblioteisproject.BookList.BookList;
import com.example.biblioteisproject.R;

public class MenuWindow extends AppCompatActivity {

    RecyclerView recommendedBooks;
    RecyclerView newBooks;
    Button buttonL, buttonP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_window);

        User user = (User) getIntent().getSerializableExtra("user");

        // Inicializa los botones
        buttonL = findViewById(R.id.buttonLibros);
        buttonP = findViewById(R.id.buttonPerfil);


        buttonL.setOnClickListener(view -> {
            Intent intent = new Intent(this, BookList.class);
            intent.putExtra("user", user);
            startActivity(intent);
        });

        buttonP.setOnClickListener(view -> {
            Toast.makeText(MenuWindow.this, "Botón 2 presionado", Toast.LENGTH_SHORT).show();

        });


        // Configura los RecyclerViews
        recommendedBooks = findViewById(R.id.recommendedBooks);
        recommendedBooks.setLayoutManager(new LinearLayoutManager(this));
        recommendedBooks.setAdapter(new BookAdapter());

        newBooks = findViewById(R.id.newBooks);
        newBooks.setLayoutManager(new LinearLayoutManager(this));
        newBooks.setAdapter(new BookAdapter());
    }

    class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

        @Override
        public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.fragment_available_books, parent, false);
            return new BookViewHolder(view);
        }


        @Override
        public void onBindViewHolder(BookViewHolder holder, int position) {
            holder.bind();
        }

        @Override
        public int getItemCount() {
            return 4; // Puedes cambiar esto según la cantidad de libros que deseas mostrar
        }

        class BookViewHolder extends RecyclerView.ViewHolder {

            Button btnDetalles;

            public BookViewHolder(View itemView) {
                super(itemView);
                btnDetalles = itemView.findViewById(R.id.btnDetalles);
            }

            public void bind() {
                // Lógica para vincular datos al ViewHolder (por ejemplo, imagenes o texto)
            }

            private View.OnClickListener listener(int position){
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MenuWindow.this, BookDetail.class);
                        intent.putExtra("book", position);
                    }
                };
            }
        }
    }
}
