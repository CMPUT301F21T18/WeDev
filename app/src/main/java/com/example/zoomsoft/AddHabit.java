package com.example.zoomsoft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AddHabit extends AppCompatActivity {

    EditText habitTitle;
    EditText habitReason;
    Switch status;
    EditText daysOfWeek;
    DatePicker startDate;
    ImageButton addButton;
    FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_habit);

        addButton = findViewById(R.id.edit_habit_check);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Need to make some changes in new_habit.xml such that daysOfWeek is received
                //as an ArrayList<Integer>
                habitTitle = findViewById(R.id.habit_title_edit_text);
                habitReason = findViewById(R.id.habit_reason_edit_text);
                status = (Switch) findViewById(R.id.public_private_switch);
                daysOfWeek = findViewById(R.id.habit_days_edit_text);
                startDate = (DatePicker) findViewById(R.id.datePicker);

                String privacy;

                if (status.isChecked()){
                    //privacy =
                }
                else {

                }

                String title = habitTitle.getText().toString();
                int newDay = startDate.getDayOfMonth();
                int newMonth = startDate.getMonth() + 1;
                int newYear = startDate.getYear();
                String date = newYear+"-"+newMonth+"-"+newDay;
                String reason = habitReason.getText().toString();
                //String privacy = status.getText().toString();
                String days = daysOfWeek.getText().toString();

                //Habits habit = new Habits(title, date, reason, days, privacy);
                Habits habit = new Habits(title);
                MainPageFirebase mainPageFirebase = new MainPageFirebase();
                mainPageFirebase.addNewHabit(habit);
                //To return back to MainPage once the habit has been added
                Intent intent = new Intent(AddHabit.this, MainPageTabs.class);
                startActivity(intent);
            }
        });

    }
}