package com.example.biblioteisproject.Profile;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.biblioteisproject.API.models.Book;
import com.example.biblioteisproject.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BookProfileAdapter extends RecyclerView.Adapter<BookProfileAdapter.BookViewHolder> {

    private List<Book> books;

    public BookProfileAdapter(List<Book> books) {
        this.books = books;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflar el layout item_book_profile.xml
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_available_books, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Book book = books.get(position);

        // Establecer el título, autor y fecha de publicación
        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText("Autor: " + book.getAuthor());
        holder.tvPublishedDate.setText("Publicado en: " + book.getPublishedDate());

        // Verificar si el libro está prestado por más de 15 días
        String lendDate = book.getBookLendings().get(0).getLendDate(); // Usamos la primera fecha de préstamo
        if (isOverdue(lendDate)) {
            holder.tvTitle.setTextColor(Color.RED); // Cambiar color a rojo si el libro está vencido
        } else {
            holder.tvTitle.setTextColor(Color.BLACK); // Color normal si no está vencido
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    /*// Método para calcular si el libro está prestado por más de 15 días
    private boolean isOverdue(String lendDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date lendDateObj = sdf.parse(lendDate);
            long difference = new Date().getTime() - lendDateObj.getTime();
            long days = difference / (1000 * 60 * 60 * 24);
            return days > 15; // Si han pasado más de 15 días, el libro está vencido
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }*/


    private boolean isOverdue(String lendDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Modificar el formato para incluir horas y minutos
        try {
            Date lendDateObj = sdf.parse(lendDate); // Convertir la fecha de préstamo a un objeto Date
            long difference = new Date().getTime() - lendDateObj.getTime(); // Diferencia en milisegundos
            long minutes = difference / (1000 * 60); // Convertir la diferencia de milisegundos a minutos
            return minutes > 1; // Si ha pasado más de 1 minuto, el libro está vencido
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // ViewHolder para el libro
    public static class BookViewHolder extends RecyclerView.ViewHolder {

        ImageView ivBookImage; // Imagen del libro
        TextView tvTitle;      // Título del libro
        TextView tvAuthor;     // Autor del libro
        TextView tvPublishedDate; // Fecha de publicación

        public BookViewHolder(View itemView) {
            super(itemView);
            ivBookImage = itemView.findViewById(R.id.imagen);
            tvTitle = itemView.findViewById(R.id.tvTitulo);
            tvAuthor = itemView.findViewById(R.id.tvAutor);
            tvPublishedDate = itemView.findViewById(R.id.tvFechaPubli);
        }
    }
}
