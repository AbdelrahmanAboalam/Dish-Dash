package com.example.dishdash.homepage.view;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;


import com.example.dishdash.CalenderFragment;
import com.example.dishdash.FavFragment;
import com.example.dishdash.HomeFragment;
import com.example.dishdash.R;
import com.example.dishdash.SearchFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;



public class HomePageActivity extends AppCompatActivity  {
    public static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation);
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
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                }
                return true;
            }
        });

    }

    //    private RecyclerView recyclerView;
//    private HomeAdapter homeAdapter;
//    HomePresenter homePresenter;
//    LinearLayoutManager linearLayout;
//    private void initUI(){
//        recyclerView = findViewById(R.id.recView);
//    }

//    @Override
//    public void showData(List<Food> food) {
//        homeAdapter.setList(food);
//        homeAdapter.notifyDataSetChanged();
//    }
//
//    @Override
//    public void showErrMsg(String error) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage(error).setTitle("An Error Occurred");
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }
//initUI();
//
//        recyclerView.setHasFixedSize(true);
//        linearLayout = new LinearLayoutManager(this);
//        homeAdapter = new HomeAdapter(HomePageActivity.this,new ArrayList<>());
//        homePresenter = new HomePresenterImpl(this, FoodRepositoryImpl.getInstance(FoodRempteDataSourceImpl.getInstance()));
//        linearLayout.setOrientation(RecyclerView.VERTICAL);
//        recyclerView.setLayoutManager(linearLayout);
//        recyclerView.setAdapter(homeAdapter);
//        homePresenter.getRandomFood();
}