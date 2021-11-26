package com.example.zoomsoft;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zoomsoft.eventInfo.HabitInfoFirebase;

import java.util.ArrayList;

public class EditHabit extends AppCompatActivity {

    EditText habitTitle;
    EditText habitReason;
    Switch statusSwitch;
    Switch dayOfWeek;
    DatePicker startDate;
    ImageButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_habit);

//        HabitInfoFirebase habitInfoFirebase = new HabitInfoFirebase();
//
//        habitTitle = findViewById(R.id.habit_title_edit_text);
//        habitReason = findViewById(R.id.habit_reason_edit_text);
//        statusSwitch = (Switch) findViewById(R.id.public_private_switch);
//        startDate = (DatePicker) findViewById(R.id.datePicker);
//        ArrayList<Integer> days = new ArrayList<>();
//
//        habitTitle.setText(habitInfoFirebase.habitName);
//
//        habitInfoFirebase.getHabitReason(new HabitInfoFirebase.MyCallBack() {
//            @Override
//            public void getDays(ArrayList<Long> days) {
//
//            }
//
//            @Override
//            public void getReason(String reason) {
//                habitReason.setText(reason);
//            }
//
//            @Override
//            public void getStartDate(String oldDate) {
//                int i = oldDate.length();
//                int day = Integer.parseInt(oldDate.substring(i-2, i));
//                int month;
//                if (i == 10){
//                    month = Integer.parseInt(oldDate.substring(i-5, i-3));
//                }
//                else {
//                    month = Integer.parseInt(oldDate.substring(i-4, i-3));
//                }
//                int year = Integer.parseInt(oldDate.substring(0, 4));
//                startDate.updateDate(year, month-1, day);
//            }
//
//            @Override
//            public void getStatus(String status) {
//                if (status == "private"){
//                    statusSwitch.setChecked(true);
//                }
//            }
//        });
//
//        addButton = findViewById(R.id.edit_habit_check);
//
//
//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//                String privacy;
//
//                if (statusSwitch.isChecked()){
//                    privacy = "private";
//                }
//                else {
//                    privacy = "public";
//                }
//
//                String title = habitTitle.getText().toString();
//                int newDay = startDate.getDayOfMonth();
//                int newMonth = startDate.getMonth() + 1;
//                int newYear = startDate.getYear();
//                String date = newYear+"-"+newMonth+"-"+newDay;
//                String reason = habitReason.getText().toString();
//
//                //Reading the switches and setting days of the week
//                dayOfWeek = findViewById(R.id.switch_1);
//                if (dayOfWeek.isChecked()){
//                    days.add(1);
//                }
//                else {
//                    days.add(0);
//                }
//                dayOfWeek = findViewById(R.id.switch_2);
//                if (dayOfWeek.isChecked()){
//                    days.add(1);
//                }
//                else {
//                    days.add(0);
//                }
//                dayOfWeek = findViewById(R.id.switch_3);
//                if (dayOfWeek.isChecked()){
//                    days.add(1);
//                }
//                else {
//                    days.add(0);
//                }
//                dayOfWeek = findViewById(R.id.switch_4);
//                if (dayOfWeek.isChecked()){
//                    days.add(1);
//                }
//                else {
//                    days.add(0);
//                }
//                dayOfWeek = findViewById(R.id.switch_5);
//                if (dayOfWeek.isChecked()){
//                    days.add(1);
//                }
//                else {
//                    days.add(0);
//                }
//                dayOfWeek = findViewById(R.id.switch_6);
//                if (dayOfWeek.isChecked()){
//                    days.add(1);
//                }
//                else {
//                    days.add(0);
//                }
//                dayOfWeek = findViewById(R.id.switch_7);
//                if (dayOfWeek.isChecked()){
//                    days.add(1);
//                }
//                else {
//                    days.add(0);
//                }
//
//
//                Habits habit = new Habits(title, date, reason, days, privacy);
//                habitInfoFirebase.editHabit(habit);
//
//                //To return back to MainPage once the habit has been added
//                Intent intent = new Intent(EditHabit.this, MainPageTabs.class);
//                startActivity(intent);
//            }
//        });

    }

}
