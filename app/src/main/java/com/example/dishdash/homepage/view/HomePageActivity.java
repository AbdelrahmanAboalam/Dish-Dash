package com.example.dishdash.homepage.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dishdash.R;
import com.example.dishdash.homepage.presenter.HomePresenter;
import com.example.dishdash.homepage.presenter.HomePresenterImpl;
import com.example.dishdash.model.FoodRepositoryImpl;
import com.example.dishdash.model.response.Food;
import com.example.dishdash.network.FoodRempteDataSourceImpl;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity implements HomeActivityInterface {
    public static final String TAG = "HomeActivity";
    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    HomePresenter homePresenter;
    LinearLayoutManager linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);

        initUI();

        recyclerView.setHasFixedSize(true);
        linearLayout = new LinearLayoutManager(this);
        homeAdapter = new HomeAdapter(HomePageActivity.this,new ArrayList<>());
        homePresenter = new HomePresenterImpl(this, FoodRepositoryImpl.getInstance(FoodRempteDataSourceImpl.getInstance()));
        linearLayout.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(homeAdapter);
        homePresenter.getRandomFood();
    }

    private void initUI(){
        recyclerView = findViewById(R.id.recView);
    }

    @Override
    public void showData(List<Food> food) {
        homeAdapter.setList(food);
        homeAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrMsg(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(error).setTitle("An Error Occurred");
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}