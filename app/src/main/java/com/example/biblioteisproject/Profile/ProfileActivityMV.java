package com.example.biblioteisproject.Profile;

import android.graphics.Color;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.biblioteisproject.API.models.Book;
import com.example.biblioteisproject.API.models.BookLending;
import com.example.biblioteisproject.API.retrofit.ApiClient;
import com.example.biblioteisproject.API.retrofit.ApiService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivityMV extends ViewModel {

    MutableLiveData<List<Book>> books;

    public ProfileActivityMV() {
        books = new MutableLiveData<>();
        books.setValue(new ArrayList<>());
    }

    public MutableLiveData<List<Book>> getBooks() {
        return books;
    }

    // Método para obtener los libros prestados por el usuario
    public void getLendedBooks(int userId) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // Llamamos a la API para obtener los préstamos de libros de este usuario
        Call<List<BookLending>> call = apiService.getLendings();

        call.enqueue(new Callback<List<BookLending>>() {
            @Override
            public void onResponse(Call<List<BookLending>> call, Response<List<BookLending>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<BookLending> lendings = response.body();
                    List<Book> lendedBooks = new ArrayList<>();

                    // Filtramos los préstamos que corresponden al usuario específico
                    for (BookLending lending : lendings) {
                        if (lending.getUserId() == userId) {
                            getBookById(lending.getBookId(), lendedBooks, lending.getLendDate());
                        }
                    }

                    // Filtrar y ordenar por fecha
                    sortBooksByLendDate(lendedBooks);
                    books.setValue(lendedBooks);
                }
            }

            @Override
            public void onFailure(Call<List<BookLending>> call, Throwable t) {
                // Manejo de error
            }
        });
    }

    private void getBookById(int bookId, List<Book> lendedBooks, String lendDate) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // Llamamos a la API para obtener el libro por su ID
        Call<Book> call = apiService.getBook(bookId);

        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Book book = response.body();
                    book.setBookLendings(Collections.singletonList(new BookLending()));
                    book.getBookLendings().get(0).setLendDate(lendDate);

                    lendedBooks.add(book);
                    books.setValue(lendedBooks); // Actualizamos la lista de libros
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                // Manejo de error
            }
        });
    }

    // Método para ordenar los libros por fecha de préstamo
    private void sortBooksByLendDate(List<Book> lendedBooks) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Ordenamos los libros por fecha de préstamo (más reciente primero)
        Collections.sort(lendedBooks, new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                try {
                    Date date1 = sdf.parse(o1.getBookLendings().get(0).getLendDate());
                    Date date2 = sdf.parse(o2.getBookLendings().get(0).getLendDate());
                    return date2.compareTo(date1); // Orden inverso, más reciente primero
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });
    }
}
