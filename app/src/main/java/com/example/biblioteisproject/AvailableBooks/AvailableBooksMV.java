package com.example.biblioteisproject.AvailableBooks;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.biblioteisproject.API.models.Book;
import com.example.biblioteisproject.API.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

public class AvailableBooksMV extends ViewModel {
    MutableLiveData<Book> book;
    MutableLiveData<List<Book>> books;

    public AvailableBooksMV(){
        book = new MutableLiveData<>();
        books = new MutableLiveData<>();
        books.setValue(new ArrayList<>());
        getAllAvailableBooks();
    }



    public void addBook(Book book){
        List<Book> lista = books.getValue();
        assert lista != null;
        lista.add(book);
        books.setValue(lista);
    }


    public void getAllAvailableBooks(){
        BookRepository bookRepository = new BookRepository();
        bookRepository.getBooks(new BookRepository.ApiCallback<List<Book>>() {
            @Override
            public void onSuccess(List<Book> result) {
                for (Book book : result){
                    if (book.isAvailable()){
                        addBook(book);
                    }
                }
            }
            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void getFilteredBooks(String title, String author){
        BookRepository bookRepository = new BookRepository();
        bookRepository.getBooks(new BookRepository.ApiCallback<List<Book>>() {
            @Override
            public void onSuccess(List<Book> result) {
                List<Book> filteredBooks = new ArrayList<>();
                for (Book book : result){
                    boolean matchesTitle = title.isEmpty() || book.getTitle().toLowerCase().contains(title.toLowerCase());
                    boolean matchesAuthor = author.isEmpty() || book.getAuthor().toLowerCase().contains(author.toLowerCase());
                    if (matchesTitle && matchesAuthor){
                        filteredBooks.add(book);
                    }
                }
                books.setValue(filteredBooks);
            }
            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(null, "Error al buscar libros", Toast.LENGTH_LONG).show();
            }
        });
    }
}
