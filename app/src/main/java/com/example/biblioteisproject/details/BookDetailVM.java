package com.example.biblioteisproject.details;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.biblioteisproject.API.models.Book;
import com.example.biblioteisproject.API.models.BookLending;
import com.example.biblioteisproject.API.models.User;
import com.example.biblioteisproject.API.repository.BookLendingRepository;
import com.example.biblioteisproject.API.repository.BookRepository;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class BookDetailVM extends ViewModel {
    MutableLiveData<List<BookLending>> MDlendings;
    BookLendingRepository bookLendingRepository = new BookLendingRepository();
    MutableLiveData<Book> book;
    BookRepository bookRepository = new BookRepository();

    public BookDetailVM() {
        MDlendings = new MutableLiveData<>();
        MDlendings.setValue(new ArrayList<>());
        book = new MutableLiveData<>();
        fetchLendings();
    }


    public MutableLiveData<List<BookLending>> getLendings() {
        return MDlendings;
    }

    public MutableLiveData<Book> getBook() {
        return book;
    }


    public void fetchLendings() {
        bookLendingRepository.getAllLendings(new BookRepository.ApiCallback<List<BookLending>>() {
            @Override
            public void onSuccess(List<BookLending> result) {
                MDlendings.postValue(result);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void fetchBook(int id) {
        bookRepository.getBookById(id, new BookRepository.ApiCallback<Book>() {
            @Override
            public void onSuccess(Book result) {
                book.postValue(result);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public boolean isLentTo(int userId, int bookId) {
        for (BookLending lending : getLendings().getValue()){
            if (lending.getUserId() == userId && lending.getBookId() == bookId) {
                return true;
            }
        }

        return false;
    }
}



