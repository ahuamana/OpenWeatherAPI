package com.example.open_weathermap_api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    TextView cityName;
    Button search;
    TextView show;
    String APIKEYweather = "fdbcfef5d14487a138b55af24d1cf470";

    final String[] temp = {""};

    // Paso 3:
    // crear clase para obtener datos del clima
    private class getWeather extends AsyncTask<String, Void, String>
    {


        @Override
        protected String doInBackground(String... urls) {

            // Paso 5:
            // Codigo que hara con los datos de la URL
            StringBuilder result = new StringBuilder();

            //inicio try catch
            try {

                //conexion
                ////convertir string en una URL
                URL url = new URL(urls[0]);
                ////obtener los datos del HTML
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                //lectura de datos
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";

                //Leer linea por linea con bufferreader
                while((line = reader.readLine())!= null)
                {
                    result.append(line).append("\n");
                }

                return result.toString();

            }catch (Exception e)
            {
                e.printStackTrace();
                return null;

            }
            //fin try catch
        }

        //Paso 6:
        //luego de la ejecucion se pasa a la manipulacion de los datos
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //asignar resultado al campo en el layout defrente sin setear
            //show.setText(result);


            try {
                //Setear en JSON
                JSONObject root = new JSONObject(result);
                //Obtener los datos del valor "main" en json
                //JSONObject listaCiudades = root.getJSONObject("list");
                //JSONObject jsonObjectNuevo = jsonObjectSTART.getJSONObject("main");


                String datanew = root.getString("list");


                //Log.e("Data", weatherinfo);
                //Log.e("Data", datanew);

                //asignar valores
                show.setText(datanew);

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Error", "Error");
            }

        }
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Full screen android layout
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //codigo aquí
        cityName = findViewById(R.id.edtCiudad);
        search = findViewById(R.id.btnBuscar);
        show = findViewById(R.id.show);

        //implementar accion del boton
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Definir URL al cual llamar
                String url = "https://api.openweathermap.org/data/2.5/find?q="+cityName.getText()+"&appid="+APIKEYweather;
                // Paso 4:
                // llamar a la clase para obtener los valores
                getWeather task = new getWeather();
                //Ejecutar la tarea y enviar la URL
                try {
                    temp [0]=task.execute(url).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(temp[0] == null)
                {

                    show.setText("No se puede encontrar");
                }

            }
        });
        //fin de implementacion


    }
}