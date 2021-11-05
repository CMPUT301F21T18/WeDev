/*Displays the user's habits needed to be performed during the current day (Currently still shows all habits)
  Indicates whether the habit has been done today or not (In progress)
  Allows user to click on a habit and view its information (In progress)
 */
package com.example.zoomsoft;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class DailyHabitsMainPageFrag extends Fragment {

    ArrayList<Habits> habitDataList = new ArrayList<>();
    ArrayAdapter habitAdaptor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        {
            View view = inflater.inflate(R.layout.list_of_habits_main_page_fragment, container, false);

            ListView habitList = view.findViewById(R.id.habit_list);
            habitAdaptor = new HabitCustomList(this.getContext(), habitDataList);
            habitList.setAdapter(habitAdaptor);

            FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
            rootRef.collection("Habits").document("a@gmail.com").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            habitDataList.clear();
                            Map<String, Object> map = document.getData();
                            for (Map.Entry<String, Object> entry : map.entrySet()) {
                                if (entry.getKey().equals("HabitsList")) {
                                    Log.d("TAG", entry.getValue().toString());
                                    habitDataList.add(new Habits(entry.getValue().toString()));
                                }
                            }
                            habitAdaptor.notifyDataSetChanged();
                        }
                    }
                }
            });

            return view;

            //return inflater.inflate(R.layout.list_of_habits_main_page_fragment, container, false);

        }


    }
}
