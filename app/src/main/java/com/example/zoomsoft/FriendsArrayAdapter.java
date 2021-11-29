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
 * An array adapter class for inflating the view_friend_content page.
 */
public class FriendsArrayAdapter extends ArrayAdapter<String> {

    public FriendsArrayAdapter(Context context, ArrayList<String> friendsArrayList) {
        super(context,R.layout.view_friend_content,friendsArrayList);
    }
    /**
     * Gets the view that displays the friend array adapter's data at specified position.
     * @param position The position of the item within the adapter's data set of the item whose view
     *        we want.
     * @param convertView The old view to reuse, if possible.
     * @param parent The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
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

