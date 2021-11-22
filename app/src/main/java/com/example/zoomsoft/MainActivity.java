package com.example.zoomsoft;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.zoomsoft.eventInfo.HabitInfo;
import com.example.zoomsoft.loginandregister.Login;
import com.example.zoomsoft.loginandregister.Register;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.zoomsoft";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button loginButton = findViewById(R.id.login);
        final Button registerButton = findViewById(R.id.register);

        //Home Page-after onClick, user variable is initialized
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent login = new Intent(getApplicationContext(), Login.class);
                startActivity(login);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent register = new Intent(getApplicationContext(), Register.class);
                startActivity(register);
            }
        });
        //Asks for permission to use the camera,
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] {
                            Manifest.permission.CAMERA
                    }, 100);
        }
        //===================================================
    }

    //WorkToDo:
    //Connect to firebase and fireStore -> success

    //implement the login functionality and register functionality ->


}