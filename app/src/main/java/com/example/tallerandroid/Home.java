package com.example.tallerandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    TextView etData;
    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        etData = findViewById(R.id.etData);
        Bundle dataReceive = getIntent().getExtras();
        username = dataReceive.getString("userName");
        password = dataReceive.getString("passwd");
        etData.setText("Hello " + dataReceive.getString("userName") + ", welcome to my app");
    }

    public void logout(View l) {
        Intent ir = new Intent(this, MainActivity.class);
        ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TOP | ir.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(ir);
    }

    public void gotolists(View l) {
        Intent ir = new Intent(this, MyLists.class);
        Bundle data = new Bundle();
        data.putString("userName", username);
        data.putString("passwd", password);
        ir.putExtras(data);
        ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TOP | ir.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(ir);
    }

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
                Intent ir = new Intent(this, MainActivity.class);
                ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TOP | ir.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(ir);
                return true;
            case  R.id.menuLists:
                //Toast.makeText(this, "Hola menu Home", Toast.LENGTH_LONG).show();
                Intent i = new Intent(this, MyLists.class);
                Bundle data = new Bundle();
                data.putString("userName", username);
                data.putString("passwd", password);
                i.putExtras(data);
                i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP | i.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}