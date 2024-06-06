package com.example.project_app_book.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_app_book.R;
import com.example.project_app_book.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private User loggedInUser;

    androidx.appcompat.widget.Toolbar actionBar;
    BottomNavigationView bottomNavigationView;
    FrameLayout frameFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        Intent intent = getIntent();
        loggedInUser = (User) intent.getSerializableExtra("loggedInUser");
        addEvents();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack(); // Pop the back stack to go to the previous fragment
        } else {
            super.onBackPressed(); // Otherwise, perform the default back action
        }
    }
    void addControls()
    {
        bottomNavigationView=(BottomNavigationView)
                findViewById(R.id.bottom_nav);
        frameFragment = (FrameLayout) findViewById(R.id.frameFragment);
    }

    void addEvents() {
        loadFragment(new FragmentHome());
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.btnHome) {
                    selectedFragment = new FragmentHome();
                } else if (item.getItemId() == R.id.btnCompass) {
                    selectedFragment = new FragmentHome();
                } else if (item.getItemId() == R.id.btnUser) {
                    selectedFragment = new FragmentUser();
                    if (selectedFragment != null) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("loggedInUser", loggedInUser);
                        selectedFragment.setArguments(bundle);
                    }
                }

                if (selectedFragment != null) {
                    loadFragment(selectedFragment);
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