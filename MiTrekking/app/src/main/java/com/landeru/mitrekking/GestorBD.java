package com.landeru.mitrekking;

/**
 * Created by lander on 17/3/16.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class GestorBD {

    // Atributos

    private SQLiteDatabase bd = null;
    private BDHelper helper = null;


    private class BDHelper extends SQLiteOpenHelper{

        private String tablaRuta = "CREATE TABLE ruta (idRuta INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, timestamp text);";
        private String tablaPunto = "CREATE TABLE punto (idPunto INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, latitud REAL, longitud REAL,timestamp text,idRuta INTEGER, FOREIGN KEY (idRuta) REFERENCES ruta (idRuta));";

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

    // Guardar ruta NOT FOUND
/*
    public void guardarRuta(){

        if(bd.isOpen()){

            ContentValues nuevaRuta = new ContentValues();

            Calendar cal = new GregorianCalendar();

            Date date = cal.getTime();

            SimpleDateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            String tiempo = fechaHora.format(date);

            nuevaRuta.put("timestamp", tiempo);

            // Hacemos el insert en la BD

            bd.insert("ruta", null, nuevaRuta);


        } // end if

    } //end guardar Ruta

*/

    public void guardarRuta(){

        if (bd.isOpen()) {

            Calendar cal = new GregorianCalendar();

            Date date = cal.getTime();

            SimpleDateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            String tiempo = fechaHora.format(date);

            bd.execSQL("insert into ruta values (null," + tiempo + ")");
        }// end if

    } //end gurardar Ruta


    // Devolver la ruta última

    public int mostrarIdRuta(){
      int idRuta = 0;
      if(bd.isReadOnly()){

            Cursor cursorRuta = bd.rawQuery("SELECT last_insert_rowid();", null);
            cursorRuta.moveToFirst();
            idRuta = cursorRuta.getInt(0);
            cursorRuta.close();

      }//   end if
        return idRuta;

    }// end obtener ruta

    // Método para devolver los datos de las rutas

    public List<Ruta> obtenerRutas(){

        List<Ruta> rt = new ArrayList<>();

        if(bd.isOpen()){

            String tabla = "ruta";
            String [] columnas = new String [] {"idRuta","timestamp"};
            String where = null;
            String [] argumentos = null;
            String groupby = null;
            String having = null;
            String orderby = null;
            String limit = null;

            Cursor cursorRuta = bd.query(tabla,columnas,where,argumentos,groupby,having,orderby,limit);

            if (cursorRuta.moveToFirst()){
                do {
                    Ruta r = new Ruta (Integer.parseInt(cursorRuta.getString(0)),cursorRuta.getString(1));

                } while(cursorRuta.moveToNext());

            }// end ifi


        }// end if


        return rt;
    }// end obtenerRutas


    // Guardar coordenadas

    public void guardarCoordenadas(Coordenada coordenada){

        if(bd.isOpen() && coordenada != null){

            ContentValues dato = new ContentValues();

            dato.put("latitud",coordenada.getLatitud());
            dato.put("longitud",coordenada.getLongitud());
            dato.put("timestamp",coordenada.getTimestap());
            dato.put("idRuta", coordenada.getId_ruta());

            bd.insert("punto", null, dato);


        }// end if



    } // finalizar guardar en la BD



    // Método para recuperar las coordenadas en forma de ArrayList.

    public List<Coordenada> obtenerCoordenadas(String coordenadaSeleccionada){

        // Guardamos las coordenadas en un ArrayList.

        List<Coordenada> coordenada = new ArrayList<>();

        if(bd.isOpen()){

            // Cargamos los datos para la consulta

            String tabla = "punto";
            String [] columnas = new String[]{"idPunto, latitud","longitud","timestamp, idRuta"};
            String where = "idRuta = ?"; // Aquí vamos a poner el refinamiento de clave primaria clave ajena.
            String [] argumentos = new String [] {coordenadaSeleccionada};
            String groupby = null;
            String having = null;
            String orderby = "idPunto desc";
            String limite = null;

            // Creamos el cursor

            Cursor cursorCoordenadas = bd.query(tabla,columnas,where,argumentos,groupby,having,orderby,limite);

            // Recorremos el cursor

            if(cursorCoordenadas.moveToFirst()){

                do {
                    Coordenada c = new Coordenada(Double.parseDouble(cursorCoordenadas.getString(1)),Double.parseDouble(cursorCoordenadas.getString(2)),cursorCoordenadas.getString(3));
                }while (cursorCoordenadas.moveToNext());


            }// end if

        }// end if

        return coordenada;

    }// end obtenerCoordenadas

    // Debug

    public int cantidadRuta(){

        Cursor cRuta = bd.rawQuery("SELECT * FROM ruta",null);
        return cRuta.getCount();

    }// end cantidad ruta

    public int cantidadCoordenadas(){

        Cursor cCoordenadas = bd.rawQuery("SELECT * FROM punto",null);

        return cCoordenadas.getCount();
    }// end cantidad coordenadas
/*
    public int primeraRuta(){

        Cursor cpRuta = bd.rawQuery("SELECT idRuta FROM ruta where idRuta = 0", null);

        return Integer.parseInt(String.valueOf(cpRuta));

    }// end primeraRuta
*/








}//end class
