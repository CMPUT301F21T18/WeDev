package com.example.zoomsoft;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class Profile extends Fragment {
    private Button addFriendButton;
    public static String email = MainPageTabs.email;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        {

            View view = inflater.inflate(R.layout.profile, container, false);

            addFriendButton = view.findViewById(R.id.addFriend);
            addFriendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), AddFriends.class);
                    startActivity(intent);
                }
            });
            return view;
        }
    }
}