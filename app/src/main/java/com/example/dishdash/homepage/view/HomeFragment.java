package com.example.dishdash.homepage.view;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dishdash.R;
import com.example.dishdash.db.FoodLocalDataSourceImp;
import com.example.dishdash.homepage.presenter.HomePresenter;
import com.example.dishdash.homepage.presenter.HomePresenterImpl;
import com.example.dishdash.model.FoodRepositoryImpl;
import com.example.dishdash.model.response.Category;
import com.example.dishdash.model.response.Food;
import com.example.dishdash.network.FoodRempteDataSourceImpl;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements HomeActivityInterface,CategoryAdapter.OnCategoryClickListener {
    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    HomePresenter homePresenter;
    LinearLayoutManager linearLayout;


    private RecyclerView recyclerView2;
    private CategoryAdapter categoryAdapter;
    LinearLayoutManager linearLayout2;

    private RecyclerView recyclerView3;
    private AllCategoriesAdapter allCategoriesAdapter;
    LinearLayoutManager layoutManager3;

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
        homePresenter = new HomePresenterImpl(this, FoodRepositoryImpl.getInstance(FoodRempteDataSourceImpl.getInstance(),
                FoodLocalDataSourceImp.getInstance(getContext())));
        linearLayout.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(homeAdapter);

        recyclerView2.setHasFixedSize(true);
        linearLayout2 = new LinearLayoutManager(getActivity());
        categoryAdapter = new CategoryAdapter(getActivity(),new ArrayList<>());
        linearLayout2.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView2.setLayoutManager(linearLayout2);
        recyclerView2.setAdapter(categoryAdapter);

        categoryAdapter.setOnCategoryClickListener(this);

        recyclerView3.setHasFixedSize(true);
        layoutManager3 = new LinearLayoutManager(getActivity());
        allCategoriesAdapter = new AllCategoriesAdapter(getActivity(),new ArrayList<>());
        layoutManager3.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView3.setLayoutManager(layoutManager3);
        recyclerView3.setAdapter(allCategoriesAdapter);




        // Fetch data
        homePresenter.getRandomFood();
        homePresenter.getCategoryFood();
        homePresenter.getFoodByCategory(CategoryAdapter.id);
        return view;
    }
    private void initUI(View view) {
        recyclerView = view.findViewById(R.id.recView);
        recyclerView2=view.findViewById(R.id.recView2);
        recyclerView3=view.findViewById(R.id.recView3);
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

    @Override
    public void showCategoryData(List<Category> meals) {
        categoryAdapter.setList(meals);
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCategoryErrMsg(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(error).setTitle("An Error Occurred");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void showCategoryFood(List<Food> food) {
        allCategoriesAdapter.setList(food);
        allCategoriesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCategoryFoodErrMsg(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(error).setTitle("An Error Occurred");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onCategoryClick(String categoryId) {
        homePresenter.getFoodByCategory(categoryId);
    }
}