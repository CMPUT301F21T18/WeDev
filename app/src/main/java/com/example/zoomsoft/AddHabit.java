package com.example.zoomsoft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AddHabit extends AppCompatActivity {

    EditText habitTitle;
    EditText habitReason;
    Switch status;
    Switch dayOfWeek;
    DatePicker startDate;
    ImageButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_habit);

        addButton = findViewById(R.id.edit_habit_check);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                habitTitle = findViewById(R.id.habit_title_edit_text);
                habitReason = findViewById(R.id.habit_reason_edit_text);
                status = (Switch) findViewById(R.id.public_private_switch);
                startDate = (DatePicker) findViewById(R.id.datePicker);

                String privacy;

                if (status.isChecked()){
                    privacy = "private";
                }
                else {
                    privacy = "public";
                }

                String title = habitTitle.getText().toString();
                int newDay = startDate.getDayOfMonth();
                int newMonth = startDate.getMonth() + 1;
                int newYear = startDate.getYear();
                String date = newYear+"-"+newMonth+"-"+newDay;
                String reason = habitReason.getText().toString();
                ArrayList<Integer> days = new ArrayList<>();

                //Reading the switches and setting days of the week
                dayOfWeek = findViewById(R.id.switch_1);
                if (dayOfWeek.isChecked()){
                    days.add(1);
                }
                else {
                    days.add(0);
                }
                dayOfWeek = findViewById(R.id.switch_2);
                if (dayOfWeek.isChecked()){
                    days.add(1);
                }
                else {
                    days.add(0);
                }
                dayOfWeek = findViewById(R.id.switch_3);
                if (dayOfWeek.isChecked()){
                    days.add(1);
                }
                else {
                    days.add(0);
                }
                dayOfWeek = findViewById(R.id.switch_4);
                if (dayOfWeek.isChecked()){
                    days.add(1);
                }
                else {
                    days.add(0);
                }
                dayOfWeek = findViewById(R.id.switch_5);
                if (dayOfWeek.isChecked()){
                    days.add(1);
                }
                else {
                    days.add(0);
                }
                dayOfWeek = findViewById(R.id.switch_6);
                if (dayOfWeek.isChecked()){
                    days.add(1);
                }
                else {
                    days.add(0);
                }
                dayOfWeek = findViewById(R.id.switch_7);
                if (dayOfWeek.isChecked()){
                    days.add(1);
                }
                else {
                    days.add(0);
                }


                Habits habit = new Habits(title, date, reason, days, privacy);
                MainPageFirebase mainPageFirebase = new MainPageFirebase();
                mainPageFirebase.addNewHabit(habit);

                //To return back to MainPage once the habit has been added
                Intent intent = new Intent(AddHabit.this, MainPageTabs.class);
                startActivity(intent);
            }
        });

    }
}