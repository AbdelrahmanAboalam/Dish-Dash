package com.example.dishdash.mealditalies.view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dishdash.R;
import com.example.dishdash.favourite.presenter.FavouritePresenter;
import com.example.dishdash.favourite.view.FavFoodAdapter;
import com.example.dishdash.favourite.view.OnFavClickListener;
import com.example.dishdash.homepage.view.IngredientsAdapter;
import com.example.dishdash.mealditalies.presenter.MealsPresenter;
import com.example.dishdash.model.response.Food;

import java.util.ArrayList;


public class MealFragment extends Fragment implements OnFoodClickListener {
    private static final String ARG_FOOD_NAME = "food_name";
    private Food food;

    MealsPresenter favpresnter;
    FavFoodAdapter adapter;
    OnFoodClickListener favListener;

    ImageView mealImage;
    TextView mealName;
    TextView instruction;
    WebView webView;
    Button btnFav;

    private RecyclerView recyclerView;
    private IngredientsAdapter ingredientsAdapter;
    LinearLayoutManager linearLayout;
    private OnFavClickListener listener;

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
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_meal, container, false);
        initUI(view);

        recyclerView.setHasFixedSize(true);
        linearLayout = new LinearLayoutManager(getActivity());
        ingredientsAdapter = new IngredientsAdapter(getActivity(), new ArrayList<>());
        linearLayout.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(ingredientsAdapter);

        btnFav=view.findViewById(R.id.btnFav);
        mealImage = view.findViewById(R.id.imageView);
        mealName = view.findViewById(R.id.textView2);
        instruction = view.findViewById(R.id.txtInstruction);
        //For Web
        webView = view.findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());


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
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favListener.onAddToFavClick(food);
            }
        });
        return view;
    }

    private void initUI(View view) {
        recyclerView = view.findViewById(R.id.recyclerView2);
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
        favpresnter.addToFav(food);
//        adapter.notifyDataSetChanged();
    }
}