package com.landeru.mitrekking;

import android.Manifest;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;


/**
 * Created by lander on 10/3/16.
 */
public class Servicio extends IntentService {

    GestorBD baseDatos = null;

    // manager

    LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    // Constructor
    public Servicio() {
        super("Servicio");

    }// contructor del servicio

    @Override
    protected void onHandleIntent(Intent intent) {
        //listener
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Llamada al método para guardar en la BD las coordenadas.

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }


            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        // Comprobación de los permisos

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }





    }// end onHandleIntent
}
