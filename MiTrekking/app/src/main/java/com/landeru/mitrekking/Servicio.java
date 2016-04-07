package com.landeru.mitrekking;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by lander on 10/3/16.
 */
public class Servicio extends IntentService {

    GestorBD baseDatos = null;

    // manager
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    // Constructor
    public Servicio(String name) {
        super(name);

        // Abrimos la BD

        baseDatos = new GestorBD(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
    //listener


    }//
}
