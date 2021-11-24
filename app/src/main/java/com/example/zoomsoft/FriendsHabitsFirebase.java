package com.example.zoomsoft;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.Map;


public class FriendsHabitsFirebase extends AppCompatActivity {
    public String  email = ViewFriendsHabit.friendEmail;
    Source source = Source.SERVER;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    interface FriendsHabitsInterface{
        void callBackFriendsHabits (ArrayList<String> friendsHabits);
    }

    public FriendsHabitsFirebase() {

    }
    public void getFriendsHabits(FriendsHabitsInterface friendsHabitsInterface){
        final CollectionReference collectionReference = db.collection("Habits");
        DocumentReference documentReference = collectionReference.document(email);
        documentReference.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        Map<String, Object> map = documentSnapshot.getData();
                        Log.d("Map provided: ", map.toString());
                        ArrayList<String>  friendsHabits = (ArrayList<String>) map.get("habits"); //an arraylist of habits
                        friendsHabitsInterface.callBackFriendsHabits(friendsHabits);
                    }
                }
            }
        });
    }
}






//    ListView friendsHabitsList;
//    @Override
//    protected void onCreate(Bundle savedInstanceState ) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.);
//
//        FriendsFirebase friendsFirebase = new FriendsFirebase();
//        friendsFirebase.getFriend(new FriendsFirebase.FriendsInterface() {
//            @Override
//            public void callBackFriends(ArrayList<String> friends) {
//                FriendsArrayAdapter friendsArrayAdapter = new FriendsArrayAdapter(getApplicationContext(), );
//                friendsHabitsList = findViewById(R.id.);
//                friendsHabitsList.setAdapter(friendsArrayAdapter);
//            }
//        });
//    }



//    ListView friendsListView;
//    FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//    interface FriendsInterface{
//        void callBackFriends(ArrayList<String> friends );
//    }
//
//    public FriendsFirebase() {
//
//    }
//
//    /**
//     * Gets the list of friends for each user.
//     * @param friendsInterface
//     */
//
//    public void getFriend(FriendsInterface friendsInterface){
//        final CollectionReference collectionReference = db.collection("Friends");
//        DocumentReference documentReference = collectionReference.document(email);
//        documentReference.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()) {
//                    DocumentSnapshot documentSnapshot = task.getResult();
//                    if (documentSnapshot.exists()) {
//                        Map<String, Object> map = documentSnapshot.getData();
//                        Log.d("Map provided: ", map.toString());
//                        ArrayList<String>  friends = (ArrayList<String>) map.get("friends"); //an arraylist of friends
//                        friendsInterface.callBackFriends(friends);
//                    }
//                }
//            }
//        });
//    }
//}