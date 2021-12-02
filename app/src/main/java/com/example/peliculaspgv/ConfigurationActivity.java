package com.example.peliculaspgv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConfigurationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        // Storing data into SharedPreferences
        SharedPreferences preferencias;

        EditText etApiKey = (EditText) findViewById(R.id.etApiKey);
        Button btGuardar = (Button) findViewById(R.id.btGuardar);

        // Preferencias.
        preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);

        // Mostrar en la pantalla.
        etApiKey.setText( preferencias.getString("api_key","") );


        btGuardar.setOnClickListener(e->{
            // Guardar preferencias y salir.
            SharedPreferences.Editor editor = preferencias.edit();
            editor.putString("api_key", etApiKey.getText().toString());
            editor.commit();

            Toast t = Toast.makeText(getApplicationContext(), "Configuración guardada correctamente.", Toast.LENGTH_SHORT);
            t.show();

            finish();
        });

    }
}