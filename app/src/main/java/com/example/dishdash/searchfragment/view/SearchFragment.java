package com.example.dishdash.searchfragment.view;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TableLayout;

import com.example.dishdash.R;
import com.example.dishdash.db.FoodLocalDataSourceImp;
import com.example.dishdash.favourite.presenter.FavouritePresenterImp;
import com.example.dishdash.favourite.view.FavFoodAdapter;
import com.example.dishdash.mealditalies.view.MealFragment;
import com.example.dishdash.model.FoodRepositoryImpl;
import com.example.dishdash.model.response.Food;
import com.example.dishdash.network.FoodRempteDataSourceImpl;
import com.example.dishdash.searchfragment.presenter.SearchPresenter;
import com.example.dishdash.searchfragment.presenter.SearchPresenterImpl;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements SerView,OnSearchClickListener {

    private SerAdapter serAdapter;
    private SearchPresenter searchPresenter;
    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    int tap=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView=view.findViewById(R.id.recView4);
        TabLayout tableLayout=view.findViewById(R.id.tabLayout);
        SearchView searchView=view.findViewById(R.id.searchView);

        searchPresenter =new SearchPresenterImpl( this, FoodRepositoryImpl.getInstance(FoodRempteDataSourceImpl.getInstance(),
                FoodLocalDataSourceImp.getInstance(getContext())));


        serAdapter=new SerAdapter(getContext(),new ArrayList<Food>(),this);
        layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(serAdapter);

        tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tap=tab.getPosition();

                switch (tap){
                    case 0: searchView.setQueryHint("Please Enter Meal name");
                    break;
                    case 1: searchView.setQueryHint("Please Enter Country name");
                        break;
                    case 2: searchView.setQueryHint("Please Enter Category name");
                        break;
                    case 3: searchView.setQueryHint("Please Enter Ingretient name");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                switch (tap){
                    case 0: searchPresenter.getFoodName(searchView.getQuery().toString());
                        break;
                    case 1: searchPresenter.getFoodByCountry(searchView.getQuery().toString());
                        break;
                    case 2: searchPresenter.getFoodByCategory(searchView.getQuery().toString());
                        break;
                    case 3: searchPresenter.getFoodByIngredient(searchView.getQuery().toString());
                        break;
                }


                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return view;
    }

    @Override
    public void onMealClick(Food food) {
        searchPresenter.getFoodById(food.getMealId());
    }

    @Override
    public void showData(List<Food> food) {
        if(serAdapter.byId){
            MealFragment mealFragment=MealFragment.getInstance(food.get(0));
            FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container,mealFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            serAdapter.byId=false;
        }
        else {
            serAdapter.setList(food);
            serAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showErrMsg(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(error).setTitle("An Error Occurred");
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}