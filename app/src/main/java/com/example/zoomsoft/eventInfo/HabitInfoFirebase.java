package com.example.zoomsoft.eventInfo;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.zoomsoft.MainPageTabs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HabitInfoFirebase {
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public String email = MainPageTabs.email;
    public String habitName = HabitInfo.clickedHabit;
    Source source = Source.SERVER;

    interface MyCallBack {
        void getDays(ArrayList<Long> days);
        void getReason(String reason);
        void getStartDate(String date);
    }

    public HabitInfoFirebase() {
    }

    /*
    This function is responsible for getting the days that the user has selected and populates them
    in info fragment
     */
    public void getDaysSelected(MyCallBack myCallBack) {
        final CollectionReference collectionReference = db.collection("Events");
        DocumentReference documentReference = collectionReference.document(email);
        documentReference.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        Map<String, Object> map = documentSnapshot.getData();
                        List<String> list = new ArrayList<>();
                        HashMap hashMap = (HashMap) map.get(habitName);
                        ArrayList<Long> dayList = (ArrayList<Long>) hashMap.get("days");
                        myCallBack.getDays(dayList);
                    }
                }
            }
        });
    }

    /*
    This function gets the reason for the clicked Habit
     */
    public void getHabitReason(MyCallBack myCallBack) {
        String habitReason = "";
        final CollectionReference collectionReference = db.collection("Events");
        DocumentReference documentReference = collectionReference.document(email);
        documentReference.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()) {
                        Map<String, Object> map = documentSnapshot.getData();
                        Log.d("Map provided: ", map.toString());
                        HashMap hashMap = (HashMap) map.get(habitName);
                        String reason = (String) hashMap.get("reason");
                        myCallBack.getReason(reason);
                    }
                }
                else {
                    int x = 6; //will decide on this later
                }
            }
        });
    }

    public void getHabitStartDate(MyCallBack myCallBack) {
        final CollectionReference collectionReference = db.collection("Events");
        DocumentReference documentReference = collectionReference.document(email);
        documentReference.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()) {
                        Map<String, Object> map = documentSnapshot.getData();
                        Log.d("Map provided: ", map.toString());
                        HashMap hashMap = (HashMap) map.get(habitName);
                        String startDate = (String) hashMap.get("startDate");
                        myCallBack.getStartDate(startDate);
                    }
                }
                else {
                    int x = 6; //will decide on this later
                }
            }
        });
    }
}
