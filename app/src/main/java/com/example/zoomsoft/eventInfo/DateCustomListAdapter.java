package com.example.zoomsoft.eventInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.zoomsoft.R;

import java.util.ArrayList;
import java.util.Date;

public class DateCustomListAdapter extends ArrayAdapter<String> {

    private ArrayList<String> dataList;
    private Context context;

    public DateCustomListAdapter(Context context, ArrayList<String> dataList) {
        super(context, 0, dataList);
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_of_dates_content, parent,false);
        }
        String val = dataList.get(position);
        TextView textView = view.findViewById(R.id.dateContent);
        textView.setText(val);
        return view;
    }
}
