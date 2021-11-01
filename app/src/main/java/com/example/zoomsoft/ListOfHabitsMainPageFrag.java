package com.example.zoomsoft;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListOfHabitsMainPageFrag extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        {
            View view = inflater.inflate(R.layout.list_of_habits_main_page_fragment, container, false);

            ListView habitList = view.findViewById(R.id.habit_list);
            ArrayList<Habits> habitDataList = new ArrayList<>();
            ArrayAdapter<Habits> habitAdaptor;

            final String TAG = "Sample";
            FirebaseFirestore db;

            db = FirebaseFirestore.getInstance();
            final CollectionReference collectionReference = db.collection("Habits");
            DocumentReference documentReference = collectionReference.document("a@gmail.com");

            collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
                        FirebaseFirestoreException error) {

                    for(QueryDocumentSnapshot doc: queryDocumentSnapshots)
                    {
                        Log.d(TAG, String.valueOf(doc.getData().get("HabitsList")));
                        List<String> test = (List<String>) doc.getData().get("HabitsList");
                        //List<User> users = document.toObject(UserDocument.class).users;
                        for (int i = 0; i < test.size(); i++){
                            habitDataList.add(new Habits(test.get(i))); // Adding the Habits from FireStore
                        }
                    }
                    //habitAdaptor.notifyDataSetChanged(); // Notifying the adapter to render any new data fetched from the cloud
                }
            });

            habitAdaptor = new HabitCustomList(this.getContext(), habitDataList);
            habitList.setAdapter(habitAdaptor);

            return view;

            //return inflater.inflate(R.layout.list_of_habits_main_page_fragment, container, false);
        }


    }

}
