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
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

public class Servicio extends Service {

    // Objeto para guardar las coordenadas
    Coordenada corde = null;
    GestorBD baseDatos = new GestorBD(this);

    // Location manager

//    LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onCreate(){
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                corde = new Coordenada(0.0,0.0,"nada");
            // Llamada al método para guardar en la BD las coordenadas.
                corde.setLatitud(location.getLatitude());
                corde.setLatitud(location.getLongitude());
                // Abrimos la BD
                baseDatos.abrirBD();
                // Guardamos el dato
                baseDatos.guardarCoordenadas(corde);

            }
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
}// end class
