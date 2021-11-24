package com.example.zoomsoft;


import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FriendsHabitsDisplay<dataBase> extends AppCompatActivity {
    ListView friendsHabitsList;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_habits);

        FriendsHabitsFirebase friendsHabitsFirebase = new FriendsHabitsFirebase();
        friendsHabitsFirebase.getFriendsHabits(new FriendsHabitsFirebase.FriendsHabitsInterface() {
            @Override
            public void callBackFriendsHabits(ArrayList<String> friendsHabits) {
                FriendsArrayAdapter friendsHabitArrayAdapter = new FriendsArrayAdapter(getApplicationContext(),friendsHabits);
                friendsHabitsList = findViewById(R.id.friends_habits_list);
                friendsHabitsList.setAdapter(friendsHabitArrayAdapter);
            }
        });

    }
}
