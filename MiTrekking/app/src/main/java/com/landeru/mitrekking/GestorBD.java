package com.landeru.mitrekking;

/**
 * Created by lander on 17/3/16.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class GestorBD {

    // Atributos

    private SQLiteDatabase bd = null;
    private BDHelper helper = null;

    // Creamos la base de datos

    private class BDHelper extends SQLiteOpenHelper{

        private String tablaRuta = "CREATE TABLE ruta (idRuta INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,fecha text);";
        private String tablaPunto = "CREATE TABLE  punto (idPunto INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, latitud REAL, longitud REAL,timestap text,idRuta INTEGER, FOREIGN KEY (idRuta) REFERENCES ruta (idRuta));";

        //Constructor
        public BDHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }   //end public

        //  Crear la BD

        @Override
        public void onCreate(SQLiteDatabase db){

            if(!db.isReadOnly()){
                // Activamos las claves ajenas
                db.execSQL("PRAGMA foreign_keys=ON;");
                // Creamos la tabla Ruta
                db.execSQL(tablaRuta);
                // Creamos la tabla Punto
                db.execSQL(tablaPunto);

            }// end if

        }// end onCreate

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

            if(!db.isReadOnly()){

                //Borramos las tablas
                db.execSQL("DROP TABLE IF EXISTS punto;");
                db.execSQL("DROP TABLE IF EXISTS ruta;");

                // Activamos las claves foraneas
                db.execSQL("PRAGMA foreign_keys=ON;");
                // Creamos las tablas??
                db.execSQL(tablaRuta);
                db.execSQL(tablaPunto);

            }// end if


        }// end onUpgrade

    } // end BDHelper

    // Llamamos al constructor de la base de datos

    public GestorBD(Context context){

        helper = new BDHelper(context,"MiTrekking.sqlite", null,1);

    }// end constructor

    // Abrir la conexión a la BD

    public void abrirBD(){

        if(bd == null){

            bd = helper.getWritableDatabase();

        } // end if

    }// end abrirBD

    // Cerrar la BD

    public void cerrarBD(){

        if(bd != null) {

            bd.close();

        } // end if

    } // end cerrarBD

    // Guardar coordenadas

    public void guardarCoordenadas(Coordenada coordenada){

        if(bd.isOpen() && coordenada != null){

            ContentValues dato = new ContentValues();

            dato.put("latitud",coordenada.getLatitud());
            dato.put("longitud",coordenada.getLongitud());
            dato.put("timestap",coordenada.getTimestap());

            bd.insert("punto",null,dato);


        }// end if



    } // finalizar guardar en la BD





}//end class
