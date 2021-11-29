package com.example.zoomsoft;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;

/**
 * ViewFriendsHabit allows a doer to see a certain user's list of Habits
 */
public class ViewFriendsHabit extends AppCompatActivity {

    ListView friendsHabitList;
    public static String friendEmail;

    /**
     * onCreate runs at the start and has firebase connectivity
     * @param savedInstanceState The last saved instance state of the Fragment, or null if this is a freshly created Fragment.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        friendEmail = intent.getStringExtra(ViewFriend.EXTRA_TEXT2);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_habits);

        FriendsHabitsFirebase friendsHabitsFirebase = new FriendsHabitsFirebase();
        friendsHabitsFirebase.getFriendsHabits(new FriendsHabitsFirebase.FriendsHabitsInterface() {
            @Override

            /**
             * friendsHabit: array of string that contains habits of the friends
             * @param friendsHabits
             */
            public void callBackFriendsHabits(ArrayList<String> friendsHabits) {
                FriendsHabitsArrayAdapter friendsHabitsArrayAdapter = new FriendsHabitsArrayAdapter(getApplicationContext(),friendsHabits);
                friendsHabitList = findViewById(R.id.friends_habits_list);
                friendsHabitList.setAdapter(friendsHabitsArrayAdapter);
            }
        });
    }
}
