package com.example.zoomsoft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AddHabit extends AppCompatActivity {

    EditText habitTitle;
    EditText habitReason;
    EditText startDate;
    EditText daysOfWeek;
    ImageButton addButton;
    FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_habit);

        addButton = findViewById(R.id.edit_habit_check);
        habitTitle = findViewById(R.id.habit_title_edit_text);
        habitReason = findViewById(R.id.habit_reason_edit_text);
        startDate = findViewById(R.id.habit_date_edit_text);
        daysOfWeek = findViewById(R.id.habit_days_edit_text);

        String title = habitTitle.getText().toString();
        String reason = habitReason.getText().toString();
        String date = startDate.getText().toString();
        String days = daysOfWeek.getText().toString();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Habits habit = new Habits(title, date, reason, days);

                //To return back to MainPage once the habit has been added
                Intent intent = new Intent(AddHabit.this, ListOfHabitsMainPageFrag.class);
                startActivity(intent);
            }
        });

    }
}