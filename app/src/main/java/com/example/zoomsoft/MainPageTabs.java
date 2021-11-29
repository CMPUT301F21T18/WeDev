//Tabbed Activity
//https://www.youtube.com/watch?v=h4HwU_ENXYM&ab_channel=CodinginFlow

package com.example.zoomsoft;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.zoomsoft.databinding.ActivityMainPageTabsBinding;
import com.example.zoomsoft.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

/**
 * MainPageTabs holds tab layout information
 */
public class MainPageTabs extends AppCompatActivity {

    private ActivityMainPageTabsBinding binding;
    public static String email;
    @Override
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainPageTabsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        email = intent.getStringExtra(MainActivity.EXTRA_MESSAGE + "email");
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
    }
}