package com.landeru.mitrekking;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SelectorRutas extends AppCompatActivity {

    private Button verMapa;
    private TextView error;
    GestorBD baseD = new GestorBD(this);
    private Spinner spinner;
    private int rutaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector_rutas);
/*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        verMapa = (Button) findViewById(R.id.button3);
        error=(TextView)findViewById(R.id.textView);

        verMapa.setVisibility(View.INVISIBLE);

        spinner = (Spinner) findViewById(R.id.spinner);

        spinner.setVisibility(View.INVISIBLE);


        baseD.abrirBD();

        if (baseD.cantidadRuta() == 0){

            error.setText("No hay ninguna ruta por el momento");
        }else{

            // Ocultamos el textView
            error.setVisibility(View.INVISIBLE);


            List<Ruta> listaRuta = new ArrayList<>();

            for (int i = 0; i < baseD.obtenerRutas().size(); i++){

                listaRuta.add(baseD.obtenerRutas().get(1));

                System.out.print(baseD.obtenerRutas().get(1));


            }// end for


            // Creamos el adaptador

            LazyAdapter adaptador = new LazyAdapter(this, listaRuta);

            spinner.setVisibility(View.VISIBLE);
            spinner.setAdapter(adaptador);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    // Nos quedamos con el número seleccionado.

                    rutaSeleccionada = position;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });


            // Mostramos el botón para ir al mapa

            verMapa.setVisibility(View.VISIBLE);

            findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    startActivity(new Intent(SelectorRutas.this, MapsActivity.class));

                }

            });


        }


    }// end on create

}// end class
