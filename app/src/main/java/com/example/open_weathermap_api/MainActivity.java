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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    TextView cityName;
    Button search;
    TextView show;
    String APIKEYweather = "fdbcfef5d14487a138b55af24d1cf470";
    String valorFinal="";

     
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

        ExecutorService service = Executors.newSingleThreadExecutor();


        //implementar accion del boton
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Paso 1: PreExecute
                // llamar a la clase para obtener los valores
                   IniciarTarea();

            }
        });
        //fin de implementacion


    }

    private void IniciarTarea() {

        //Paso 2: This is doing on background()
        new Thread(new Runnable() {
            @Override
            public void run() {

                //Definir URL al cual llamar
                String url = "https://api.openweathermap.org/data/2.5/find?q="+cityName.getText()+"&appid="+APIKEYweather;

                //Do everything here
                valorFinal=getSiteString(url);

                //Paso3: this is postExecute
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //ASginar valores
                        show.setText(valorFinal);

                    }
                });
                //Finish paso 3

            }
        }).start();

    }

    private String getSiteString(String site) {

        // Paso 5:
        // Codigo que hara con los datos de la URL
        StringBuilder result = new StringBuilder();

        //inicio try catch
        try {

            //conexion
            ////convertir string en una URL
            URL url = new URL(site);
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
}