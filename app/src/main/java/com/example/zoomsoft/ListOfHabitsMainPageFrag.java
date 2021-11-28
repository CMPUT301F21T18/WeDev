package com.example.zoomsoft;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.zoomsoft.eventInfo.HabitInfo;
import com.example.zoomsoft.eventInfo.HabitInfoDisplay;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ListOfHabitsMainPageFrag extends Fragment {

    private String TAG = "SAMPLE";
    ArrayAdapter habitAdaptor;
    FloatingActionButton addHabitButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_of_habits_main_page_fragment, container, false);

        ListView habitList = view.findViewById(R.id.habit_list);

        MainPageFirebase mainPageFirebase = new MainPageFirebase();
        mainPageFirebase.getListOfHabits(new MainPageFirebase.MainPageInterface() {
            @Override
            public void getHabitInterface(ArrayList<String> habitArrayList) {
                if(getActivity() != null) {
                    habitAdaptor = new HabitCustomList(getContext(), habitArrayList);
                    habitList.setAdapter(habitAdaptor);
                }
            }

            @Override
            public void getAllHabitsForToday(ArrayList<String> habitsToday) {

            }
        });

        habitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String clickedHabit = (String) habitList.getItemAtPosition(i); //Getting name of habit
                Intent intent = new Intent(getActivity(), HabitInfo.class); //Adding habit name to intent
                intent.putExtra(MainActivity.EXTRA_MESSAGE + "1", clickedHabit);
                startActivity(intent);
            }
        });

        addHabitButton = view.findViewById(R.id.add_habit_button);
        addHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddHabit.class);
                startActivity(intent);
            }
        });

        mainPageFirebase.rootRef.collection("Events").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                mainPageFirebase.getListOfHabits(new MainPageFirebase.MainPageInterface() {
                    @Override
                    public void getHabitInterface(ArrayList<String> habitArrayList) {
                        if(getActivity() != null) {
                            ArrayAdapter habitAdaptor = new HabitCustomList(getContext(), habitArrayList);
                            habitList.setAdapter(habitAdaptor);
                        }
                    }
                    @Override
                    public void getAllHabitsForToday(ArrayList<String> habitsToday) {

                    }
                });
            }
        });
        return view;
    }
}