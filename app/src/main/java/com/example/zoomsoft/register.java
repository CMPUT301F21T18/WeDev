package com.example.zoomsoft;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class register extends AppCompatActivity {

    protected EditText usernameEditText;
    protected EditText emailEditText;
    protected EditText passwordEditText;
    protected Button registerButton;
    final String TAG = "Sample";
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        usernameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        registerButton = findViewById(R.id.register);
        final CollectionReference collectionReference = db.collection("User");

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                user = new User(username, email, password);
                //perform validation and make sure the username is unique and does not exist in the collection(Users/username)->can be done later on
                //add into firebase
                HashMap<String, String> data = new HashMap<>();
                if(username.length() > 0 && email.length() > 0 && password.length() > 6) {
                    data.put("email", email);
                    data.put("password", password);
//                    data.put("username", username);
                    collectionReference
                            .document(username)
                            .set(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "Data has been added successfully!");
                                    //call the home activity from here
                                    //===========>
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "Data could not be added!" + e.toString());
                                }
                            });
                }
            }
        });
    }

}

//    final String cityName = addCityEditText.getText().toString();
//    final String provinceName = addProvinceEditText.getText().toString();
//    HashMap<String, String> data = new HashMap<>();
//                if(cityName.length() > 0 && provinceName.length() > 0) {
//                        data.put("Province Name", provinceName);
//                        collectionReference
//                        .document(cityName)
//                        .set(data)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//@Override
//public void onSuccess(Void aVoid) {
//// These are a method which gets executed when the task is succeeded
//        Log.d(TAG, "Data has been added successfully!");
//        addCityEditText.setText("");
//        addProvinceEditText.setText("");
//        }
//        })
//        .addOnFailureListener(new OnFailureListener() {
//@Override
//public void onFailure(@NonNull Exception e) {
//// These are a method which gets executed if there’s any problem
//        Log.d(TAG, "Data could not be added!" + e.toString());
//        }
//        });
