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

public class FriendsArrayAdapter extends ArrayAdapter<String> {

    public FriendsArrayAdapter(Context context, ArrayList<String> friendsArrayList) {
        super(context,R.layout.view_friend_content,friendsArrayList);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String friends = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_friend_content,parent,false);
        }

        TextView item = convertView.findViewById(R.id.friendName);
        item.setText(friends);
        return convertView;

    }
}

