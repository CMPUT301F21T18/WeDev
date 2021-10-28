package com.example.zoomsoft;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class login extends AppCompatActivity {

    protected EditText emailEditText;
    protected EditText passwordEditText;
    final String TAG = "Sample";
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    protected Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        final CollectionReference collectionReference = db.collection("User");

        //make sure that email exists and the password matches when login is clicked
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                DocumentReference documentReference = db.collection("User").document(email);
                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()) {
                                //use the document to login
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                //call the home activity
                            }
                            else {
                                //not such document
                                Log.d(TAG, "No such document with the login details");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
            }
        });
    }
}


//    DocumentReference docRef = db.collection("cities").document("SF");
//docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//@Override
//public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//        if (task.isSuccessful()) {
//        DocumentSnapshot document = task.getResult();
//        if (document.exists()) {
//        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//        } else {
//        Log.d(TAG, "No such document");
//        }
//        } else {
//        Log.d(TAG, "get failed with ", task.getException());
//        }
//        }
//        });