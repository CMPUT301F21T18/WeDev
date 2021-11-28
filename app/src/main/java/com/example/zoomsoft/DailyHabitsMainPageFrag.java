/*Displays the user's habits needed to be performed during the current day (Currently still shows all habits)
  Indicates whether the habit has been done today or not (In progress)
  Allows user to click on a habit and view its information (In progress)
 */
package com.example.zoomsoft;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.zoomsoft.eventInfo.HabitInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DailyHabitsMainPageFrag extends Fragment {

    ArrayList<String> habitDataList = new ArrayList<>();
    ArrayAdapter habitAdaptor;
    ListView habitList;
    public static String email = MainPageTabs.email;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.daily_habits_main_page_fragment, container, false);
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        habitList = view.findViewById(R.id.habit_list);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        TextView textView = view.findViewById(R.id.todayDate);
        textView.setText(formattedDate);

        MainPageFirebase mainPageFirebase = new MainPageFirebase();
        mainPageFirebase.getDailyHabits(day, new MainPageFirebase.MainPageInterface() {
            @Override
            public void getHabitInterface(ArrayList<String> habitArrayList) {

            }

            @Override
            public void getAllHabitsForToday(ArrayList<String> habitsToday) {
                habitAdaptor = new HabitCustomList(getContext(), habitsToday);
                habitList.setAdapter(habitAdaptor);
            }
        });
        habitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //if done, then add the events for the onClick here

            }
        });










        //snapshot added here
        mainPageFirebase.rootRef.collection("Events").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                mainPageFirebase.getDailyHabits(day, new MainPageFirebase.MainPageInterface() {
                    @Override
                    public void getHabitInterface(ArrayList<String> habitArrayList) {

                    }

                    @Override
                    public void getAllHabitsForToday(ArrayList<String> habitsToday) {
                        if(getContext() != null) {
                            habitList = view.findViewById(R.id.habit_list);
                            habitAdaptor = new HabitCustomList(getContext(), habitsToday);
                            habitList.setAdapter(habitAdaptor);
                        }
                    }
                });
            }
        });
        return view;
            //return inflater.inflate(R.layout.list_of_habits_main_page_fragment, container, false);
    }
    
}
