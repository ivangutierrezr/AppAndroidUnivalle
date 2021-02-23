package com.example.tallerandroid;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("inside","oncreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
       throw new UnsupportedOperationException("not yet implement");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.post(periodoicUpdate);
        return START_STICKY;
    }

    public boolean isOnline (Context c) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        if (ni != null && ni.isConnectedOrConnecting())
            return true;
        else
            return false;
    }

    Handler handler= new Handler();
    private Runnable periodoicUpdate=new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(periodoicUpdate, 1*1000- SystemClock.elapsedRealtime()%1000);
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(MainActivity.BroadcastStringForAction);
            broadcastIntent.putExtra("online_status", " "+ isOnline(MyService.this));
            sendBroadcast(broadcastIntent);
        }
    };




}
