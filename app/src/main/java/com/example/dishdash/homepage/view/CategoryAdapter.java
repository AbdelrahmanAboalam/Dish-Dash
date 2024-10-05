package com.example.dishdash.homepage.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dishdash.R;
import com.example.dishdash.model.response.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private static final String TAG = "RecyclerView2";
    private final Context context;
    private List<Category> categoryList;
    public  static String id = "Beef";
    private boolean pos =true;
    TextView txtCategories;
OnClickListener listener;

    public CategoryAdapter(Context context, List<Category> categoryList,OnClickListener listener,TextView txtCategories) {
        this.context = context;
        this.categoryList = categoryList;
        this.listener = listener;
        this.txtCategories=txtCategories;
    }
    public void setList(List<Category> categoryList){
        this.categoryList = categoryList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
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
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
        View v = inflater.inflate(R.layout.circle, recyclerView, false);
        CategoryAdapter.ViewHolder vh = new CategoryAdapter.ViewHolder(v);
        Log.i(TAG, "===== onCreateViewHolder =====");
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (position==0 && pos==true){
            listener.onCategoryClick(categoryList.get(position).getStrCategory());
            txtCategories.setText(categoryList.get(position).getStrCategory()+" Meals");
        }
        Glide.with(context).load(categoryList.get(position).getStrCategoryThumb())
                .apply(new RequestOptions().circleCrop()
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground))
                .into(holder.img);
        holder.txtView.setText(categoryList.get(position).getStrCategory());
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onCategoryClick(categoryList.get(position).getStrCategory());
                    txtCategories.setText(categoryList.get(position).getStrCategory()+" Meals");

                }
            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onCategoryClick(categoryList.get(position).getStrCategory());
                    txtCategories.setText(categoryList.get(position).getStrCategory()+" Meals");

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
