package com.example.zoomsoft;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.zoomsoft.loginandregister.User;

import java.util.ArrayList;
/**
 * UserCustomList
 */
public class UserCustomList extends ArrayAdapter<User>{
    private ArrayList<User> users;
    private Context context;
    /**
     *
     * @param context
     * @param user
     */
    public UserCustomList(@NonNull Context context, ArrayList<User> user) {
        super(context, 0,user);
        this.users = users;
        this.context = context;
    }
    @NonNull
    @Override
    /**
     *
     * @param position
     * @param convertView
     * @return
     */
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.add_friend_content, parent,false);
        }
        User user = users.get(position);
        TextView userName = view.findViewById(R.id.user_list);
        userName.setText(user.getUsername());
        return view;
    }
}
