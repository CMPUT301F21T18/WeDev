package com.example.zoomsoft.eventInfo;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.zoomsoft.MainPageTabs;
import com.example.zoomsoft.loginandregister.Login;
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

public class HabitEventFirebase {

    interface MyCallBack {
        void getDescription(String s); //should be getDescription

        void getAllDates(List<String> list, List<Boolean> dateList);

        void getHabitDetails(HashMap<String, Object> map);
    }
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public String email = MainPageTabs.email;
    public String habitName = HabitInfo.clickedHabit;
    Source source = Source.SERVER;

    //Snapshot method:


    public HabitEventFirebase() {

    }


    //email and name of the habit to access firebaseEvent collection


    //functions to get different values from firebase:

    /*
    This function gets the description of the habit that was clicked
     */
    public void getHabitDescription(MyCallBack myCallBack) {
        String habitDescription = "";
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
                        String description = (String) hashMap.get("description");
                        myCallBack.getDescription(description);
                    }
                }
                else {
                    int x = 6; //will decide on this later
                }
            }
        });
    }

    /*
    This function gets all the dates and store them as an arraylist
     */

    public void getAllDates(MyCallBack myCallBack) {
        final CollectionReference collectionReference = db.collection("Events");
        DocumentReference documentReference = collectionReference.document(email);
        documentReference.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()) {
                        Map<String, Object> map = documentSnapshot.getData();
                        List<String> list = new ArrayList<>();
                        HashMap hashMap = (HashMap) map.get(habitName);
                        List<Boolean> dateList = new ArrayList<>();
                        for(String str : (Set<String>) hashMap.keySet()) {
                            if (str.equals("description") || str.equals("reason") || str.equals("days")) continue;
                            list.add(str); //str is a date that the event occurred
                            HashMap hashMap1 = (HashMap) hashMap.get(str);
                            for(String date : (Set<String>) hashMap1.keySet()){
                                if(date.equals("done")) {
                                    dateList.add((Boolean) hashMap1.get("done"));
                                    break;
                                }
                            }
                        }
                        myCallBack.getAllDates(list, dateList);
                    }
                }
                else {
                    int x = 6; //will decide on this later
                }
            }
        });
    }


    /*
    This method returns the habit's comment
     */

    public void getHabitClickedDetails(MyCallBack myCallBack) {
        final CollectionReference collectionReference = db.collection("Events");
        DocumentReference documentReference = collectionReference.document(email);
        documentReference.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()) {
                        Map<String, Object> map = documentSnapshot.getData();
                        List<String> list = new ArrayList<>();
                        HashMap<String, Object> hashMap = (HashMap) map.get(habitName);
                        myCallBack.getHabitDetails(hashMap);
                        myCallBack.getDescription((String) hashMap.get("description"));
                    }
                }
            }
        });
    }

    /**
     * This function deletes an event
     * @param event
     */
    public void deleteHabitEvent(String event) {
        final CollectionReference collectionReference = db.collection("Events");
        DocumentReference documentReference = collectionReference.document(email);
        Map<String,Object> updates = new HashMap<>();
        updates.put(habitName+ "." + event, FieldValue.delete());
        documentReference.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                    Log.w("TAG", "Deleted from firebase");
                else
                    Log.w("TAG", "Error deleting document");
            }
        });
    }
    /*
       Map<String, Object> deleteSong = new HashMap<>();
    deleteSong.put("songList.songName3", FieldValue.delete());

    FirebaseFirestore.getInstance()
        .collection("yourCollection")
        .document("yourDocument")
        .update(deleteSong);
     */
}
