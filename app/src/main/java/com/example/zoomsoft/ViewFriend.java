package com.example.zoomsoft;



import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ViewFriend<dataBase> extends AppCompatActivity{
    ListView friendsListView;
    ArrayList<String> friendsDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_friend_page);


        FriendsFirebase friendsFirebase = new FriendsFirebase();
        friendsFirebase.getFriend(new FriendsFirebase.FriendsInterface() {
            @Override
            public void callBackFriends(ArrayList<String> friends) {
                FriendsArrayAdapter friendsArrayAdapter = new FriendsArrayAdapter(getApplicationContext(),friends);
                ListView friendsList = findViewById(R.id.friends_list);
                friendsList.setAdapter(friendsArrayAdapter);
            }
        });
    }
}

