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

import com.example.dishdash.calendar.view.CalenderFragment;
import com.example.dishdash.favourite.view.FavFragment;
import com.example.dishdash.homepage.view.HomeFragment;
import com.example.dishdash.homepage.view.HomePageActivity;

public class NetworkFragment extends Fragment {

    ImageButton retryButton;
    ImageButton goToFavoritesButton,goToPlanButton;
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
        goToPlanButton=view.findViewById(R.id.btnGoToPlan);
        goToFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomePageActivity) getActivity()).bottomNavigationView.setSelectedItemId(R.id.page_3);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new FavFragment());
                transaction.commit();
            }
        });
        goToPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomePageActivity) getActivity()).bottomNavigationView.setSelectedItemId(R.id.page_4);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new CalenderFragment());
                transaction.commit();
            }
        });

        return view;
    }
}