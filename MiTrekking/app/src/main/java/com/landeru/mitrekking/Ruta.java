package com.landeru.mitrekking;

/**
 * Created by lander on 19/4/16.
 */
public class Ruta {
    // constructor
    public Ruta(int i, String string) {
    }// end contructor

    // Atributos

    private int id_ruta;
    private String timestamp;

    // getters
    public int getId_ruta() {
        return id_ruta;
    }

    public String getTimestamp() {
        return timestamp;
    }

    // setters
    public void setId_ruta(int id_ruta) {
        this.id_ruta = id_ruta;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


}// end class
