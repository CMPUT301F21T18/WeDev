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

public class ViewFriend<dataBase> extends AppCompatActivity{
    public static final String EXTRA_TEXT2 = "com.example.zoomsoft.EXTRA_TEXT";
    ListView friendsList;
    ListView friendsHabitsList;
    ArrayList<String> friendsDataList = new ArrayList<>();


    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_friend_page);

        FriendsFirebase friendsFirebase = new FriendsFirebase();
        friendsFirebase.getFriend(new FriendsFirebase.FriendsInterface() {
            @Override
            public void callBackFriends(ArrayList<String> friends) {
                FriendsArrayAdapter friendsArrayAdapter = new FriendsArrayAdapter(getApplicationContext(),friends);
                friendsList = findViewById(R.id.friends_list);
                friendsList.setAdapter(friendsArrayAdapter);

                friendsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        db = FirebaseFirestore.getInstance();
                        final CollectionReference collectionReference = db.collection("Events");
//                        EditText editText = (EditText) friendsList.getItemAtPosition(position);
//                        String friendName = editText.getText().toString();
                        String friendName = friendsList.getItemAtPosition(position).toString();
                        collectionReference
                                .document(friendName)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        Intent intent = new Intent(ViewFriend.this,ViewFriendsHabit.class);
                                        intent.putExtra(EXTRA_TEXT2,friendName);
                                        startActivity(intent);
                                    }
                                });

                    }
                });
            }
        });
    }

//    public void displayHabit() {
//            setContentView(R.layout.friends_habits);
//            FriendsHabitsFirebase friendsHabitsFirebase = new FriendsHabitsFirebase();
//            friendsHabitsFirebase.getFriendsHabits(new FriendsHabitsFirebase.FriendsHabitsInterface() {
//                @Override
//                public void callBackFriendsHabits(ArrayList<String> friendsHabits) {
//                    FriendsHabitsArrayAdapter friendsHabitsArrayAdapter = new FriendsHabitsArrayAdapter(getApplicationContext(),friendsHabits);
//                    friendsHabitsList = findViewById(R.id.friends_habits_list);
//                    friendsHabitsList.setAdapter(friendsHabitsArrayAdapter);
//                }
//            });
//    }
}

//                        db = FirebaseFirestore.getInstance();
//                        final CollectionReference collectionReference = db.collection("Habits");
//                        String friendName = (String) friendsList.getItemAtPosition(position);
//                        collectionReference
//                                .document(friendName)
//                                .get()
//                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                        if(task.isSuccessful()) {
//                                            DocumentSnapshot documentSnapshot = task.getResult();
//                                            if (documentSnapshot.exists()) {
//                                                Map<String, Object> map = documentSnapshot.getData();
//                                                Log.d("Map provided: ", map.toString());
//                                                ArrayList<String> friendsHabits = (ArrayList<String>) map.get("friends"); //an arraylist of friends
//                                                .callBackFriends(friendsHabits);
//                                            }
//                                        }
//                                    }
//                                });



//                        @Override
//                        protected void onCreate(Bundle savedInstanceState ) {
//                            super.onCreate(savedInstanceState);
//                            setContentView(R.layout.friends_habits);
//                            FriendsHabitsFirebase friendsHabitsFirebase = new FriendsHabitsFirebase();
//                            friendsHabitsFirebase.getFriendsHabits(new FriendsHabitsFirebase.FriendsHabitsInterface() {
//                                @Override
//                                public void callBackFriendsHabits(ArrayList<String> friendsHabits) {
//                                    FriendsHabitsArrayAdapter friendsHabitsArrayAdapter = new FriendsHabitsArrayAdapter(getApplicationContext(),friendsHabits);
//                                    friendsHabitsList = findViewById(R.id.friends_habits_list);
//                                    friendsHabitsList.setAdapter(friendsHabitsArrayAdapter);
//                                }
//                            });
//                        }

//
// db = FirebaseFirestore.getInstance();
//final CollectionReference collectionReference = db.collection("Habits");
//        String friendName = (String) friendsList.getItemAtPosition(position);
//        collectionReference
//        .document(friendName)
//        .get()
//        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//@Override
//public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//        if(task.isSuccessful()) {
//        setContentView(R.layout.friends_habits);
//        FriendsHabitsFirebase friendsHabitsFirebase = new FriendsHabitsFirebase();
//        friendsHabitsFirebase.getFriendsHabits(new FriendsHabitsFirebase.FriendsHabitsInterface() {
//@Override
//public void callBackFriendsHabits(ArrayList<String> friendsHabits) {
//        FriendsHabitsArrayAdapter friendsHabitsArrayAdapter = new FriendsHabitsArrayAdapter(getApplicationContext(), friendsHabits);
//        friendsHabitsList = findViewById(R.id.friends_habits_list);
//        friendsHabitsList.setAdapter(friendsHabitsArrayAdapter);
//        }
//        });
//        }
//        }
//        });