package com.example.tallerandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;


import com.example.tallerandroid.R;
import com.example.tallerandroid.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

public class MainActivity<connectivityManager> extends AppCompatActivity implements View.OnClickListener {


    Button btnIniciarSesion;
    EditText etUserName, etPasswd;
    Switch swAuth;

    ActivityMainBinding binding;
    public static final String BroadcastStringForAction="checkinternet";

    private IntentFilter mIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setContentView(R.layout.activity_main);
        // Toast.makeText(this,"OnCreate", Toast.LENGTH_LONG).show();

        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);

        etUserName = findViewById(R.id.etUserName);
        etPasswd = findViewById(R.id.etPasswd);
        swAuth = findViewById(R.id.swAuth);

        btnIniciarSesion.setOnClickListener(this);

        mIntentFilter= new IntentFilter();
        mIntentFilter.addAction(BroadcastStringForAction);
        Intent serviceIntent = new Intent(this,MyService.class );
        startService(serviceIntent);

        binding.appNoconnected.setVisibility(View.GONE);
        if(isOnline(getApplicationContext()))
        {
            Set_Visibility_ON();
        }
        else
        {
            Set_Visibility_OFF();

        }



    }

    public BroadcastReceiver MyReceiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(BroadcastStringForAction))
            {
                if(intent.getStringExtra("online_status").equals("true"))
                {
                    Set_Visibility_ON();
                }
                else
                {
                    Set_Visibility_OFF();
                }
            }
        }
    };

    public boolean isOnline (Context c) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        if (ni != null && ni.isConnectedOrConnecting())
            return true;
        else
            return false;
    }

    public void Set_Visibility_ON()
    {
        binding.appNoconnected.setVisibility(View.GONE);
        binding.btnSubmit.setVisibility(View.VISIBLE);
        binding.parent.setBackgroundColor(Color.WHITE);
    }

    public void Set_Visibility_OFF()
    {
        binding.appNoconnected.setVisibility(View.VISIBLE);
        binding.btnSubmit.setVisibility(View.GONE);
        binding.parent.setBackgroundColor(Color.RED);
    }

    protected void onRestart()
    {
        super.onRestart();
        registerReceiver(MyReceiver, mIntentFilter);

    }

    protected void onPause(){
        super.onPause();
        unregisterReceiver(MyReceiver);
    }

    protected void onResume(){
        super.onResume();
        registerReceiver(MyReceiver,mIntentFilter);
    }


   /* private boolean haveNetwork(){
        boolean have_WIFI = false;
        boolean have_MobilData = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        Network[] networkInfos= connectivityManager.getAllNetworks();

        for( Network info:networkInfos){
            if (info.getByName ().equalsIgnoreCase("WIFI"))
                have_WIFI=true;


        }

    }*/
   @NotNull
  
    @Override
    public void onClick(View v) {

      /*  if(!isConnected(this)){
            showCustomDialog();
        }
*/
        switch (v.getId()) {
            case R.id.btnIniciarSesion:
                if (etUserName.getText().toString().matches("") || etPasswd.getText().toString().matches("")) {
                    Toast.makeText(this,"Username and password are required", Toast.LENGTH_LONG).show();
                } else if (swAuth.isChecked() != true) {
                    Toast.makeText(this,"You must check that you are human", Toast.LENGTH_LONG).show();
                } else {
                    Intent ir = new Intent(this,Home.class);
                    Bundle data = new Bundle();
                    data.putString("userName", etUserName.getText().toString());
                    data.putString("passwd", etPasswd.getText().toString());
                    ir.putExtras(data);
                    ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TOP | ir.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(ir);
                }
            default:
                break;
        }
    }

  /*  private boolean isConnected(MainActivity mainActivity) {


        ConnectivityManager connectivityManager = (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater Inflater = getMenuInflater();
        Inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case  R.id.menuInicio:
                //Toast.makeText(this, "Hola menu Home", Toast.LENGTH_LONG).show();
                Intent ir2 = new Intent(this, MainActivity.class);
                ir2.addFlags(ir2.FLAG_ACTIVITY_CLEAR_TOP | ir2.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(ir2);
            case  R.id.menuHome:
                //Toast.makeText(this, "Hola menu Home", Toast.LENGTH_LONG).show();
                Intent ir = new Intent(this, Home.class);
                ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TOP | ir.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(ir);
            case  R.id.menuLists:
                //Toast.makeText(this, "Hola menu Home", Toast.LENGTH_LONG).show();
                Intent i = new Intent(this, MyLists.class);
                i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP | i.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Toast.makeText(this,"OnStart", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Toast.makeText(this,"OnStop", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Toast.makeText(this,"OnDestroy", Toast.LENGTH_LONG).show();
    }
}