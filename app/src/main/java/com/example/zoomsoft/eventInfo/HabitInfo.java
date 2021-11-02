package com.example.zoomsoft.eventInfo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.zoomsoft.databinding.ActivityHabitInfoBinding;
import com.example.zoomsoft.eventInfo.SectionsPagerAdapterEvent;
import com.google.android.material.tabs.TabLayout;

public class HabitInfo extends AppCompatActivity {

    private ActivityHabitInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHabitInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapterEvent sectionsPagerAdapterEvent = new SectionsPagerAdapterEvent(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapterEvent);
        TabLayout tabs = binding.habitInfoTabs;
        tabs.setupWithViewPager(viewPager);
//        FloatingActionButton fab = binding.fabDelete;
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }
}