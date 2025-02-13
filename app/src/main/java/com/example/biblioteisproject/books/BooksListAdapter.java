package com.example.biblioteisproject.books;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.biblioteisproject.API.models.Book;
import com.example.biblioteisproject.R;
import com.example.biblioteisproject.details.BookDetailActivity;

import java.util.List;

public class BooksListAdapter extends RecyclerView.Adapter {

    List<Book> books;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_available_books, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Book book = books.get(position);
        CardViewHolder cardViewHolder = (CardViewHolder) holder;
        cardViewHolder.getTvTitulo().setText(book.getTitle());
        cardViewHolder.getTvAutor().setText(book.getAuthor());
        cardViewHolder.getTvFechaPubli().setText(book.getPublishedDate());
        cardViewHolder.getBtnDetalles().setOnClickListener((view) -> {
           Intent intent = new Intent(view.getContext(), BookDetailActivity.class);
            intent.putExtra("book", books.get(position));
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public BooksListAdapter(List<Book> books) {
        this.books = books;
    }
}
