package com.example.dishdash.calendar.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dishdash.R;
import com.example.dishdash.calendar.presenter.CalendarPresenter;
import com.example.dishdash.favourite.presenter.FavouritePresenter;
import com.example.dishdash.favourite.view.FavFoodAdapter;
import com.example.dishdash.favourite.view.OnFavClickListener;
import com.example.dishdash.homepage.view.HomePageActivity;
import com.example.dishdash.mealditalies.view.MealFragment;
import com.example.dishdash.model.response.Converter;
import com.example.dishdash.model.response.Food;
import com.example.dishdash.model.response.FoodPlan;

import java.util.List;

public class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.ViewHolder>{

    private final Context context;
    private final OnCalendarClickListener listener;
    private List<FoodPlan> values;
    private static final String TAG = "PlannedAdapter";

    public CalenderAdapter(Context context, List<FoodPlan> values, OnCalendarClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.values = values;
    }

    public void setList(List<FoodPlan> updatedProducts) {
        this.values = updatedProducts;
    }
    @NonNull
    @Override
    public CalenderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
        View v = inflater.inflate(R.layout.test3, recyclerView, false);
        CalenderAdapter.ViewHolder vh = new CalenderAdapter.ViewHolder(v);
        Log.i(TAG, "===== ViewHolder Created ====");
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CalenderAdapter.ViewHolder holder, int position) {
        FoodPlan food =values.get(position);

        Glide.with(context).load(food.getMealThumbnail())
                .apply(new RequestOptions().override(200, 200)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground))
                .into(holder.imageView);
        holder.txtTitle.setText(food.getMealName());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Food food1 = Converter.convertToFoodClass(food);
                MealFragment mealFragment=MealFragment.getInstance(food1);

                ((HomePageActivity) context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,mealFragment)
                        .addToBackStack(null).commit();
            }
            });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Food food1 = Converter.convertToFoodClass(food);
                MealFragment mealFragment=MealFragment.getInstance(food1);

                ((HomePageActivity) context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,mealFragment)
                        .addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return values.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView txtTitle;
        private Button btnRemoveFromFav;
        private View layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout=itemView;
            txtTitle=itemView.findViewById(R.id.textView);
            imageView=itemView.findViewById(R.id.imgbtn);
//            btnRemoveFromFav=itemView.findViewById(R.id.remove_fav_button);
        }
    }
}
