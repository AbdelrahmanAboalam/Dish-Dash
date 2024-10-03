package com.example.dishdash.homepage.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dishdash.R;
import com.example.dishdash.homepage.presenter.HomePresenter;
import com.example.dishdash.model.response.Food;

import java.util.List;

public class AllCountriesAdapter extends RecyclerView.Adapter<AllCountriesAdapter.ViewHolder> {

private static final String TAG = "RecyclerView2";
private final Context context;
private List<Food> allCountriesList;
HomePresenter presenter;
OnClickListener listener;




public AllCountriesAdapter(Context context, List<Food> countryList,OnClickListener listener) {
    this.context = context;
    this.allCountriesList = countryList;
    this.listener = listener;
}

public void setList(List<Food> categoryList){
    this.allCountriesList = categoryList;
}

public class ViewHolder extends RecyclerView.ViewHolder {
    private ImageView img;
    private TextView txtView;
    public ConstraintLayout constraintLayout;
    public View layout;

    public ViewHolder(@NonNull View v) {
        super(v);
        layout = v;
        img = v.findViewById(R.id.imgbtn);
        txtView = v.findViewById(R.id.textView);
        constraintLayout = v.findViewById(R.id.main);
    }
}

@NonNull
@Override
public AllCountriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
    View v = inflater.inflate(R.layout.test2, recyclerView, false);
    AllCountriesAdapter.ViewHolder vh = new AllCountriesAdapter.ViewHolder(v);
    Log.i(TAG, "===== onCreateViewHolder =====");
    return vh;
}

@Override
public void onBindViewHolder(@NonNull AllCountriesAdapter.ViewHolder holder, int position) {
    Glide.with(context).load(allCountriesList.get(position).getMealThumbnail())
            .apply(new RequestOptions().override(200,200)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground))
            .into(holder.img);
    holder.txtView.setText(allCountriesList.get(position).getMealName());
    holder.img.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            listener.onFoodClick(allCountriesList.get(position).getMealId());
        }
    });
    holder.layout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            listener.onFoodClick(allCountriesList.get(position).getMealId());
        }
    });

}

@Override
public int getItemCount() {
    return allCountriesList.size();
}

}
