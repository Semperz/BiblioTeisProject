package com.example.biblioteisproject.details;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.biblioteisproject.API.models.BookLending;
import com.example.biblioteisproject.API.models.User;
import com.example.biblioteisproject.API.repository.BookLendingRepository;
import com.example.biblioteisproject.API.repository.BookRepository;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;

public class BookDetailVM extends ViewModel {
    MutableLiveData<List<BookLending>> MDlendings;
    BookLendingRepository bookLendingRepository = new BookLendingRepository();


    public BookDetailVM() {
        MDlendings = new MutableLiveData<>();
        MDlendings.setValue(new ArrayList<>());
        fetchLendings();
    }


    public LiveData<List<BookLending>> getLendings() {
        return MDlendings;
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


    public boolean isLentTo(User user, int bookId) {
        List<BookLending> lista = MDlendings.getValue();
        if (lista != null) {
            for (BookLending lending : lista) {
                if (lending.getUserId() == user.getId() && lending.getBookId() == bookId) {
                    return true;
                }
            }
        }
        return false;
    }

}

