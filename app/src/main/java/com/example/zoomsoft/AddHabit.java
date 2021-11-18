package com.example.zoomsoft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.firestore.FirebaseFirestore;

public class AddHabit extends AppCompatActivity {

    EditText habitTitle;
    ImageButton addButton;
    FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_habit);

        addButton = findViewById(R.id.edit_habit_check);
        habitTitle = findViewById(R.id.habit_title_edit_text);
        String title = habitTitle.getText().toString();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AddHabit.this, ListOfHabitsMainPageFrag.class);
                startActivity(intent);
            }
        });

    }
}