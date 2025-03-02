package com.example.biblioteisproject.details;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.biblioteisproject.API.models.Book;
import com.example.biblioteisproject.API.models.BookLending;
import com.example.biblioteisproject.API.models.User;
import com.example.biblioteisproject.API.repository.BookLendingRepository;
import com.example.biblioteisproject.API.repository.BookRepository;
import com.example.biblioteisproject.API.repository.ImageRepository;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;


public class BookDetailVM extends ViewModel {
    MutableLiveData<List<BookLending>> MDlendings;
    BookLendingRepository bookLendingRepository = new BookLendingRepository();
    MutableLiveData<Book> book;
    BookRepository bookRepository = new BookRepository();

    private MutableLiveData<Bitmap> bookImage = new MutableLiveData<>();


    public BookDetailVM() {
        MDlendings = new MutableLiveData<>();
        MDlendings.setValue(new ArrayList<>());
        book = new MutableLiveData<>();
        fetchLendings();
    }

    public LiveData<Bitmap> getBookImage() {
        return bookImage;
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

                if (result.getBookPicture() != null && !result.getBookPicture().isEmpty()) {
                    cargarImagenLibro(result.getBookPicture());
                }
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

    public void cargarImagenLibro(String nombreImagen) {
        ImageRepository imageRepository = new ImageRepository();
        imageRepository.getImage(nombreImagen, new BookRepository.ApiCallback<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody result) {
                if (result != null) {
                    try {
                        byte[] bytes = result.bytes();
                        final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        bookImage.postValue(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }
}



