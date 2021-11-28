package com.example.zoomsoft.eventInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;

import com.example.zoomsoft.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddEventActivity extends AppCompatActivity {

    Switch doneSwitch;
    ImageButton addButton;
    ImageButton cancelButton;
    DatePicker datePicker;
    EditText commentEditText;
    Button locationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        doneSwitch = findViewById(R.id.done_switch);
        addButton = findViewById(R.id.add_event_confirm_button);
        cancelButton = findViewById(R.id.add_event_cancel_button);
        datePicker = findViewById(R.id.add_event_datePicker);
        commentEditText = findViewById(R.id.event_comment_edit_text);
        locationButton = findViewById(R.id.add_event_location_button);

        //For the switch to change text to done or not done on screen
        doneSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (doneSwitch.isChecked()){
                    doneSwitch.setText("Done");
                }
                else {
                    doneSwitch.setText("Not Done");
                }
            }
        });

        //Proceed to add habit event
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int newDay = datePicker.getDayOfMonth();
                int newMonth = datePicker.getMonth() + 1;
                int newYear = datePicker.getYear();
                String date = newYear+"-"+newMonth+"-"+newDay;

                String comment = commentEditText.getText().toString();

                boolean completionStatus;

                if (doneSwitch.isChecked()){
                    completionStatus = true;
                }
                else{
                    completionStatus = false;
                }

                ArrayList<String> locationList = new ArrayList<>();
                locationList.add("N");
                locationList.add("N");

                HabitEventFirebase habitEventFirebase = new HabitEventFirebase();
                habitEventFirebase.addHabitEvent(date, comment, completionStatus, locationList);

                finish();

            }
        });

        //Return to prior activity
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

}