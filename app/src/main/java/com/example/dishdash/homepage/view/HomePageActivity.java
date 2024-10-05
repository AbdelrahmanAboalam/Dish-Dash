package com.example.dishdash.homepage.view;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.example.dishdash.NetworkFragment;
import com.example.dishdash.calendar.view.CalenderFragment;
import com.example.dishdash.favourite.view.FavFragment;
import com.example.dishdash.R;
import com.example.dishdash.searchfragment.view.SearchFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;



public class HomePageActivity extends AppCompatActivity  {
    public static final String TAG = "HomeActivity";
    public BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        bottomNavigationView =findViewById(R.id.bottom_navigation);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                    int itemId = item.getItemId();
                    if (itemId==R.id.page_1)
                    {
                        selectedFragment=new HomeFragment();
                    }
                    else if (itemId==R.id.page_2){
                        selectedFragment=new SearchFragment();
                    }
                    else if (itemId==R.id.page_3){
                        selectedFragment=new FavFragment();
                    }
                    else if (itemId==R.id.page_4){
                        selectedFragment=new CalenderFragment();
                    }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .addToBackStack(null)
                            .commit();
                }
                return true;
            }

        });


    }
    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        if (currentFragment instanceof HomeFragment || currentFragment instanceof NetworkFragment) {
            finish();
        }
        else {
            bottomNavigationView.setSelectedItemId(R.id.page_1);
            HomeFragment homeFragment = new HomeFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, homeFragment)
                    .commit();
            super.onBackPressed();
        }
    }
}