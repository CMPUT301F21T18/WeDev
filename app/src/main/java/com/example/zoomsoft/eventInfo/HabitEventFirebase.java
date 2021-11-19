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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HabitEventFirebase {

    interface MyCallBack {
        void updateComment(String s); //should be updateDescription
        void getAllDates(List<String> list);
        void getHabitDetails(HashMap<String, Object> map);
    }

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public String email = "a@gmail.com";
    public String habitName;
    Source source = Source.SERVER;

    public HabitEventFirebase(String habitName) {
        this.habitName = habitName;
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
                        HashMap hashMap = (HashMap) map.get("Walk a dog");
                        String description = (String) hashMap.get("description");
                        myCallBack.updateComment(description);
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
                        HashMap hashMap = (HashMap) map.get("Walk a dog");
                        for(String str : (Set<String>) hashMap.keySet()) {
                            if (str.equals("description") || str.equals("reason") || str.equals("days")) continue;
                            list.add(str);
                        }
                        myCallBack.getAllDates(list);
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
                        HashMap<String, Object> hashMap = (HashMap) map.get("Walk a dog");
                        myCallBack.getHabitDetails(hashMap);
                    }
                }
            }
        });
    }

}
