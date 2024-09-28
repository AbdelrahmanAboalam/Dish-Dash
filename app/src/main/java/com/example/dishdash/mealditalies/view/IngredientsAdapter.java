package com.example.dishdash.mealditalies.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dishdash.R;
import com.example.dishdash.model.response.Food;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private static final String TAG = "RecyclerView";
    private final Context context;
    private List<Food> values;
    OnFoodClickListener favListener ;

    public IngredientsAdapter(Context context, List<Food> values,OnFoodClickListener favListener) {
        this.context = context;
        this.values = values;
        this.favListener=favListener;
    }

    public void setList(List<Food> food) {
        this.values = food;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView txtView;
        public ConstraintLayout constraintLayout;
        public View layout;


        public ViewHolder(@NonNull View v) {
            super(v);
            layout = v;
            img = v.findViewById(R.id.imageView2);
            txtView = v.findViewById(R.id.textView5);
            constraintLayout = v.findViewById(R.id.main);
        }
    }

    @NonNull
    @Override
    public IngredientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
        View v = inflater.inflate(R.layout.meal, recyclerView, false);
        IngredientsAdapter.ViewHolder vh = new IngredientsAdapter.ViewHolder(v);
        Log.i(TAG, "===== onCreateViewHolder =====");
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Food food = values.get(position);
        Glide.with(context).load(values.get(position).getMealThumbnail())
                .apply(new RequestOptions().override(200, 200)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground))
                .into(holder.img);

        holder.txtView.setText(values.get(position).getMealName());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favListener.onLayoutClick(food);
            }
        });
    }


    @Override
    public int getItemCount() {
        return values.size();
    }
}