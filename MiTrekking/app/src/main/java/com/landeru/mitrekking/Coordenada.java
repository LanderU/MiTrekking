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

    // Setters
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }// end setLongitud

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }//end setLatitud

    public void setTimestap(String timestamp) {
        this.timestamp = timestamp;
    }//end setTimestap

} // class
