package com.landeru.mitrekking;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Servicio extends Service {

    // Objeto para guardar las coordenadas
    Coordenada corde = null;
    GestorBD baseDatos = new GestorBD(this);


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onCreate(){
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                //constructor
                corde = new Coordenada(0.0,0.0,"nada");

                // seteamos la latitud y la longitud
                corde.setLatitud(location.getLatitude());
                corde.setLatitud(location.getLongitude());

                // fecha y hora
                Calendar cal = new GregorianCalendar();

                Date date = cal.getTime();

                SimpleDateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                corde.setTimestap(fechaHora.format(date));

                // Abrimos la BD
                baseDatos.abrirBD();

                // Llamamos al método que nos trae el último id_ruta
                corde.setId_ruta(baseDatos.mostrarIdRuta());
                // Guardamos el dato
                baseDatos.guardarCoordenadas(corde);
/* Debug
                System.out.println("CantidadRutas: "+baseDatos.cantidadRuta());
                System.out.println("Cantidad puntos: " + baseDatos.cantidadCoordenadas());
                System.out.println("Id ruta última ruta: " + baseDatos.mostrarIdRuta());
                System.out.println("Id último punto insertado: "+ baseDatos.mostrarIdCoordenadas(baseDatos.mostrarIdRuta()));
*/

            }//end location
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
        };
        // Comprobación de los permisos
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }



    }// end onCreate

    @Override
    public void onDestroy(){

            // Cerramos la BD
            baseDatos.cerrarBD();
            // Destruimos el servicio
            stopSelf();
            super.onDestroy();


        // Debug
/*
        Context context = getApplicationContext();
        CharSequence mensaje = "Servicio destroy";
        int duracion = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context,mensaje,duracion);
        toast.show();
*/


    }// end onDestroy
}// end class
