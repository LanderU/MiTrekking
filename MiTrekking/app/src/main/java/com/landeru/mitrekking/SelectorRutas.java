package com.landeru.mitrekking;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SelectorRutas extends AppCompatActivity {

    private Button verMapa;
    private TextView error;
    GestorBD baseD = new GestorBD(this);

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

        baseD.abrirBD();

        if (baseD.cantidadRuta() == 0){

            error.setText("No hay ninguna ruta por el momento");
        }else{
            error.setVisibility(View.INVISIBLE);
            verMapa.setVisibility(View.VISIBLE);

            findViewById(R.id.button3).setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v){

                    startActivity(new Intent(SelectorRutas.this, MapsActivity.class));

                }

            });

        }


    }// end on create

}// end class
