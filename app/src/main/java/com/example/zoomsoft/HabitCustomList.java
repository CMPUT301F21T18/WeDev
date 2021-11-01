package com.example.zoomsoft;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class HabitCustomList extends ArrayAdapter<Habits> {

    private ArrayList<Habits> habits;
    Context context;

    public HabitCustomList(Context context, ArrayList<Habits> habits){
        super(context,0, habits);
        this.habits = habits;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_of_habits_content, parent,false);
        }
        Habits habit = habits.get(position); //Gets exact Class entry as reference for its data
        //Creating references to TextViews
        TextView habitName = view.findViewById(R.id.content_habit_name);

        //Adds the data from the entries onto the TextViews
        habitName.setText(habit.getHabitTitle());
        return view;
    }

}
