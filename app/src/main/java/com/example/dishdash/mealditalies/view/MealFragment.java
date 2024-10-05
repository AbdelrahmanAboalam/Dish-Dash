package com.example.dishdash.mealditalies.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class MealFragment extends Fragment implements OnFoodClickListener, MealView {
    // Constants
    private static final String ARG_FOOD_NAME = "food_name";
    private static final String TAG = "track l moseba";

    // References
    private Food food;
    private FoodPlan foodPlan;
    private FoodRepositoryImpl favPresenter;

    // UI
    private TextView title;
    private ImageView mealImage;
    private TextView mealName;
    private TextView instruction;
    private WebView webView;
    private ImageButton btnFav;
    private ImageButton imgbtn;
    private RecyclerView recyclerView;
    private IngredientsAdapter ingredientsAdapter;
    private LinearLayoutManager linearLayout;

    // Method to receive meal
    public static MealFragment getInstance(Food food) {
        MealFragment fragment = new MealFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_FOOD_NAME, food);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            food = (Food) getArguments().getSerializable(ARG_FOOD_NAME);
            favPresenter = FoodRepositoryImpl.getInstance(FoodRempteDataSourceImpl.getInstance(), FoodLocalDataSourceImp.getInstance(getContext()));
            onCheckClick(food);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal, container, false);
        // Initialize UI
        initUI(view);

        // Set the title of the fragment
        title = getActivity().findViewById(R.id.fragment_title);
        title.setText("Meal Details");

        // Set up the calendar dialog for selecting a date
        imgbtn = view.findViewById(R.id.imgbtn);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalenderFragment dialogFragment = new CalenderFragment();
                dialogFragment.setOnDateSelectedListener(MealFragment.this); // Set this fragment as the listener
                dialogFragment.show(getParentFragmentManager(), "calendarDialog");
            }
            });


        // Set up the RecyclerView for ingredients
        ingredientsAdapter = new IngredientsAdapter(getActivity(), new ArrayList<>(), this);
        recyclerView.setHasFixedSize(true);
        linearLayout = new LinearLayoutManager(getActivity());
        linearLayout.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayout);

        // Initialize other UI elements
        btnFav = view.findViewById(R.id.btnFav);
        mealImage = view.findViewById(R.id.imageView);
        mealName = view.findViewById(R.id.textView2);
        instruction = view.findViewById(R.id.txtInstruction);

        // Set up WebView for YouTube video
        webView = view.findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        // Get ingredients and update RecyclerView
        List<Ingredient> ingredientPojos = getIngList(food);
        ingredientsAdapter.setList2(ingredientPojos);
        recyclerView.setAdapter(ingredientsAdapter);
        ingredientsAdapter.notifyDataSetChanged();

        // Load meal image with Glide
        Glide.with(this)
                .load(food.getMealThumbnail())
                .apply(new RequestOptions().override(200, 200)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground))
                .into(mealImage);

        // Format and display instructions
        String formattedInstructions = formatInstructions(food.getInstructions());
        mealName.setText(food.getMealName());
        instruction.setText(formattedInstructions);

        // Load YouTube video
        String youtubeEmbedUrl = "https://www.youtube.com/embed/" + getYoutubeVideoId(food.getYoutubeUrl());
        webView.loadUrl(youtubeEmbedUrl);

        // Set favorite button state
        btnFav.setImageResource(food.isFav() ? R.drawable.heart_filled : R.drawable.heart_outline);
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFavoriteStatus();
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: " + food);
    }

    /****************************************************************************/
    /************************** Initialize UI  **********************************/
    /****************************************************************************/
    private void initUI(View view) {
        recyclerView = view.findViewById(R.id.rec2);
    }

    /****************************************************************************/
    /****************************** To make URL *********************************/
    /****************************************************************************/
    private String getYoutubeVideoId(String url) {
        String videoId = "";
        if (url != null && url.contains("v=")) {
            String[] parts = url.split("v=");
            videoId = parts[1].split("&")[0];
        }
        return videoId;
    }

    /****************************************************************************/
    /***************************** Toggle Favorite ******************************/
    /****************************************************************************/
    private void toggleFavoriteStatus() {
        if (!food.isFav()) {
            Toast.makeText(getContext(), food.getMealName() + " Added To Favourite", Toast.LENGTH_SHORT).show();
            updateFoodPlanbyId(food.getMealId(), true);
            onAddToFavClick(food);
            btnFav.setImageResource(R.drawable.heart_filled);
        } else {
            Toast.makeText(getContext(), food.getMealName() + " Removed From Favourite", Toast.LENGTH_SHORT).show();
            updateFoodPlanbyId(food.getMealId(), false);
            onRemoveFavClick(food);
            btnFav.setImageResource(R.drawable.heart_outline);
        }
    }

    // Format instructions with numbered bullet points
    private String formatInstructions(String instructions) {
        String[] paragraphs = instructions.split("\\.");
        StringBuilder formattedText = new StringBuilder();
        for (int i = 0; i < paragraphs.length; i++) {
            if (!paragraphs[i].trim().isEmpty()) {
                formattedText.append((i + 1) + "- " + paragraphs[i].trim() + "\n\n");
            }
        }
        return formattedText.toString().trim();
    }
    /****************************************************************************/
    /***************************** Click Listeners ******************************/
    /****************************************************************************/
    @Override
    public void onLayoutClick(Food food) {
        Toast.makeText(getContext(), food.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddToFavClick(Food food) {
        food.setFav(true);
        favPresenter.insertFood(food);
    }

    @Override
    public void onRemoveFavClick(Food food) {
        food.setFav(false);
        favPresenter.deleteFood(food);
    }

    @Override
    public void onCheckClick(Food food) {
        favPresenter.checkFoodExists(food);
    }

    @Override
    public void updateFoodPlanbyId(String mealId, boolean isFav) {
        favPresenter.updateFoodPlanbyId(mealId, isFav);
    }

    @Override
    public void onDateSelected(String date) {
        Toast.makeText(getContext(), "Selected date: " + date, Toast.LENGTH_SHORT).show();
        foodPlan = Converter.convertToPlanClass(food, date);
        favPresenter.insertFoodPlan(foodPlan);
    }

    @Override
    public void showData(List<Food> food) {
        // Method to show data if needed
    }

    @Override
    public void showErrMsg(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(error).setTitle("An Error Occurred");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /****************************************************************************/
    /***************************** Get Ingredients ******************************/
    /****************************************************************************/
    private List<Ingredient> getIngList(Food food) {
        List<Ingredient> ingList = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            try {
                String ingredient = (String) food.getClass().getMethod("getIngredient" + i).invoke(food);
                String measure = (String) food.getClass().getMethod("getMeasure" + i).invoke(food);
                if (ingredient != null && !ingredient.isEmpty() && measure != null && !measure.isEmpty()) {
                    String imageUrl = "https://www.themealdb.com/images/ingredients/" + ingredient + ".png";
                    ingList.add(new Ingredient(ingredient, measure, imageUrl));
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return ingList;
    }
}
