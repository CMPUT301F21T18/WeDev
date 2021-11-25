package com.example.zoomsoft.eventInfo;

import static com.example.zoomsoft.MainPageTabs.email;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.zoomsoft.R;
import com.example.zoomsoft.loginandregister.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditEventActivity extends AppCompatActivity {
    EditText status;
    EditText comment;
    ImageButton good;
    Button addLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        status = findViewById(R.id.habit_reason_edit_text);
        comment = findViewById(R.id.habit_title_edit_text);

        addLocation = findViewById(R.id.AddLocation);
        good = findViewById(R.id.edit_habit_check);
        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(comment.getText().toString().isEmpty() || status.getText().toString().isEmpty())
//                    Toast.makeText(EditEventActivity.this,
//                            "Please don't leave any field empty", Toast.LENGTH_LONG).show();

                    //save in firebase firebase fireStore
                String statusString = status.getText().toString();
                String commentString = comment.getText().toString();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                final CollectionReference collectionReference = db.collection("Events");
                DocumentReference documentReference = collectionReference.document(email);
                if(!statusString.isEmpty()) {
                    Map<String,Object> updates = new HashMap<>();
                    updates.put(HabitInfo.clickedHabit + "." + HabitEventDisplay.clickedDate + "." + "status", statusString);
                    documentReference.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                                Log.w("TAG", "Deleted from firebase");
                            else
                                Log.w("TAG", "Error deleting document");
                        }
                    });
                }

                if(!commentString.isEmpty()) {
                    Map<String,Object> updates = new HashMap<>();
                    updates.put(HabitInfo.clickedHabit + "." + HabitEventDisplay.clickedDate + "." + "comment", commentString);
                    documentReference.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                                Log.w("TAG", "Deleted from firebase");
                            else
                                Log.w("TAG", "Error deleting document");
                        }
                    });
                }
                finish();
                Toast.makeText(EditEventActivity.this,
                            "Fields updated accordingly", Toast.LENGTH_LONG).show();
            }
        });

        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditEventActivity.this, ViewLocationMap.class);
                startActivity(intent);
            }
        });


    }
}