package com.example.zoomsoft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button loginButton = findViewById(R.id.login);
        final Button registerButton = findViewById(R.id.register);

        //Home Page-after onClick, user variable is initialized
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent login = new Intent(getApplicationContext(),login.class);
                startActivity(login);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent register = new Intent(getApplicationContext(),register.class);
                startActivity(register);
            }
        });
        //===================================================
    }

    //WorkToDo:
    //Connect to firebase and fireStore -> success

    //implement the login functionality and register functionality ->


}