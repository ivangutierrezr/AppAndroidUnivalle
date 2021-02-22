package com.example.tallerandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.tallerandroid.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnIniciarSesion;
    EditText etUserName, etPasswd;
    Switch swAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Toast.makeText(this,"OnCreate", Toast.LENGTH_LONG).show();

        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);

        etUserName = findViewById(R.id.etUserName);
        etPasswd = findViewById(R.id.etPasswd);
        swAuth = findViewById(R.id.swAuth);

        btnIniciarSesion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
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