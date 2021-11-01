package com.example.zoomsoft;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.zoomsoft.databinding.HabitInfoBinding;
import com.example.zoomsoft.ui.main.SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class HabitInfoActivity extends AppCompatActivity {

    private HabitInfoBinding binding;
    TabLayout tabLayout;
    TabItem info, items;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tabLayout = (TabLayout) findViewById(R.id.habit_info_tabs);


        binding = HabitInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        info = (TabItem) findViewById(R.id.info_tab);


        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.habitInfoTabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fabDelete = binding.fabDelete;
        FloatingActionButton fabEdit = binding.fabEdit;



        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}