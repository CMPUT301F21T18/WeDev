/*The main activity that sets up the tab display
  It is called when a user logs in or registers
 */
package com.example.zoomsoft;

import android.content.Intent;
import android.os.Bundle;

import com.example.zoomsoft.eventInfo.HabitInfo;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.zoomsoft.ui.main.SectionsPagerAdapter;
import com.example.zoomsoft.databinding.ActivityMainPageTabsBinding;

public class MainPageTabs extends AppCompatActivity {

    private ActivityMainPageTabsBinding binding;
    public static String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainPageTabsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        email = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
    }
}