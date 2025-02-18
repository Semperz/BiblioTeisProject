package com.example.biblioteisproject.details;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.biblioteisproject.API.models.BookLending;
import com.example.biblioteisproject.API.repository.BookLendingRepository;
import com.example.biblioteisproject.API.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;

public class BookDetailVM extends ViewModel {
    private final MutableLiveData<List<BookLending>> lendings = new MutableLiveData<>(new ArrayList<>());
    private final BookLendingRepository bookLendingRepository = new BookLendingRepository();

    public LiveData<List<BookLending>> getLendings() {
        return lendings;
    }

    public void fetchLendings() {
        bookLendingRepository.getAllLendings(new BookRepository.ApiCallback<List<BookLending>>() {
            @Override
            public void onSuccess(List<BookLending> result) {
                lendings.postValue(result); // 🔹 Usar postValue en hilos secundarios
            }

            @Override
            public void onFailure(Throwable t) {
                // Manejo de errores: podrías registrar el error o mostrar un mensaje
                t.printStackTrace();
            }
        });
    }

    public void addLending(BookLending bookLending) {
        List<BookLending> lista = lendings.getValue();
        if (lista != null) {
            lista.add(bookLending);
            lendings.setValue(lista); // 🔹 Actualiza LiveData
        }
    }

    public boolean isLentTo(int idUser) {
        List<BookLending> lista = lendings.getValue();
        if (lista != null) {
            for (BookLending bookLending : lista) {
                if (bookLending.getUserId() == idUser) {
                    return true;
                }
            }
        }
        return false;
    }
}

