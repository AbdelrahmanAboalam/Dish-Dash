package com.example.dishdash.calendar.view;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dishdash.R;
import com.example.dishdash.calendar.presenter.CalendarPresenter;
import com.example.dishdash.calendar.presenter.CalendarPresenterImpl;
import com.example.dishdash.db.FoodLocalDataSourceImp;
import com.example.dishdash.favourite.presenter.FavouritePresenter;
import com.example.dishdash.favourite.presenter.FavouritePresenterImp;
import com.example.dishdash.favourite.view.FavFoodAdapter;
import com.example.dishdash.homepage.view.HomePageActivity;
import com.example.dishdash.mealditalies.view.MealFragment;
import com.example.dishdash.model.FoodRepositoryImpl;
import com.example.dishdash.model.response.Converter;
import com.example.dishdash.model.response.Food;
import com.example.dishdash.model.response.FoodPlan;
import com.example.dishdash.network.FoodRempteDataSourceImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CalenderFragment extends Fragment implements CalendarView,OnCalendarClickListener {

    CalendarPresenter calendarPresenter;
    private RecyclerView recyclerViewFav;
    private CalenderAdapter adapter;
    LinearLayoutManager layoutManager;

    TextView title;

    LiveData<List<FoodPlan>> plannedFood;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calender, container, false);

        title=getActivity().findViewById(R.id.fragment_title);
        title.setText("Planned Meals");

        DatePicker datePicker = view.findViewById(R.id.datePicker2);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        recyclerViewFav=view.findViewById(R.id.recPlaned);
        calendarPresenter =new CalendarPresenterImpl( this, FoodRepositoryImpl.getInstance(FoodRempteDataSourceImpl.getInstance(),
                FoodLocalDataSourceImp.getInstance(getContext())));

        adapter=new CalenderAdapter(getContext(),new ArrayList<FoodPlan>(),this);
        recyclerViewFav.setAdapter(adapter);
        layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewFav.setLayoutManager(layoutManager);


        try {
            ViewGroup ll = (ViewGroup) ((ViewGroup) datePicker.getChildAt(0)).getChildAt(0);
            if (ll != null) {
                ll.getChildAt(0).setVisibility(View.GONE); // Hide the top part
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        DatePicker.OnDateChangedListener dateChangedListener = new DatePicker.OnDateChangedListener() {

            @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;

                plannedFood = calendarPresenter.getPlanedFood(selectedDate);
                plannedFood.observe(getViewLifecycleOwner(), new Observer<List<FoodPlan>>() {
                    @Override
                    public void onChanged(List<FoodPlan> foodPlans) {
                        adapter.setList(foodPlans);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        };
        datePicker.init(year, month, day, dateChangedListener);

        dateChangedListener.onDateChanged(datePicker, year, month, day);
        return view;
    }

    @Override
    public void showData(List<FoodPlan> foodPlans) {
        adapter.setList(foodPlans);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showErrMsg(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(error).setTitle("An Error Occurred");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onLayoutClick(FoodPlan foodPlan) {
        Food food = Converter.convertToFoodClass(foodPlan);
        MealFragment mealFragment=MealFragment.getInstance(food);
        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,mealFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onAddToFavClick(FoodPlan foodPlan) {
        Food food = Converter.convertToFoodClass(foodPlan);
        calendarPresenter.updateFoodPlanById(foodPlan.getMealId(),true);
        calendarPresenter.updateFoodById(foodPlan.getMealId(),true);
        calendarPresenter.addtoFav(food);
        Toast.makeText(getContext(), foodPlan.getMealName()+ " Added to Favourites", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRemoveFromFavClick(FoodPlan foodPlan) {
        Food food = Converter.convertToFoodClass(foodPlan);
        calendarPresenter.updateFoodPlanById(foodPlan.getMealId(),false);
        calendarPresenter.updateFoodById(foodPlan.getMealId(),false);
        calendarPresenter.removeFromFav(food);
        Toast.makeText(getContext(), foodPlan.getMealName()+ " Removed from Favourites", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRemovFromPlanClick(FoodPlan foodPlan) {
        calendarPresenter.removeFromPlan(foodPlan);
        adapter.notifyDataSetChanged();
        Toast.makeText(getContext(), foodPlan.getMealName()+ " Removed from Plan", Toast.LENGTH_SHORT).show();
    }
}