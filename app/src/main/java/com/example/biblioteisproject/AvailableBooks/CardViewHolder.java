package com.example.biblioteisproject.AvailableBooks;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.biblioteisproject.R;

public class CardViewHolder extends RecyclerView.ViewHolder {
    ImageView imagen;
    TextView tvTitulo, tvAutor, tvFechaPubli;
    Button btnDetalles;


    public CardViewHolder(View view) {
        super(view);
        imagen = view.findViewById(R.id.imagen);
        tvTitulo = view.findViewById(R.id.tvTitulo);
        tvAutor = view.findViewById(R.id.tvAutor);
        tvFechaPubli = view.findViewById(R.id.tvFechaPubli);
        btnDetalles = view.findViewById(R.id.btnDetalles);

    }

    public Button getBtnDetalles() {
        return btnDetalles;
    }

    public void setBtnDetalles(Button btnDetalles) {
        this.btnDetalles = btnDetalles;
    }

    public ImageView getImagen() {
        return imagen;
    }

    public void setImagen(ImageView imagen) {
        this.imagen = imagen;
    }

    public TextView getTvAutor() {
        return tvAutor;
    }

    public void setTvAutor(TextView tvAutor) {
        this.tvAutor = tvAutor;
    }

    public TextView getTvFechaPubli() {
        return tvFechaPubli;
    }

    public void setTvFechaPubli(TextView tvFechaPubli) {
        this.tvFechaPubli = tvFechaPubli;
    }

    public TextView getTvTitulo() {
        return tvTitulo;
    }

    public void setTvTitulo(TextView tvTitulo) {
        this.tvTitulo = tvTitulo;
    }
}
