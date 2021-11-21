package com.example.zoomsoft;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainPageFirebase {

    interface MainPageInterface {
        void getHabitInterface(ArrayList<String> habitArrayList);
    }

    public MainPageFirebase() {

    }

    FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    final CollectionReference collectionReference = rootRef.collection("Habits");
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

    public void addNewHabit(Habits habits){

        //get the title
        String title = habits.getHabitTitle();
        //get the reason
        String reason = habits.getHabitReason();
        //get the start date
        String date = habits.getStartDate();
        //get days of week (Need to change implementation over to ArrayList
        String days = habits.getHabitWeekDay();

        HashMap<String, String> temp = new HashMap<>();
        temp.put("reason", "Because I should");
        temp.put("description", "doing the thing");

        HashMap<String, ArrayList<Integer>> temp2 = new HashMap<>();

        HashMap<String, HashMap<String, String>> data = new HashMap<>();
        data.put("Test", temp);

        collectionReference
                .document(email)
                .set(data);
    }




}
