package com.landeru.mitrekking;

import java.util.Date;

/**
 * Created by lander on 17/3/16.
 */
public class Coordenada {



    // Atributos
    private float longitud = 0;
    private  float latitud = 0;
    private String timestap = "";

    // Getters
    public float getLongitud() {
        return longitud;
    } // end getLongitud

    public float getLatitud() {
        return latitud;
    } // end getLatitud

    public String getTimestap() {
        return timestap;
    } // end getTimestap

    // Setters
    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }// end setLongitud

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }//end setLatitud

    public void setTimestap(String timestap) {
        this.timestap = timestap;
    }//end setTimestap

} // class
