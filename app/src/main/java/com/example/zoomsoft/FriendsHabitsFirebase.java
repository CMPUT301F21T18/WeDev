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
        final CollectionReference collectionReference = db.collection("Events");
        collectionReference
                .document(email)
                .get(source)
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if(documentSnapshot.exists()){
                                Map<String, Object> map = documentSnapshot.getData();
                                ArrayList<String> friendsHabitsList = new ArrayList<>(map.keySet());
                                friendsHabitsInterface.callBackFriendsHabits(friendsHabitsList);
                            }
                        }else{

                        }
                    }
                });

    }
}



