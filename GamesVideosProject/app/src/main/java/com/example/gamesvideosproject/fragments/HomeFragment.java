package com.example.gamesvideosproject.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.gamesvideosproject.R;
import com.example.gamesvideosproject.activites.MainActivity;
import com.example.gamesvideosproject.adapters.ItemClickListener;


public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final ItemClickListener itemClickListener;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;

    }

//    public static HomeFragment newInstance(String param1, String param2) {
//        HomeFragment fragment = new HomeFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button btn = view.findViewById(R.id.exploreButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onClick(MainActivity.FragmentType.login);
            }
        });
        // Find the TextView by its id
        TextView textview1 = view.findViewById(R.id.appNameTextView);
        TextView textview2 = view.findViewById(R.id.Dess);
        // Now you can use the TextView object (textview1) to set text or perform any other operations
        textview1.setText("\n\n            PlayNation\n Your Ultimate Gaming Hub\n");
        textview2.setText("Welcome to PlayNation, where gaming dreams come alive! \uD83C\uDFAE\n" +
                "\n" +
                "\uD83C\uDF1F Discover New Games: Dive into a world of endless entertainment. Explore a curated collection of games across genres—action, puzzles, strategy, and more.\n" +
                "\n" +
                "\uD83D\uDD25 Stay Updated: Get real-time game info, reviews, and tips. Stay ahead of the gaming curve with PlayNation.\n" +
                "\n" +
                "Ready to level up? Register to PlayNation now and unlock a universe of gaming possibilities! \uD83D\uDE80");
        return view;
    }
}