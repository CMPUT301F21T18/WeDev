package com.example.zoomsoft;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainPageFirebase {

    interface MainPageInterface {
        void getHabitInterface(ArrayList<String> habitArrayList);
        void getNewHabitDetails(Habits habits);
    }

    public MainPageFirebase() {

    }

    FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    final CollectionReference collectionReference = rootRef.collection("Events");
    String email = "a@gmail.com";
    Source source = Source.SERVER;

    public void getListOfHabits(MainPageInterface mainPageInterface){
        collectionReference
                .document(email)
                .get(source)
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if(documentSnapshot.exists()) {
                                Map<String, Object> map = documentSnapshot.getData(); //nameofhabit -> detailsofHaBIT key -> value
                                ArrayList<String> habitNameList = new ArrayList<>(map.keySet());
                                mainPageInterface.getHabitInterface(habitNameList);
                            }
                        }
                        else {
                            //nothing for now
                        }
                    }
                });
    }

    public void addNewHabit(MainPageInterface mainPageInterface){

    }




}
