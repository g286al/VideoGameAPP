package com.example.gamesvideosproject.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gamesvideosproject.R;
import com.example.gamesvideosproject.models.Game;
import com.squareup.picasso.Picasso;

public class GameDetails extends Fragment {

    private Game game;

    public GameDetails(Game game) {
        this.game = game;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_details, container, false);
        Button backButton = view.findViewById(R.id.backButton);
        // Retrieve the game object based on the position

        // Bind views
        ImageView gameDetailImg = view.findViewById(R.id.gameDetailImg);
        TextView gameDetailName = view.findViewById(R.id.gameDetailName);
        TextView gameDetailRelease = view.findViewById(R.id.gameDetailRelease);
        TextView gameDetailRating = view.findViewById(R.id.gameDetailRating);
        TextView gameDetailGenre = view.findViewById(R.id.gameDetailGenre);
        TextView gameDetailDescription = view.findViewById(R.id.gameDetailDescription);
        TextView gameDetailDeveloper = view.findViewById(R.id.gameDetailDevelopers);
        final RatingBar ratingBar = view.findViewById(R.id.rating_rating_bar);

        TextView gameDetailRatingsCount = view.findViewById(R.id.gameDetailRatingsCount);

        String ratingInfo = gameDetailRating.getText().toString();
        float ratingValue = 0.0f; // default value in case of parsing failure or empty string

        if (!ratingInfo.isEmpty()) {
            try {
                ratingValue = Float.parseFloat(ratingInfo);
            } catch (NumberFormatException e) {
                e.printStackTrace(); // handle parsing error
            }
        }

        ratingBar.setRating(Float.parseFloat(game.getRating()));

        Picasso.get().load(game.getImageUrl()).into(gameDetailImg);
        // Set text values
        gameDetailName.setText(game.getName());
        gameDetailRelease.setText(game.getReleaseDate());
        gameDetailRating.setText(game.getRating());
        gameDetailGenre.setText(game.getGenre());
        gameDetailDescription.setText(game.getDescription());
        Button readMoreButton = view.findViewById(R.id.readMoreButton);
        gameDetailRatingsCount.setText(game.getRatingsCount());
        gameDetailDeveloper.setText(game.getDevelopers());
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                requireActivity().onBackPressed();

            }
        });
        readMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDescriptionDialog(game.getDescription());
            }
        });

        return view;
    }

    private void showDescriptionDialog(String description) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Game Description");
        builder.setMessage(description);
        builder.setPositiveButton("OK", null);
        builder.show();
    }
}