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
 * ProfileAdapter array is defined here
 */
public class ProfileAdapter extends ArrayAdapter<Profile> {
    public ProfileAdapter(Context context, ArrayList<Profile> userArrayList) {
        super(context, R.layout.profile_content,userArrayList);
    }
    // Creates the profile page and ad
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
        Profile profileItem = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.profile_content,parent,false);
        }


        TextView item = convertView.findViewById(R.id.profile_item);
        item.setText(profileItem.item);

        return convertView;
    }


}