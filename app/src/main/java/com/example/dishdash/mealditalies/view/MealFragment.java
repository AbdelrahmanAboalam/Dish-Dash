package com.example.dishdash.mealditalies.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dishdash.R;
import com.example.dishdash.db.FoodLocalDataSourceImp;
import com.example.dishdash.model.FoodRepositoryImpl;
import com.example.dishdash.model.response.Converter;
import com.example.dishdash.model.response.Food;
import com.example.dishdash.model.response.FoodPlan;
import com.example.dishdash.model.response.Ingredient;
import com.example.dishdash.network.FoodRempteDataSourceImpl;
import com.example.dishdash.searchfragment.presenter.SearchPresenter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


public class MealFragment extends Fragment implements OnFoodClickListener ,MealView{
    private static final String ARG_FOOD_NAME = "food_name";
    private Food food;
    private FoodPlan foodPlan;

private static final String TAG = "track l moseba";
    FoodRepositoryImpl favpresnter;

    ImageView mealImage;
    TextView mealName;
    TextView instruction;
    WebView webView;
    Button btnFav;
    ImageButton imgbtn;
    private String selectedDate;


    private RecyclerView recyclerView;
    private IngredientsAdapter ingredientsAdapter;
    LinearLayoutManager linearLayout;


    public static MealFragment getInstance(Food food){
        MealFragment fragment =new MealFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_FOOD_NAME, food);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            food = (Food)getArguments().getSerializable(ARG_FOOD_NAME);
            favpresnter=FoodRepositoryImpl.getInstance(FoodRempteDataSourceImpl.getInstance(), FoodLocalDataSourceImp.getInstance(getContext()));
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_meal, container, false);
        initUI(view);


        imgbtn = view.findViewById(R.id.imgbtn);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalenderFragment dialogFragment = new CalenderFragment();
                dialogFragment.setOnDateSelectedListener(selectedDate -> {
                    Toast.makeText(getContext(), "Selected date: " + selectedDate, Toast.LENGTH_SHORT).show();
                    foodPlan = Converter.convertToPlanClass(food, selectedDate);
                    favpresnter.insertFoodPlan(foodPlan);
                });

                dialogFragment.show(getParentFragmentManager(), "calendarDialog");
            }
        });

        ingredientsAdapter = new IngredientsAdapter(getActivity(),new ArrayList<>(),this);
        recyclerView.setHasFixedSize(true);
        linearLayout = new LinearLayoutManager(getActivity());
        linearLayout.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayout);


        btnFav=view.findViewById(R.id.btnFav);
        mealImage = view.findViewById(R.id.imageView);
        mealName = view.findViewById(R.id.textView2);
        instruction = view.findViewById(R.id.txtInstruction);
        //For Web
        webView = view.findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        List<Ingredient> ingredientPojos = getIngList(food);

        ingredientsAdapter.setList2(ingredientPojos);
        recyclerView.setAdapter(ingredientsAdapter);
        ingredientsAdapter.notifyDataSetChanged();

        Glide.with(this).load(food.getMealThumbnail()).apply(new RequestOptions().override(200,200)
                .placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground))
                .into(mealImage);

        //For Parce response
        String[] paragraphs = food.getInstructions().split("\\.");
        StringBuilder formattedText = new StringBuilder();
        for (int i = 0; i < paragraphs.length; i++) {
            if (!paragraphs[i].trim().isEmpty()) {
                // Add numbered bullet points
                formattedText.append((i + 1) + "- " + paragraphs[i].trim() + "\n\n");
            }
        }


        mealName.setText(food.getMealName());
        instruction.setText(formattedText.toString().trim());

        String youtubeEmbedUrl = "https://www.youtube.com/embed/" + getYoutubeVideoId(food.getYoutubeUrl());
        webView.loadUrl(youtubeEmbedUrl);
        if(food.isFav()){
            btnFav.setEnabled(false);
        }
        else{
            btnFav.setEnabled(true);
        }
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Hi from button" + " added to cart", Toast.LENGTH_SHORT).show();
                onAddToFavClick(food);
                btnFav.setEnabled(false);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: "+food);
//        Log.i(TAG, "onViewCreated: " +ingredientPojos );

    }



    private void initUI(View view) {
        recyclerView = view.findViewById(R.id.rec2);
    }
    private String getYoutubeVideoId(String url) {
        String videoId = "";
        if (url != null && url.contains("v=")) {
            String[] parts = url.split("v=");
            videoId = parts[1].split("&")[0];
        }
        return videoId;
    }

    @Override
    public void onLayoutClick(Food food) {
        Toast.makeText(getContext(), food.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddToFavClick(Food food) {
        food.setFav(true);
        favpresnter.insertFood(food);
    }



    @Override
    public void showData(List<Food> food) {

    }

    @Override
    public void showErrMsg(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(error).setTitle("An Error Occurred");
        AlertDialog dialog = builder.create();
        dialog.show();
    }




    private List<Ingredient> getIngList(Food food) {
        List<Ingredient> ingList =  new ArrayList<>();
        for (int i =1 ; i <= 20; i++) {
            try {
                String ingredient = (String) food.getClass().getMethod("getIngredient" + i).invoke(food);
                String measure = (String) food.getClass().getMethod("getMeasure" + i).invoke(food);
                if (ingredient != null && !ingredient.isEmpty() && measure != null && !measure.isEmpty()) {
                    String imageUrl = "https://www.themealdb.com/images/ingredients/" + ingredient + ".png";
                    ingList.add(new Ingredient(ingredient, measure, imageUrl));
                    Log.i(TAG, "getIngList: " + ingList);
                }
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e )
            {
                Log.i(TAG, "u in catch RUN ");
                // Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        return ingList;
    }
}
