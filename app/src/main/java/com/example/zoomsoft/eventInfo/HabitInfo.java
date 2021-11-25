package com.example.zoomsoft.eventInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.zoomsoft.MainActivity;
import com.example.zoomsoft.MainPageTabs;
import com.example.zoomsoft.databinding.ActivityHabitInfoBinding;
import com.example.zoomsoft.eventInfo.SectionsPagerAdapterEvent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class HabitInfo extends AppCompatActivity {

    private ActivityHabitInfoBinding binding;
    public static String email = MainPageTabs.email;
    public static String clickedHabit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHabitInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        clickedHabit = intent.getStringExtra(MainActivity.EXTRA_MESSAGE+"1");
        SectionsPagerAdapterEvent sectionsPagerAdapterEvent = new SectionsPagerAdapterEvent(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapterEvent);
        TabLayout tabs = binding.habitInfoTabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fabDelete = binding.fabDelete;
        FloatingActionButton fabEdit = binding.fabEdit;

        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HabitInfoFirebase habitInfoFirebase = new HabitInfoFirebase();
                habitInfoFirebase.deleteHabit(clickedHabit);
            }
        });

        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HabitInfoFirebase habitInfoFirebase = new HabitInfoFirebase();
                habitInfoFirebase.deleteHabit(clickedHabit);
            }
        });
    }
}