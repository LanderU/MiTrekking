package com.landeru.mitrekking;

import java.util.Date;

/**
 * Created by lander on 17/3/16.
 */
public class Coordenada {



    // Atributos
    private double longitud = 0;
    private  double latitud = 0;
    private String timestamp = "";
    private int id_ruta = 0;


    public Coordenada(double longitud, double latitud, String timestamp) {
        this.longitud = longitud;
        this.latitud = latitud;
        this.timestamp = timestamp;
    }//


    // Getters
    public double getLongitud() {
        return longitud;
    } // end getLongitud

    public double getLatitud() {
        return latitud;
    } // end getLatitud

    public String getTimestap() {
        return timestamp;
    } // end getTimestap

    public int getId_ruta() {return id_ruta;} // end getid_ruta

    // Setters
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }// end setLongitud

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }//end setLatitud

    public void setTimestap(String timestamp) {this.timestamp = timestamp;}//end setTimestap

    public void setId_ruta(int id_ruta) {this.id_ruta = id_ruta;}// end setId_ruta



} // class
