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

public class ReceivedRequestsArrayAdapter extends ArrayAdapter<String> {

    public ReceivedRequestsArrayAdapter(Context context, ArrayList<String> receivedRequestsArrayList) {
        super(context,R.layout.view_received_request_content,receivedRequestsArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String requestfriends = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_received_request_content,parent,false);
        }

        TextView item = convertView.findViewById(R.id.requestName);
        item.setText(requestfriends);
        return convertView;
    }


}