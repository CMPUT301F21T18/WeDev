package com.example.zoomsoft.eventInfo;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.zoomsoft.EditHabit;
import com.example.zoomsoft.Habits;
import com.example.zoomsoft.MainPageTabs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
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

    public interface MyCallBack {
        void getDays(ArrayList<Long> days);
        void getReason(String reason);
        void getStartDate(String date);
        void getStatus(String status);
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

    public void getHabitStatus(MyCallBack myCallBack) {
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
//                        Log.d("Map provided: ", map.toString());
                        HashMap hashMap = (HashMap) map.get(habitName);
                        String reason = (String) hashMap.get("status");
                        myCallBack.getStatus(reason);
                    }
                }
                else {
                    int x = 6; //will decide on this later
                }
            }
        });
    }

    public void deleteHabit(String clickedHabit){
        final CollectionReference collectionReference = db.collection("Events");
        DocumentReference documentReference = collectionReference.document(email);
        documentReference.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()) {
                        documentReference.update(clickedHabit, FieldValue.delete());
                    }
                }
            }
        });
    }

    public void editHabit(Habits habits){

        //get the title
        String title = habits.getHabitTitle();
        //get the reason
        String reason = habits.getHabitReason();
        //get the start date
        String date = habits.getStartDate();
        //get days of week (Need to change implementation over to ArrayList
        ArrayList<Integer> days = habits.getHabitWeekDay();
        //get privacy status
        String privacy = habits.getPrivacy();

        HashMap<String, Object> temp = new HashMap<>();
        temp.put("startDate", date);
        temp.put("reason", reason);
        temp.put("days", days);
        temp.put("status", privacy);

        HashMap<String, HashMap<String, Object>> data = new HashMap<>();
        data.put(title, temp);

        final CollectionReference collectionReference = db.collection("Events");
        DocumentReference documentReference = collectionReference.document(email);
        documentReference.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()) {
                        documentReference.update(habitName, data);
                    }
                }
            }
        });
    }
}
