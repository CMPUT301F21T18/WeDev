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
/**
 * ViewFriends generates the ability to have a list of friends to see
 */
public class ViewFriend<dataBase> extends AppCompatActivity {
    public static final String EXTRA_TEXT2 = "com.example.zoomsoft.EXTRA_TEXT";
    ListView friendsList;
    FirebaseFirestore db;

    @Override
    /**
     * initializes firebase connectivity with friends
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_friend_page);

        FriendsFirebase friendsFirebase = new FriendsFirebase();
        friendsFirebase.getFriend(new FriendsFirebase.FriendsInterface() {
            @Override
            /**
             * callBackFriends initializes FriendsArrayAdapter
             * @param friends
             */
            public void callBackFriends(ArrayList<String> friends) {
                FriendsArrayAdapter friendsArrayAdapter = new FriendsArrayAdapter(getApplicationContext(), friends);
                friendsList = findViewById(R.id.friends_list);
                friendsList.setAdapter(friendsArrayAdapter);

                friendsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    /**
                     * database connectivity with friend name
                     * @param parent
                     * @param view
                     * @param position
                     * @param id
                     */
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        db = FirebaseFirestore.getInstance();
                        final CollectionReference collectionReference = db.collection("Events");
                        String friendName = friendsList.getItemAtPosition(position).toString();
                        collectionReference
                                .document(friendName)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    /**
                                     * onComplete check
                                     * @param task
                                     */
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        Intent intent = new Intent(ViewFriend.this, ViewFriendsHabit.class);
                                        intent.putExtra(EXTRA_TEXT2, friendName);
                                        startActivity(intent);
                                    }
                                });
                    }
                });
            }
        });
    }
}