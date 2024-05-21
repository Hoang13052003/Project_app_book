package com.example.project_app_book.view;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.project_app_book.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Main extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar actionBar;
    BottomNavigationView bottomNavigationView;
    FrameLayout frameFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        addControls();
//        actionBar = ( androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbarBai1);
//        setSupportActionBar(actionBar);
        addEvents();
    }
    void addControls()
    {
        bottomNavigationView=(BottomNavigationView)
                findViewById(R.id.bottom_nav);
        frameFragment = (FrameLayout) findViewById(R.id.frameFragment);
    }

    void addEvents()
    {
        loadFragment(new FragmentHome());
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                if (item.getItemId() == R.id.btnHome) {
                    loadFragment(new FragmentHome());
                    return true;
                }
                if (item.getItemId() == R.id.btnCompass) {
                    loadFragment(new FragmentHome());
                    return true;
                }
                if (item.getItemId() == R.id.btnUser) {
                    loadFragment(new FragmentHome());
                    return true;
                }
                return false;
            }
        });
    }
    public void loadFragment(Fragment fragment)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        ft.replace(R.id.fragLayoutLoad,fragment);
        ft.commit();
    }
}