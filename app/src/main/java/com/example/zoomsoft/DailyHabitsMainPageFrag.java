package com.example.zoomsoft;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class DailyHabitsMainPageFrag extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        {
            View view = inflater.inflate(R.layout.daily_habits_main_page_fragment, container, false);

            ListView habitList = view.findViewById(R.id.habit_list);
            ArrayList<Habits> habitDataList = new ArrayList<>();
            ArrayAdapter<Habits> habitAdaptor;

            habitDataList.add(new Habits("Walk"));
            habitDataList.add(new Habits("Meditate"));

            habitAdaptor = new HabitCustomList(this.getContext(), habitDataList);
            habitList.setAdapter(habitAdaptor);

            return view;

            //return inflater.inflate(R.layout.list_of_habits_main_page_fragment, container, false);
        }

    }
}
