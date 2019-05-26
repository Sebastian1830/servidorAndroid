package com.example.prueba2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String ServerURL = "http://localhost:8080/frutas/Registrar.php" ;
    EditText nombre, color;
    Button button;
    String TempNombre, TempColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombre = (EditText)findViewById(R.id.txtNombre);
        color = (EditText)findViewById(R.id.txtColor);
        button = (Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObtenerDatos();
                InsertaDatos(TempNombre, TempColor);
            }
        });
    }

    public void ObtenerDatos(){
        TempNombre = nombre.getText().toString();
        TempColor = color.getText().toString();
    }

    public void InsertaDatos(final String nombre, final String color) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String NombreHolder = nombre;
                String ColorHolder = color;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("nombre", NombreHolder));
                nameValuePairs.add(new BasicNameValuePair("color", ColorHolder));
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(ServerURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();
                } catch (ClientProtocolException e) {
                    //Toast.makeText(MainActivity.this, "Error1", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    //Log.d("Error",e.getMessage());
                    //Toast.makeText(MainActivity.this, "Error2", Toast.LENGTH_LONG).show();
                }
                return "Datos insertados con éxito";
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Toast.makeText(MainActivity.this, "Datos enviados con éxito", Toast.LENGTH_LONG).show();
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(nombre, color);
    }
}
