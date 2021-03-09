package com.example.tallerandroid;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /*for (int i = 0; i <= 15; i++) {
            Toast.makeText(this, "Hola Servicio", Toast.LENGTH_LONG).show();
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
            return super.onStartCommand(intent, flags, startId);
        }*/

        new Thread(new Runnable() {
            @Override
           public void run() {
                for (int i = 0; i <= 15; i++) {
                    try {
                        Toast.makeText(getApplicationContext(), "Hola Servicio", Toast.LENGTH_LONG).show();
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

}