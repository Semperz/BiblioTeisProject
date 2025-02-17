package com.example.biblioteisproject.menu;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.biblioteisproject.API.models.Book;
import com.example.biblioteisproject.API.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MenuWindowMV extends ViewModel {

    MutableLiveData<Book> book;
    MutableLiveData<List<Book>> books;

    public MenuWindowMV(){
        book = new MutableLiveData<>();
        books = new MutableLiveData<>();
        books.setValue(new ArrayList<>());
        getRecomendedBooks();
    }



    public void addBook(Book book){
        List<Book> lista = books.getValue();
        assert lista != null;
        lista.add(book);
        books.setValue(lista);
    }


    public void getRecomendedBooks(){
        BookRepository bookRepository = new BookRepository();
        bookRepository.getBooks(new BookRepository.ApiCallback<List<Book>>() {
            @Override
            public void onSuccess(List<Book> result) {
                for (int i = 0; i < 1; i++){
                    addBook(result.get(new Random().nextInt(result.size())));
                }
            }
            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

}
