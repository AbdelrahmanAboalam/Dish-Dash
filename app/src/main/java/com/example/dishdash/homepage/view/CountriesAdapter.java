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
import com.example.dishdash.model.response.Country;
import com.example.dishdash.model.response.Food;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.ViewHolder>{

    private static final String TAG = "RecyclerView2";
    private final Context context;
    private List<Country> allcategoryList;
    HomePresenter presenter;
    OnClickListener listener;
    private Map<String,String> country =new HashMap<>();
    private boolean pos=true;
    private TextView txtCountries;





//    private OnFoodClickListener listener;


    public CountriesAdapter(Context context, List<Country> categoryList,OnClickListener listener , TextView txtCountries) {
        this.context = context;
        this.allcategoryList = categoryList;
        this.listener = listener;
        this.txtCountries=txtCountries;
        setMapp();
    }
    public void setList(List<Country> categoryList){
        this.allcategoryList = categoryList;
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
    public CountriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
        View v = inflater.inflate(R.layout.circle, recyclerView, false);
        CountriesAdapter.ViewHolder vh = new CountriesAdapter.ViewHolder(v);
        Log.i(TAG, "===== onCreateViewHolder =====");
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesAdapter.ViewHolder holder, int position) {
        if(position==0 && pos==true)
        {
            listener.onCountryClick(allcategoryList.get(position).getArea());
            txtCountries.setText(allcategoryList.get(position).getArea()+" Meals");
            pos=false;
        }
        Glide.with(context).load(getFlagUrl(allcategoryList.get(position).getArea()))
                .apply(new RequestOptions().circleCrop()
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground))
                .into(holder.img);
        holder.txtView.setText(allcategoryList.get(position).getArea());
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCountryClick(allcategoryList.get(position).getArea());
                txtCountries.setText(allcategoryList.get(position).getArea()+" Meals");
            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCountryClick(allcategoryList.get(position).getArea());
                txtCountries.setText(allcategoryList.get(position).getArea()+" Meals");
            }
        });

    }

    @Override
    public int getItemCount() {
        return allcategoryList.size();
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
