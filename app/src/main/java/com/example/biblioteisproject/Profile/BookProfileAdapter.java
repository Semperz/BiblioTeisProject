package com.example.biblioteisproject.Profile;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.biblioteisproject.API.models.Book;
import com.example.biblioteisproject.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BookProfileAdapter extends RecyclerView.Adapter<BookProfileAdapter.BookViewHolder> {

    private List<Book> books;

    public BookProfileAdapter(List<Book> books) {
        this.books = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el layout correcto para cada ítem
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_available_books, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);

        // Establecer el título, autor y fecha de publicación
        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText("Autor: " + book.getAuthor());
        holder.tvPublishedDate.setText("Publicado en: " + book.getPublishedDate());

        // Verificar si el libro tiene préstamos antes de acceder a ellos
        if (book.getBookLendings() != null && !book.getBookLendings().isEmpty()) {
            String lendDate = book.getBookLendings().get(0).getLendDate();// Primera fecha de préstamo
            if (isOverdue(lendDate)) {
                holder.tvTitle.setTextColor(Color.RED); // Marcar en rojo si el préstamo está vencido
            } else {
                holder.tvTitle.setTextColor(Color.BLACK); // Mantener color normal si no está vencido
            }
        } else {
            holder.tvTitle.setTextColor(Color.BLACK); // Si no tiene préstamos, dejar el color normal
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    /**
     * Método para calcular si un libro está prestado por más de X minutos
     * @param lendDate Fecha de préstamo en formato "yyyy-MM-dd HH:mm:ss"
     * @return true si han pasado más de X minutos, false en caso contrario
     */
    private boolean isOverdue(String lendDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            Date lendDateObj = sdf.parse(lendDate); // Convertir la fecha a Date
            long difference = new Date().getTime() - lendDateObj.getTime(); // Diferencia en milisegundos
            long minutes = difference / (1000 * 60); // Convertir a minutos
            return minutes > 2; // Si han pasado más de 15 dias, el libro está vencido 21600
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ViewHolder para cada libro
    public static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBookImage; // Imagen del libro
        TextView tvTitle;      // Título del libro
        TextView tvAuthor;     // Autor del libro
        TextView tvPublishedDate; // Fecha de publicación

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBookImage = itemView.findViewById(R.id.imagen);
            tvTitle = itemView.findViewById(R.id.tvTitulo);
            tvAuthor = itemView.findViewById(R.id.tvAutor);
            tvPublishedDate = itemView.findViewById(R.id.tvFechaPubli);
        }
    }
}
