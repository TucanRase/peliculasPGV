package com.example.peliculaspgv;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetalleActivity extends AppCompatActivity {

    String titulo,imagen,sinopsis,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        // Poner el boton de vuelta atrás.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Leer el parametro de la pelicula.
        Intent intent = getIntent();
        this.titulo =  intent.getStringExtra("titulo");
        this.imagen =  intent.getStringExtra("imagen");
        this.sinopsis =  intent.getStringExtra("sinopsis");
        this.id = intent.getStringExtra("id");

        System.out.println(titulo+imagen+sinopsis+id);

        Log.d("test", "Creditos: Leyendo id:" + id);
        Log.d("test", "Creditos: Leyendo titulo:" + titulo);

        setTitle(titulo);

        String MOVIE_BASE_URL="https://image.tmdb.org/t/p/w185";

        ImageView iv = (ImageView) findViewById(R.id.ivPortada);

        Picasso.get().load(MOVIE_BASE_URL + imagen).into(iv);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);

        TextView txtTitulo=(TextView) findViewById(R.id.txtTitulo);
        txtTitulo.setText(titulo.toUpperCase());

        TextView txtSinopsis=(TextView) findViewById(R.id.txtSinopsis);
        txtSinopsis.setText(sinopsis);

        Button btn = (Button)findViewById(R.id.btnCreditos);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RepartoActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("titulo",titulo);
                startActivity(intent);

            }
        });
    }
}