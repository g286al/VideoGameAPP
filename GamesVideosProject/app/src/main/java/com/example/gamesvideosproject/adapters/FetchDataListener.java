package com.example.gamesvideosproject.adapters;

import com.example.gamesvideosproject.models.Game;

import java.util.ArrayList;

public interface FetchDataListener {
    void didFinishFetchingData(ArrayList<Game> gameList);
    void onGameDetailsFetched(Game game);
}
