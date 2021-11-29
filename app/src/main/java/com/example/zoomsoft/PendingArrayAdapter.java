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

/**
 * PendingArrayAdapter holds pending info
 */
public class PendingArrayAdapter extends ArrayAdapter<String> {

    public PendingArrayAdapter(Context context, ArrayList<String> pendingRequestsArrayList) {
        super(context,R.layout.view_request_content,pendingRequestsArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String pendingfriends = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_request_content,parent,false);
        }

        TextView item = convertView.findViewById(R.id.pendingRequestName);
        item.setText(pendingfriends);
        return convertView;
    }


}