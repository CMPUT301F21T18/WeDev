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


public class FriendsHabitsArrayAdapter extends  ArrayAdapter<String>{


    public FriendsHabitsArrayAdapter(Context context, ArrayList<String> friendsHabitsArrayList) {
        super(context,R.layout.friends_habits_content,friendsHabitsArrayList);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent ){
        String friendsHabit = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.friends_habits_content,parent,false);

        }
        TextView item = convertView.findViewById(R.id.friends_habit_item);
        item.setText(friendsHabit);
        return convertView;
    }
}


