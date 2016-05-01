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
import android.widget.CursorAdapter;

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

        private String tablaRuta = "CREATE TABLE IF NOT EXISTS ruta (" +
                                                                       "idRuta INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                                       "timestamp text" +
                                                                     ");";
        private String tablaPunto = "CREATE TABLE IF NOT EXISTS punto (" +
                                                                        "idPunto INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                                                                       "latitud REAL, " +
                                                                        "longitud REAL,timestamp text," +
                                                                        "idRuta INTEGER, " +
                                                                        "FOREIGN KEY (idRuta) REFERENCES ruta (idRuta)" +
                                                                       ");";

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
                db.execSQL("PRAGMA foreign_keys=ON;");
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

    public void guardarRuta(){

        if (bd.isOpen()) {

            Calendar cal = new GregorianCalendar();

            Date date = cal.getTime();

            SimpleDateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            String tiempo = fechaHora.format(date);

            String sql = "INSERT INTO ruta (timestamp) VALUES ('"+tiempo+"')";

            bd.execSQL(sql);

        }// end if

    } //end gurardar Ruta


    // Devolver la ruta última

    public int mostrarIdRuta(){
      int ultimaRuta = Integer.MAX_VALUE;

      if(bd.isOpen()){

            Cursor cursorRuta = bd.rawQuery("SELECT idRuta from ruta ORDER BY idRuta DESC", null);
            cursorRuta.moveToFirst();
            ultimaRuta = cursorRuta.getInt(0);
            cursorRuta.close();

      }//end if

        return ultimaRuta;

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

            String sql = "INSERT INTO punto (latitud,longitud,timestamp,idRuta) values ("+coordenada.getLatitud()+","+coordenada.getLongitud()+",'"+coordenada.getTimestap()+"',"+coordenada.getId_ruta()+");";

            bd.execSQL(sql);

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

    public int mostrarIdCoordenadas(int idRuta){

        int idCoordenada = Integer.MAX_VALUE;

        if(bd.isOpen()){

            Cursor cursorRuta = bd.rawQuery("SELECT idPunto from punto where idRuta = "+idRuta+" ORDER BY idPunto DESC", null);
            cursorRuta.moveToFirst();
            idCoordenada = cursorRuta.getInt(0);
            cursorRuta.close();

        }//end if


        return idCoordenada;
    }// end mostrarIdCoordenadas


}//end class
