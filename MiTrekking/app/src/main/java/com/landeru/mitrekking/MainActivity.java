package com.landeru.mitrekking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    private Button bt1;
    private GestorBD bd = new GestorBD(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bt1 = (Button) findViewById(R.id.button);
        // Acción de pulsar el botón 1
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Abrimos la BD

                bd.abrirBD();

                // Guardamos la ruta

                bd.guardarRuta();

                // Cerramos la BD

                bd.cerrarBD();

                // Iniciamos el servicio en otro hilo
                startService(new Intent(MainActivity.this, Servicio.class));


                // Deshabilitamos el botón
                bt1.setEnabled(false);


                //Mostramos un mensaje
                Context context = getApplicationContext();
                CharSequence mensaje = "Capturando su ruta pulse \"DETENER\" para finalizar";
                int duracion = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, mensaje, duracion);
                toast.show();
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this,Servicio.class));
                bt1.setEnabled(true);
                startActivity(new Intent(MainActivity.this, SelectorRutas.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
