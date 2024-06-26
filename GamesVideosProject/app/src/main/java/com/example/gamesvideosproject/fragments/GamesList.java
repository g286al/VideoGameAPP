package com.example.gamesvideosproject.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamesvideosproject.R;
import com.example.gamesvideosproject.activites.MainActivity;
import com.example.gamesvideosproject.adapters.EndlessScrollListener;
import com.example.gamesvideosproject.adapters.FetchDataListener;
import com.example.gamesvideosproject.adapters.GameAdapter;
import com.example.gamesvideosproject.adapters.ItemClickListener;
import com.example.gamesvideosproject.adapters.OnScrollListener;
import com.example.gamesvideosproject.models.Game;
import com.example.gamesvideosproject.models.WrapContentGridLayoutManager;
import com.example.gamesvideosproject.services.DataService;
import com.example.gamesvideosproject.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class GamesList extends Fragment implements FetchDataListener  {

    public ItemClickListener itemClickListener2;
    public ItemClickListener itemClickListenerFilter;
    public ItemClickListener itemClickListener;
//    private ArrayList<Game> allGamesList = new ArrayList<>();
    private DataService dataService;
    public OnScrollListener getOnScrollListener() {
        return onScrollListener;
    }
    private MainActivityViewModel viewModel;

    private ArrayList<Game> filteredGamesList = new ArrayList<>();
    private boolean isSearching = false;
    public OnScrollListener onScrollListener;
    public RecyclerView recyclerView;
    public ArrayList<Game> arrGame = new ArrayList<Game>();
    public GameAdapter gameAdapter = new GameAdapter(getContext(), arrGame);
    public EndlessScrollListener endlessScrollListener;
    private SearchView searchview;

    private ConstraintLayout filterview1;
    private Button filterbutton;

    private boolean filterHidden = true;

    public GamesList(ItemClickListener itemClickListener, OnScrollListener onScrollListener) {
        this.itemClickListener = itemClickListener;
        this.onScrollListener = onScrollListener;
    }
    public GamesList(){

    }
    public static GamesList newInstance(String param1, String param2) {
        GamesList fragment = new GamesList();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void didFinishFetchingData(ArrayList<Game> fetchedGames) {
        Log.d("GameData", "Current list size: " + arrGame.size() + ", New data size: " + fetchedGames.size());
        for (Game game : fetchedGames) {
            if (!arrGame.contains(game)) {
                arrGame.add(game);
            }
        }
        gameAdapter.notifyDataSetChanged();
        endlessScrollListener.setLoading(false); // Reset loading state
    }

    @Override
    public void onGameDetailsFetched(Game game) {

    }

    private void setupRecyclerView(View view) {
        WrapContentGridLayoutManager layoutManager = new WrapContentGridLayoutManager(getContext(), 2);
        recyclerView = view.findViewById(R.id.r_view);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(gameAdapter);

        endlessScrollListener = new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if (filterHidden && dataService.hasNext()) {
                    dataService.getNext(); // Fetch next page
                }
            }
        };
        recyclerView.addOnScrollListener(endlessScrollListener);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_games, container, false);
        setupRecyclerView(view);
        WrapContentGridLayoutManager layoutManager = new WrapContentGridLayoutManager(getContext(), 2);
        recyclerView = view.findViewById(R.id.r_view);
        itemClickListener2 = new ItemClickListener() {
            @Override
            public void onClick(String name) {
                itemClickListener.onClick(name);
            }

            @Override
            public void onClick(MainActivity.FragmentType fragmentType) {

            }
        };
        gameAdapter = new GameAdapter(getContext(), arrGame, itemClickListener2);
        recyclerView.setAdapter(gameAdapter);
        filterbutton = view.findViewById(R.id.showFilterButton);
        searchview = view.findViewById(R.id.searchView);
        filterview1 = view.findViewById(R.id.filterTapp1);
        dataService = new DataService(this);

        filterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call function to show or hide filter based on current state
                toggleFilterVisibility();
            }
        });

        Button ratingFilterButton = view.findViewById(R.id.ratingFilter);

        List<String> ratingList = Arrays.asList("Under 3","3-4","4-5");


        ratingFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectionDialog("Select Rating", ratingList);
            }
        });



        Button allFilterButton = view.findViewById(R.id.allFilter);

        allFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterByAll();
            }
        });

        // Set up genre filter button
        Button genreFilterButton = view.findViewById(R.id.genreFilter);

        List<String> genresList = Arrays.asList(
                "Action", "Shooter", "Adventure", "RPG", "Battle Royale", "Strategy",
                "Sports", "Puzzle", "Idle", "Racing", "Simulation",
                "Fighting", "Horror", "Survival", "MOBA", "Card game"
        );
        genreFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectionDialog("Select Genre", genresList);
            }
        });

        // Set up release year filter button

        Button releaseYearFilterButton = view.findViewById(R.id.releaseYearFilter);


        List<String> yearsList = new ArrayList<>();
        for (int year = 1990; year <= 2024; year++) {
            yearsList.add(String.valueOf(year));
        }
        releaseYearFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectionDialog("Select Release Year", yearsList);
            }
        });

        // Initialize the adapter
        itemClickListener2 = new ItemClickListener() {
            @Override
            public void onClick(String name) {
                itemClickListener.onClick(name);
            }

            @Override
            public void onClick(MainActivity.FragmentType fragmentType) {

            }
        };
        endlessScrollListener = new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {
                onScrollListener.onScrollEnd();
            }
        };



        recyclerView.addOnScrollListener(endlessScrollListener);
        hideFilter();
        return view;
    }



    // Initialize DataService with this fragment as listener


    private void toggleFilterVisibility() {
        if (filterHidden) {
            showFilter();
        } else {
            hideFilter();
        }
    }

    private void showFilter() {
        searchview.setVisibility(View.VISIBLE);
        filterview1.setVisibility(View.VISIBLE);
        //filterbutton.setText("HIDE");
        filterHidden = false;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
    }
    private void hideFilter() {
        searchview.setVisibility(View.GONE);
        filterview1.setVisibility(View.GONE);
        //filterbutton.setText("FILTER");
        filterHidden = true;
    }

    private void showSelectionDialog(String title, List<String> optionsList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, optionsList);
        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedOption = optionsList.get(which);
                switch (title) {
                    case "Select Genre":
                        filterByGenre(selectedOption);
                        break;
                    case "Select Release Year":
                        filterByReleaseDate(selectedOption);
                        break;
                    case "Select Rating":
                        filterByRatingRange(selectedOption);
                        break;
                    default:
                        break;
                }
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean addGameIfNotExists(List<Game> games, Game newGame) {
        for (Game game : games) {
            if (game.getName().equalsIgnoreCase(newGame.getName())) {
                return false; // Game already exists, do not add
            }
        }
        games.add(newGame); // Add new game as it does not exist
        return true;
    }

    private void filterByGenre(String genre) {
        Set<String> names = new LinkedHashSet<>();
        List<Game> uniqueGames = arrGame.stream()
                .filter(game -> game.getGenre().contains(genre))
                .filter(game -> names.add(game.getName().toLowerCase()))  // Only add if name not already present
                .collect(Collectors.toList());

        updateAdapterData(uniqueGames);
    }

    private void filterByRatingRange(String ratingRange) {
        filteredGamesList.clear();

        float minRating, maxRating;
        switch (ratingRange) {
            case "Under 3":
                minRating = 0.0f;
                maxRating = 3.0f;
                break;
            case "3-4":
                minRating = 3.0f;
                maxRating = 4.0f;
                break;
            case "4-5":
                minRating = 4.0f;
                maxRating = 5.0f;
                break;
            default:
                return;
        }
        for (Game game : arrGame) {
            float rating = Float.parseFloat(game.getRating());
            if (rating >= minRating && rating <= maxRating) {
                addGameIfNotExists(filteredGamesList, game);
            }
        }
        updateAdapterData(filteredGamesList);
    }


    private void filterByReleaseDate(String year) {
        filteredGamesList.clear();

        for (Game game : arrGame) {
            if (game.getReleaseDate().contains(year)) {
                addGameIfNotExists(filteredGamesList, game);
            }
        }
        updateAdapterData(filteredGamesList);
    }

    private void filterByAll() {
        // Just call updateAdapterData with the entire list
        updateAdapterData(arrGame);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.onActionViewExpanded();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterByText(newText);
                return true;
            }
        });
    }

    private void filterByText(String query) {
        filteredGamesList.clear();

        for (Game game : arrGame) {
            if (game.getName().toLowerCase().contains(query.toLowerCase())) {
                addGameIfNotExists(filteredGamesList, game);
            }
        }
        updateAdapterData(filteredGamesList);
    }
    private void updateAdapterData(List<Game> filteredGames) {
        viewModel.setFilteredGames(new ArrayList<>(filteredGames));
        gameAdapter = new GameAdapter(getContext(), (ArrayList<Game>) filteredGames, itemClickListener2);
        recyclerView.setAdapter(gameAdapter);
        recyclerView.addOnScrollListener(endlessScrollListener); // Reattach listener
        endlessScrollListener.resetState();  // Ensure the listener is ready to load more
    }
    public void updateData(ArrayList<Game> updatedArrGame) {
        int previousSize = this.arrGame.size();
        this.arrGame.addAll(updatedArrGame);
        gameAdapter.notifyItemRangeInserted(previousSize, updatedArrGame.size());
        endlessScrollListener.setLoading(false);  // Make sure to set loading to false
    }

}