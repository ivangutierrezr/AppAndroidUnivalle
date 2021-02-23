package com.example.tallerandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MyLists extends AppCompatActivity {

    ListView listofthings;
    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lists);
        Bundle dataReceive = getIntent().getExtras();
        username = dataReceive.getString("userName");
        password = dataReceive.getString("passwd");
        listofthings = findViewById(R.id.listofthings);
    }

    public void populateList(View l) {
        List<String> your_array_list = new ArrayList<String>();

        for (int i = 0; i < 5; i++) {
            your_array_list.add("Thing " + (i+1));
        }

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list );

        listofthings.setAdapter(arrayAdapter);
    }

    public void logout(View l) {
        Intent ir = new Intent(this, MainActivity.class);
        ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TOP | ir.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(ir);
    }

    public void gobacktohome(View l) {
        Intent irHome = new Intent(this, Home.class);
        Bundle data = new Bundle();
        data.putString("userName", username);
        data.putString("passwd", password);
        irHome.putExtras(data);
        irHome.addFlags(irHome.FLAG_ACTIVITY_CLEAR_TOP | irHome.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(irHome);
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
            case  R.id.menuHome:
                //Toast.makeText(this, "Hola menu Home", Toast.LENGTH_LONG).show();
                Intent i = new Intent(this, Home.class);
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