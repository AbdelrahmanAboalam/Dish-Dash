package com.example.dishdash.favourite.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dishdash.R;
import com.example.dishdash.homepage.view.HomePageActivity;
import com.example.dishdash.mealditalies.view.MealFragment;
import com.example.dishdash.model.response.Food;

import java.util.List;

public class FavFoodAdapter extends RecyclerView.Adapter<FavFoodAdapter.ViewHolder> {

    private final Context context;
    private final OnFavClickListener listener;
    private List<Food> values;
    private static final String TAG = "FavouriteAdapter";

    public FavFoodAdapter(Context context, List<Food> values, OnFavClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.values = values;
    }

    public void setList(List<Food> updatedProducts) {
        this.values = updatedProducts;
    }


    @NonNull
    @Override
    public FavFoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
        View v = inflater.inflate(R.layout.meal, recyclerView, false);
        FavFoodAdapter.ViewHolder vh = new FavFoodAdapter.ViewHolder(v);
        Log.i(TAG, "===== ViewHolder Created ====");
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull FavFoodAdapter.ViewHolder holder, int position) {
        Food food =values.get(position);

        Glide.with(context).load(food.getMealThumbnail())
                .apply(new RequestOptions().override(200, 200)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground))
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MealFragment mealFragment=MealFragment.getInstance(values.get(position));

                ((HomePageActivity) context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,mealFragment)
                        .addToBackStack(null).commit();
            }
        });
        holder.txtTitle.setText(food.getMealName());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onLayoutClick(food);
            }
        });
        holder.btnRemoveFromFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRemoveFromFavClick(food);
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
            txtTitle=itemView.findViewById(R.id.textView5);
            imageView=itemView.findViewById(R.id.imageView2);
            btnRemoveFromFav=itemView.findViewById(R.id.remove_fav_button);
        }
    }
}
