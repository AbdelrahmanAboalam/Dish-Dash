package com.example.dishdash;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dishdash.homepage.presenter.HomePresenter;
import com.example.dishdash.homepage.view.HomeAdapter;
import com.example.dishdash.model.response.Food;

import java.io.Serializable;


public class MealFragment extends Fragment {
    private static final String ARG_FOOD_NAME = "food_name";
    private Food food;

    ImageView mealImage;
    TextView mealName;
    TextView instruction;
    WebView webView;

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
        mealImage = view.findViewById(R.id.imageView);
        mealName = view.findViewById(R.id.textView2);
        instruction = view.findViewById(R.id.txtInstruction);
        //For Web
        webView = view.findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());


        Glide.with(this).load(food.getMealThumbnail()).apply(new RequestOptions().override(700,200)
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
        return view;
    }
    private String getYoutubeVideoId(String url) {
        String videoId = "";
        if (url != null && url.contains("v=")) {
            String[] parts = url.split("v=");
            videoId = parts[1].split("&")[0];
        }
        return videoId;
    }
}