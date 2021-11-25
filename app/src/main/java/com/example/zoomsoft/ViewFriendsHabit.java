package com.example.zoomsoft;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ViewFriendsHabit extends AppCompatActivity{
    ListView friendsHabitList;
    public static String friendEmail;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        friendEmail = intent.getStringExtra(ViewFriend.EXTRA_TEXT2);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_habits);

        FriendsHabitsFirebase friendsHabitsFirebase = new FriendsHabitsFirebase();
        friendsHabitsFirebase.getFriendsHabits(new FriendsHabitsFirebase.FriendsHabitsInterface() {
            @Override
            public void callBackFriendsHabits(ArrayList<String> friendsHabits) {
                FriendsHabitsArrayAdapter friendsHabitsArrayAdapter = new FriendsHabitsArrayAdapter(getApplicationContext(),friendsHabits);
                friendsHabitList = findViewById(R.id.friends_habits_list);
                friendsHabitList.setAdapter(friendsHabitsArrayAdapter);
            }
        });
    }
}
