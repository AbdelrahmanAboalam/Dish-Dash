package com.example.dishdash.searchfragment.view;

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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dishdash.R;
import com.example.dishdash.model.response.Category;
import com.example.dishdash.model.response.Country;
import com.example.dishdash.model.response.Food;
import com.example.dishdash.model.response.Ingred;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SerAdapter extends RecyclerView.Adapter<SerAdapter.ViewHolder> {

    private final Context context;
    private final OnSearchClickListener listener;
    private List<Food> values;
    private List<Category> values2;
    private List<Country> values3;
    private List<Category> categoryList ;
    private static final String TAG = "FavouriteAdapter";
    public boolean byId=false;
    private int listType =0;
    private Map<String,String> country =new HashMap<>();
    private List<Ingred> ingredients;



    public SerAdapter(Context context, List<Food> values, List<Category> values2, OnSearchClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.values = values;
        this.values2=values2;
        this.categoryList = new ArrayList<>();
        this.ingredients=new ArrayList<>();
        setMapp();
    }



    public void setList(List<Food> updatedProducts) {
        listType=0;
        this.values = updatedProducts;
    }
    public void setList2(List<Category>updatedProducts){
        listType=1;
        this.values2 = updatedProducts;
    }
    public void setList3(List<Country>updatedProducts){
        listType=2;
        this.values3 = updatedProducts;
    }
    public void setList4(List<Ingred>updatedProducts){
        listType=3;
        this.ingredients = updatedProducts;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
        View v = inflater.inflate(R.layout.test3, recyclerView, false);
        ViewHolder vh = new ViewHolder(v);
        Log.i(TAG, "===== ViewHolder Created ====");
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (listType==0) {
            Food food = values.get(position);


            Glide.with(context).load(food.getMealThumbnail())
                    .apply(new RequestOptions().circleCrop()
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .error(R.drawable.ic_launcher_foreground))
                    .into(holder.imageView);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onMealClick(food);
                    byId = true;
                }
            });
            holder.txtTitle.setText(food.getMealName());
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onMealClick(food);
                    byId = true;
                }
            });
        }
        else if(listType==1) {
            Category food = values2.get(position);


            Glide.with(context).load(food.getStrCategoryThumb())
                    .apply(new RequestOptions().circleCrop()
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .error(R.drawable.ic_launcher_foreground))
                    .into(holder.imageView);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onCategoryClick(food.getStrCategory());

                }
            });
            holder.txtTitle.setText(food.getStrCategory());
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onCategoryClick(food.getStrCategory());

                }
            });
        }
            else if(listType==2){
            Country food = values3.get(position);

            Glide.with(context).load(getFlagUrl(food.getArea()))
                    .apply(new RequestOptions().circleCrop()
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .error(R.drawable.ic_launcher_foreground))
                    .into(holder.imageView);
            holder.txtTitle.setText(food.getArea());
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onCountryCkick(food.getArea());

                }
            });
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onCountryCkick(food.getArea());
                }
            });
            }
            else if(listType==3){
                Ingred food = ingredients.get(position);
                String URL = "https://www.themealdb.com/images/ingredients/" + food.getStrIngredient() + ".png";
            Glide.with(context).load(URL)
                    .apply(new RequestOptions().circleCrop()
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .error(R.drawable.ic_launcher_foreground))
                    .into(holder.imageView);
            holder.txtTitle.setText(food.getStrIngredient());
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onIngredientClick(food.getStrIngredient());
                }
            });
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onIngredientClick(food.getStrIngredient());
                }
            });
        }
        }



    @Override
    public int getItemCount() {
        if (listType == 0) {
            if (values == null) {
                return 0;
            }
            return values.size();
        } else if (listType == 1) {
            if (values2 == null) {
                return 0;
            }
            return values2.size();
        } else if (listType == 2) {
            if (values3 == null) {
                return 0;
            }
            return values3.size();
        } else if (listType == 3) {
            if (ingredients == null) {
                return 0;
            }
            return ingredients.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView txtTitle;

        private View layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout=itemView;
            txtTitle=itemView.findViewById(R.id.textView);
            imageView=itemView.findViewById(R.id.imgbtn);
        }
    }

    private void setMapp(){
        country.put("Canadian", "ca");
        country.put("American", "us");
        country.put("British", "gb");
        country.put("Chinese", "cn");
        country.put("Croatian", "hr");
        country.put("Dutch", "nl");
        country.put("Egyptian", "eg");
        country.put("Filipino", "ph");
        country.put("French", "fr");
        country.put("Greek", "gr");
        country.put("Indian", "in");
        country.put("Irish", "ie");
        country.put("Italian", "it");
        country.put("Jamaican", "jm");
        country.put("Japanese", "jp");
        country.put("Kenyan", "ke");
        country.put("Malaysian", "my");
        country.put("Mexican", "mx");
        country.put("Moroccan", "ma");
        country.put("Polish", "pl");
        country.put("Portuguese", "pt");
        country.put("Russian", "ru");
        country.put("Spanish", "es");
        country.put("Thai", "th");
        country.put("Tunisian", "tn");
        country.put("Turkish", "tr");
        country.put("Ukrainian", "ua");
        country.put("Vietnamese", "vn");
    }

    private String getFlagUrl(String areaName) {
        String countryCode = country.get(areaName);
        if (countryCode != null) {
            return "https://flagcdn.com/160x120/" + countryCode + ".png";
        } else {
            return "https://flagcdn.com/160x120/un.png";
        }
    }
}
