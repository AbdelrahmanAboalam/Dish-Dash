package com.example.dishdash;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dishdash.favourite.view.FavFragment;
import com.example.dishdash.homepage.view.HomeFragment;

public class NetworkFragment extends Fragment {

    ImageButton retryButton;
    Button goToFavoritesButton;
    TextView fragment_title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_network, container, false);

        fragment_title=getActivity().findViewById(R.id.fragment_title);
        fragment_title.setText("No Connection");
         retryButton = view.findViewById(R.id.btnRetry);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new HomeFragment());
                transaction.commit();
            }

        });

        goToFavoritesButton = view.findViewById(R.id.btnGoToFavorites);
        goToFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new FavFragment());
                transaction.commit();
            }
        });

        return view;
    }
}