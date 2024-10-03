package com.example.dishdash.homepage.view;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dishdash.NetworkFragment;
import com.example.dishdash.R;
import com.example.dishdash.db.FoodLocalDataSourceImp;
import com.example.dishdash.favourite.view.FavFragment;
import com.example.dishdash.homepage.presenter.HomePresenter;
import com.example.dishdash.homepage.presenter.HomePresenterImpl;
import com.example.dishdash.mealditalies.view.MealFragment;
import com.example.dishdash.model.FoodRepositoryImpl;
import com.example.dishdash.model.response.Category;
import com.example.dishdash.model.response.Country;
import com.example.dishdash.model.response.Food;
import com.example.dishdash.network.FoodRempteDataSourceImpl;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements HomeActivityInterface,OnClickListener {
    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    HomePresenter homePresenter;
    LinearLayoutManager linearLayout;

    TextView title;

    private RecyclerView recyclerView2;
    private CategoryAdapter categoryAdapter;
    LinearLayoutManager linearLayout2;

    private RecyclerView recyclerView3;
    private AllCategoriesAdapter allCategoriesAdapter;
    LinearLayoutManager layoutManager3;


    private CountriesAdapter countriesAdapter;
    private RecyclerView recyclerView4;
    LinearLayoutManager layoutManager4;
    TextView txtCountries,txtCategories;

    private AllCountriesAdapter allCountriesAdapter;
    private RecyclerView recyclerView5;
    LinearLayoutManager layoutManager5;

    ProgressBar progressBar;



    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

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

        if (!isNetworkConnected()) {
            // Redirect to NoConnectionFragment
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new NetworkFragment());
            transaction.addToBackStack(null);
            transaction.commit();
            return view;  // Return early to avoid fetching data
        }
        progressBar=view.findViewById(R.id.progressBar);
        txtCountries=view.findViewById(R.id.txtCountries);
        txtCategories=view.findViewById(R.id.txtCategories);

        title=getActivity().findViewById(R.id.fragment_title);
        title.setText("Home Page");

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
        categoryAdapter = new CategoryAdapter(getActivity(),new ArrayList<>(),this,txtCategories);
        linearLayout2.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView2.setLayoutManager(linearLayout2);
        recyclerView2.setAdapter(categoryAdapter);



        recyclerView3.setHasFixedSize(true);
        layoutManager3 = new LinearLayoutManager(getActivity());
        allCategoriesAdapter = new AllCategoriesAdapter(getActivity(),new ArrayList<>(),this);
        layoutManager3.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView3.setLayoutManager(layoutManager3);
        recyclerView3.setAdapter(allCategoriesAdapter);

        recyclerView4.setHasFixedSize(true);
        layoutManager4 = new LinearLayoutManager(getActivity());
        countriesAdapter = new CountriesAdapter(getActivity(),new ArrayList<>(),this,txtCountries);
        layoutManager4.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView4.setLayoutManager(layoutManager4);
        recyclerView4.setAdapter(countriesAdapter);


        recyclerView5.setHasFixedSize(true);
        layoutManager5 = new LinearLayoutManager(getActivity());
        allCountriesAdapter = new AllCountriesAdapter(getActivity(),new ArrayList<>(),this);
        layoutManager5.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView5.setLayoutManager(layoutManager5);
        recyclerView5.setAdapter(allCountriesAdapter);


        // Fetch data
        homePresenter.getRandomFood();
        homePresenter.getCategoryFood();
//        homePresenter.getFoodByCategory(CategoryAdapter.id);
        homePresenter.getCountries();
        return view;
    }
    private void initUI(View view) {
        recyclerView = view.findViewById(R.id.recView);
        recyclerView2=view.findViewById(R.id.recView2);
        recyclerView3=view.findViewById(R.id.recView3);
        recyclerView4=view.findViewById(R.id.recView4);
        recyclerView5=view.findViewById(R.id.recView5);
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
    public void showFood(List<Food> food) {
        MealFragment mealFragment=MealFragment.getInstance(food.get(0));
        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,mealFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void showCountry(List<Country> food) {
        countriesAdapter.setList(food);
        countriesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showFoodFromCountry(List<Food> food) {
        allCountriesAdapter.setList(food);
        progressBar.setVisibility(View.GONE);
        allCountriesAdapter.notifyDataSetChanged();
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

    @Override
    public void onCountryClick(String countryId) {
        homePresenter.getFoodByCountry(countryId);
    }

    @Override
    public void onFoodClick(String categoryId) {
        homePresenter.getFoodById(categoryId);
    }
}