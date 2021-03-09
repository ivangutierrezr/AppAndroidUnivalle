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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyLists extends AppCompatActivity {

    ListView listofthings;
    String username;
    String password;

    Object users;
    String usersArray;
    private String url1 = "https://invessoft.com/api/eventos/2";
    RequestQueue requestQueue;

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

    public void getdb(View l) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url1, null, response -> {
                    try {
                        users = response.get("agenda");
                        System.out.println(users);
                        usersArray = users.toString();
                        System.out.println(usersArray);

                        JSONArray array = new JSONArray(usersArray);
                        for (int i =0; i < array.length(); i++) {
                          /*  "id_trabajo_agenda": 619,
                                "fecha": "2020-08-21",
                                "hora_inicio": "10:00:00",
                                "hora_fin": "10:20:00",
                                "id_espacio": 46,
                                "id_trabajo":*/
                            JSONObject row = array.getJSONObject(i);
                            String id_trabajo_agenda = row.getString("id_trabajo_agenda");
                            String fecha = row.getString("fecha");
                            String hora_inicio = row.getString("hora_inicio");
                            String hora_fin = row.getString("hora_fin");
                            String id_espacio = row.getString("id_espacio");
                            String id_trabajo = row.getString("id_trabajo");

                            System.out.println("id_trabajo_agenda: ");
                            System.out.println(id_trabajo_agenda);
                            System.out.println("fecha: ");
                            System.out.println(fecha);
                            System.out.println("hora_inicio: ");
                            System.out.println(hora_inicio);
                            System.out.println("hora_fin: ");
                            System.out.println(hora_fin);
                            System.out.println("id_espacio: ");
                            System.out.println(id_espacio);
                            System.out.println("id_trabajo: ");
                            System.out.println(id_trabajo);

//                            private static final String SQL_CREATE_ENTRIES =
//                                    "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
//                                            FeedEntry._ID + " INTEGER PRIMARY KEY," +
//                                            FeedEntry.COLUMN_NAME_TITLE + " TEXT," +
//                                            FeedEntry.COLUMN_NAME_SUBTITLE + " TEXT)";
//
//                            private static final String SQL_DELETE_ENTRIES =
//                                    "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    // TODO: Handle error

                });

        requestQueue.add(jsonObjectRequest);

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