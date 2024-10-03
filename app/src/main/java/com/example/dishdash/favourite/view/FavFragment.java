package com.example.dishdash.favourite.view;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dishdash.R;
import com.example.dishdash.db.FoodLocalDataSourceImp;
import com.example.dishdash.favourite.presenter.FavouritePresenter;
import com.example.dishdash.favourite.presenter.FavouritePresenterImp;
import com.example.dishdash.model.FoodRepositoryImpl;
import com.example.dishdash.model.response.Food;
import com.example.dishdash.network.FoodRempteDataSourceImpl;

import java.util.ArrayList;
import java.util.List;


public class FavFragment extends Fragment implements OnFavClickListener,FavView {


    FavouritePresenter favouritePresenter;
    private RecyclerView recyclerViewFav;
    private FavFoodAdapter favProductAdapter;
    LinearLayoutManager layoutManager;

    TextView title;
    LiveData<List<Food>> favFood;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fav, container, false);

        title=getActivity().findViewById(R.id.fragment_title);
        title.setText("Favourite Page");

        recyclerViewFav=view.findViewById(R.id.recFav);
        favouritePresenter =new FavouritePresenterImp( this, FoodRepositoryImpl.getInstance(FoodRempteDataSourceImpl.getInstance(),
                FoodLocalDataSourceImp.getInstance(getContext())));
        favFood=favouritePresenter.getFavFood();

        favProductAdapter=new FavFoodAdapter(getContext(),new ArrayList<Food>(),this);
        recyclerViewFav.setAdapter(favProductAdapter);
        layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewFav.setLayoutManager(layoutManager);

        favFood.observe(getViewLifecycleOwner(), new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> food) {
                favProductAdapter.setList(food);
                favProductAdapter.notifyDataSetChanged();
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Finish this fragment and go back
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }

    @Override
    public void showData(List<Food> foods) {
        favProductAdapter.setList(foods);
        favProductAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrMsg(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(error).setTitle("An Error Occurred");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onLayoutClick(Food food) {
        Toast.makeText(getContext(), food.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRemoveFromFavClick(Food food) {
        favouritePresenter.removeFromFav(food);
        favProductAdapter.notifyDataSetChanged();
    }
}