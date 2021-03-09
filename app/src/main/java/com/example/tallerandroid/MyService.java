package com.example.tallerandroid;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.IBinder;

import java.util.List;

public class MyService extends Service {
    Context context = this;
    CheckConnection checkConnection;
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

//        new Thread(new Runnable() {
//            @Override
//           public void run() {
//                for (int i = 0; i <= 15; i++) {
//                    try {
//                        Toast.makeText(getApplicationContext(), "Hola Servicio", Toast.LENGTH_LONG).show();
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onCreate() {
        super.onCreate();
        checkConnection = new CheckConnection();
        checkConnection.execute();
    }


    class CheckConnection extends AsyncTask<Void,Integer,Void> { // Paramtros, Progreso, Resultados
        @Override
        protected Void doInBackground(Void... voids) {
            // colorear.setBackgroundColor(Color.rgb(aleatorio(),aleatorio(),aleatorio()));
            while(true) {
                boolean isConnected = false;
                try {
                    ConnectivityManager cm =
                            (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

                    NetworkInfo wifiConn = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    NetworkInfo mobileConn = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                    if ((wifiConn != null && wifiConn.isConnected() ) || (mobileConn != null && mobileConn.isConnected())) {
                        isConnected = true;
                    }
                    if (isConnected == false) {
                        onProgressUpdate(0);
                    } else {
                        onProgressUpdate(1);
                    }
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        protected void onProgressUpdate(Integer values) {
            super.onProgressUpdate(values);
            System.out.println(values);
            if (values.equals(0)) {
                ActivityManager am = (ActivityManager) context
                        .getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.RunningTaskInfo> alltasks = am
                        .getRunningTasks(1);
                for (ActivityManager.RunningTaskInfo aTask : alltasks) {
                    if (aTask.topActivity.getClassName().equals("com.example.tallerandroid.MainActivity")) {
                        System.out.println("We are in home");
                    } else {
                        Intent ir = new Intent(context, MainActivity.class);
                        ir.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(ir);
                    }
                }
            }
        }
    }
}
