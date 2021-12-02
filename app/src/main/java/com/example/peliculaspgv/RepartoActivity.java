package com.example.peliculaspgv;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.peliculaspgv.Actor;
import com.example.peliculaspgv.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RepartoActivity extends AppCompatActivity {
    private ArrayList<Actor> listaActores = new ArrayList<Actor>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reparto);

        listView = (ListView) findViewById(R.id.listCast);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String titulo = intent.getStringExtra("titulo");

        setTitle("Actores de "+titulo);

        new ObtenerActoresAsync().execute(endPointCastPrimera+id+endPointCastSegunda);


    }
    public static final String endPointCastPrimera = "https://api.themoviedb.org/3/movie/";
    public static final String endPointCastSegunda ="/credits?api_key=6c5bbf4c9700e48c4189d6d418b5ae8c&language=es";
    public static final String CAST_BASE_URL="https://image.tmdb.org/t/p/w185";
    class ObtenerActoresAsync extends AsyncTask<String, Integer, String> {
        ProgressDialog progreso;

        protected void onPreExecute (){
            super.onPreExecute();

            // Mostrar progress bar.
            progreso = new ProgressDialog(RepartoActivity.this);
            progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progreso.setMessage("Obteniendo actores ...");
            progreso.setCancelable(false);
            progreso.setMax(100);
            progreso.setProgress(0);
            progreso.show();
        }

        protected String doInBackground(String... params) {
            StringBuilder result = new StringBuilder();

            try{
                URL urlObj = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;

                while ((line = reader.readLine()) != null) result.append(line);

                Log.d("test", "respuesta: " + result.toString());

            } catch (Exception e) {
                Log.d("test", "error2: " + e.toString());
            }

            return result.toString();
        }

        protected void onProgressUpdate(Integer...a){
            super.onProgressUpdate(a);
        }

        protected void onPostExecute(String result) {
            JSONObject resp = null;
            JSONArray actores = null;

            try {
                resp = new JSONObject(result);
                actores = resp.getJSONArray("cast");

                System.out.println("1"+actores.toString());

                for (int i=0; i < actores.length(); i++) {
                    JSONObject actor = actores.getJSONObject(i);
                    System.out.println("2"+actor.getString("original_name"));
                    listaActores.add( new Actor(
                            actor.getString("original_name"),
                            actor.getString("profile_path"),
                            actor.getString("character") ));
                }
               // System.out.println(listaActores.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }



            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            progreso.dismiss();
            AdaptadorActores adaptador=new AdaptadorActores(getApplicationContext(),listaActores);
            listView.setAdapter(adaptador);

        }
    }
    class AdaptadorActores extends BaseAdapter {
        Context context;
        ArrayList<Actor> arrayList;

        public AdaptadorActores(Context context, ArrayList<Actor> arrayList) {
            this.context = context;
            this.arrayList = arrayList;
        }

        public int getCount() {
            return arrayList.size();
        }

        public Actor getItem(int position) {
            return arrayList.get(position);
        }

        public long getItemId(int i) {
            return i;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView ==  null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.lista_actores, parent, false);
            }

            // Nombre del personaje
            TextView fecha = (TextView) convertView.findViewById(R.id.tvPersonaje);
            fecha.setText(arrayList.get(position).getCharacter());

            // Nombre del actor
            TextView descripcion = (TextView) convertView.findViewById(R.id.tvNombre);
            descripcion.setText(arrayList.get(position).getOriginal_name());

            // Imagen.
            ImageView imagen = (ImageView) convertView.findViewById(R.id.actor_image);
            Picasso.get().load(CAST_BASE_URL + arrayList.get(position).getProfile_path()).into(imagen);
            imagen.setScaleType(ImageView.ScaleType.FIT_XY);

            return convertView;
        }
    }



}
