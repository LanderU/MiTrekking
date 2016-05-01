package com.landeru.mitrekking;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lander on 1/5/16.
 */
public class LazyAdapter extends BaseAdapter {



    private LayoutInflater inflater = null;
    private List<Ruta> listaRutas = null;


    public LazyAdapter(Activity activity, List<Ruta> listaRutas){

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listaRutas = listaRutas;

    }// end LazyAdapter



    @Override
    public int getCount() {
        return listaRutas.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Creación de la fila

        View vista = inflater.inflate(R.layout.estilofila,null);

        // Referencia al control

        TextView timestamp = (TextView) vista.findViewById(R.id.textView2);

        // Rellenamos los datos

        Ruta rutas = listaRutas.get(position);

        timestamp.setText(rutas.getTimestamp());


        return vista;
    }
}// end class
