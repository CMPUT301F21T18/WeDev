package com.example.zoomsoft;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;

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

/**
 * Gets the view that displays the friends habits with the progress bar
 */
public class FriendsHabitsArrayAdapter extends  ArrayAdapter<String>{
    int totalDone;
    int count;
    FirebaseFirestore db;
    Source source = Source.SERVER;
    public String  friendsEmail = ViewFriendsHabit.friendEmail;


    public FriendsHabitsArrayAdapter(Context context, ArrayList<String> friendsHabitsArrayList) {
        super(context,R.layout.friends_habits_content,friendsHabitsArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent ){
        String friendsHabit = getItem(position);
        if(view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.friends_habits_content, parent, false);
        }

        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.friend_progress_bar);

        TextView item = view.findViewById(R.id.friends_habit_item);
        item.setText(friendsHabit);

        db = FirebaseFirestore.getInstance();

        final CollectionReference collectionReference = db.collection("Events");
        DocumentReference documentReference = collectionReference.document(friendsEmail);
        documentReference.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()) {
                        List<String> list = new ArrayList<>();
                        Map<String, Object> map = documentSnapshot.getData();
                        HashMap hashMap = (HashMap) map.get(friendsHabit);
                        if(hashMap == null) return;
                        //dateList.clear();
                        List<Boolean> dateList = new ArrayList<>();
                        for(String str : (Set<String>) hashMap.keySet()) {
                            if (str.equals("description") || str.equals("reason") || str.equals("days") || str.equals("startDate") || str.equals("status")) continue;
                            list.add(str); //str is a date that the event occurred
                            HashMap hashMap1 = (HashMap) hashMap.get(str);
                            for(String date : (Set<String>) hashMap1.keySet()){
                                if(date.equals("done")) {
                                    dateList.add((Boolean) hashMap1.get("done"));
                                    break;
                                }
                            }
                        }
                        count = 0;
                        for (int i=0; i< dateList.size(); i++){
                            if (dateList.get(i) == true){
                                count++;
                            }
                        }
                        totalDone = dateList.size();
                        progressBar.setMax(totalDone);
                        progressBar.setProgress(count);
                    }
                }
                else {
                    int x = 6; //will decide on this later
                }
            }
        });

        return view;
    }
}

