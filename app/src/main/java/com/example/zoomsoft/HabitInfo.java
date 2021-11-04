package com.example.zoomsoft;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.zoomsoft.databinding.ActivityHabitInfoBinding;
import com.example.zoomsoft.ui.main.SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class HabitInfo extends AppCompatActivity {

    private ActivityHabitInfoBinding infoBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        infoBinding = ActivityHabitInfoBinding.inflate(getLayoutInflater());
        setContentView(infoBinding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = infoBinding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = infoBinding.habitInfoTabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton edit_fab = infoBinding.fabEdit;
        FloatingActionButton delete_fab = infoBinding.fabDelete;


        edit_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        delete_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}