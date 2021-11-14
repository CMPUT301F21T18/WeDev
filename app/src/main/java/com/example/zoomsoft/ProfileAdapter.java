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

public class ProfileAdapter extends ArrayAdapter<Profile> {
    public ProfileAdapter(Context context, ArrayList<Profile> userArrayList) {
        super(context, R.layout.profile_content,userArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Profile profileItem = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.profile_content,parent,false);
        }


        TextView item = convertView.findViewById(R.id.profile_item);
        item.setText(profileItem.item);

        return convertView;
    }


}