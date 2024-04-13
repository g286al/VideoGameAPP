package com.example.gamesvideosproject.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gamesvideosproject.adapters.FetchDataListener;
import com.example.gamesvideosproject.models.Game;
import com.example.gamesvideosproject.services.DataService;

import java.util.ArrayList;

public class MainActivityViewModel extends ViewModel implements FetchDataListener {
    public MutableLiveData<ArrayList<Game>> arrGame = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Game>> newItems = new MutableLiveData<>();
    public DataService dataService;

    private MutableLiveData<ArrayList<Game>> filteredGames = new MutableLiveData<>();
    public MutableLiveData<Game>  gameDetailFetched = new MutableLiveData<>();
    public void setFilteredGames(ArrayList<Game> games) {
        filteredGames.setValue(games);
    }
    public String id;
    public LiveData<ArrayList<Game>> getFilteredGames() {
        return filteredGames;
    }

    public MainActivityViewModel() {
        this.dataService = new DataService(this);
    }

    @Override
    public void didFinishFetchingData(ArrayList<Game> gameList) {
        if(arrGame.getValue() == null) {
            arrGame.setValue(new ArrayList<>());
        }
        ArrayList<Game> tempList = arrGame.getValue();

//        for (Game game : gameList) {
//            if (!arrGame.contains(game)) {
//                tempList.add(game);
//            }
//        }

        if (tempList != null) {
            tempList.addAll(gameList);
        }
        arrGame.setValue(tempList);
        newItems.setValue(gameList);
    }

    @Override
    public void onGameDetailsFetched(Game game) {
        gameDetailFetched.setValue(game);
    }

    public void fetchMoreData(){

        dataService.getNext();
    }
    public Game getGameByPos(int position){
        Game choosenGame = arrGame.getValue().get(position);
        dataService.GatherMoreInfo(choosenGame);
        return choosenGame;
    }

    public Game getGameByPosfilter(int position){
        Game choosenGame = filteredGames.getValue().get(position);
        dataService.GatherMoreInfo(choosenGame);
        return choosenGame;
    }

    public Game getGameByName(String name) {
        Game choosenGame = arrGame.getValue().stream().filter(game -> game.getName().equals(name)).findFirst().orElse(null);
        id = choosenGame.getId();
        if(choosenGame != null) {
            dataService.GatherMoreInfo(choosenGame);

        }
        return choosenGame;
    }
}
