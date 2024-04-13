package com.example.gamesvideosproject.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.gamesvideosproject.R;
import com.example.gamesvideosproject.activites.MainActivity;
import com.example.gamesvideosproject.adapters.ItemClickListener;

public class SplashScreenFragment extends Fragment {

    private ItemClickListener itemClickListener;

    public SplashScreenFragment(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.splash_screen, container, false);
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.new1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click action here
                // For example, navigate to the next fragment
                itemClickListener.onClick(MainActivity.FragmentType.home);
            }
        });
        return view;
    }
}
