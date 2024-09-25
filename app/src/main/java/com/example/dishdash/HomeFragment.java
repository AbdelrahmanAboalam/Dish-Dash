package com.example.dishdash;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dishdash.homepage.presenter.HomePresenter;
import com.example.dishdash.homepage.presenter.HomePresenterImpl;
import com.example.dishdash.homepage.view.HomeActivityInterface;
import com.example.dishdash.homepage.view.HomeAdapter;
import com.example.dishdash.model.FoodRepositoryImpl;
import com.example.dishdash.model.response.Food;
import com.example.dishdash.network.FoodRempteDataSourceImpl;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements HomeActivityInterface {
    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    HomePresenter homePresenter;
    LinearLayoutManager linearLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        initUI(view);

        // Set up RecyclerView
        recyclerView.setHasFixedSize(true);
        linearLayout = new LinearLayoutManager(getActivity());
        homeAdapter = new HomeAdapter(getActivity(), new ArrayList<>());
        homePresenter = new HomePresenterImpl(this, FoodRepositoryImpl.getInstance(FoodRempteDataSourceImpl.getInstance()));
        linearLayout.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(homeAdapter);

        // Fetch data
        homePresenter.getRandomFood();

        return view;
    }
    private void initUI(View view) {
        recyclerView = view.findViewById(R.id.recView);
    }

    @Override
    public void showData(List<Food> food) {
        homeAdapter.setList(food);
        homeAdapter.notifyDataSetChanged();

    }

    @Override
    public void showErrMsg(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(error).setTitle("An Error Occurred");
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}