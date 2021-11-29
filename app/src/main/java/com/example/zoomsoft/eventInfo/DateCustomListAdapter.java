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
import java.util.List;

/**
 * An adapter for a list of habit event dates and their completion status.
 */
public class DateCustomListAdapter extends ArrayAdapter<String> {

    private ArrayList<String> dataList;
    private List<Boolean> doneList;
    private Context context;

    public DateCustomListAdapter(Context context, ArrayList<String> dataList, List<Boolean> doneList) {
        super(context, 0, dataList);
        this.context = context;
        this.dataList = dataList;
        this.doneList = doneList;
    }

    //Habit
    //Description
    TextView textView;
    //Date
    //Comment
    //Photo
    //Camera
    //Location

    /**
     * Gets the view that displays the date custom list adapter's data at specified position.
     * @param position The position of the item within the adapter's data set of the item whose view
     *        we want.
     * @param convertView The old view to reuse, if possible.
     * @param parent The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_of_dates_content, parent,false);
        }
        String val = dataList.get(position);
        TextView textView = view.findViewById(R.id.dateContent);
        TextView textView1 = view.findViewById(R.id.done);
        if(!doneList.get(position).booleanValue()) textView1.setText("Not Done");
        textView.setText(val);
        return view;
    }
}
