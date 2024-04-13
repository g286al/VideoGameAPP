package com.example.gamesvideosproject.adapters;

import androidx.fragment.app.Fragment;

import com.example.gamesvideosproject.activites.MainActivity;

public interface ItemClickListener {
//    void onClick(int position);
    void onClick(String name);
    void onClick(MainActivity.FragmentType fragmentType);
}
