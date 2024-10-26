package com.example.quotesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class home extends Fragment {

    private TextView quoteTextView;
    private Button generateButton, favoriteButton, shareButton;
    private String currentQuote;
    private String[] quotes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        quotes = new String[]{
                "The best way to predict the future is to create it.",
                "Do not wait to strike till the iron is hot, but make it hot by striking.",
                "Whether you think you can or think you can’t, you’re right.",
                "The best time to plant a tree was 20 years ago. The second best time is now.",
                "An unexamined life is not worth living.",
                "Your time is limited, so don't waste it living someone else's life.",
                "The only way to do great work is to love what you do.",
                "The best way to predict the future is to create it.",
                "You miss 100% of the shots you don't take.",
                "Believe you can and you're halfway there.",
                "Act as if what you do makes a difference. It does.",
                "Success is not how high you have climbed, but how you make a positive difference to the world.",
                "The purpose of our lives is to be happy.",
                "The only limit to our realization of tomorrow will be our doubts of today.",
                "What lies behind us and what lies before us are tiny matters compared to what lies within us.",
                "Dream big and dare to fail.",
                "It does not matter how slowly you go as long as you do not stop.",
                "Your life is your story, and the adventure ahead of you is the journey to fulfill your own purpose and potential.",
        };

        quoteTextView = view.findViewById(R.id.quoteTextView);
        generateButton = view.findViewById(R.id.generateButton);
        favoriteButton = view.findViewById(R.id.favoriteButton);
        shareButton = view.findViewById(R.id.shareButton);

        generateButton.setOnClickListener(v -> displayRandomQuote());

        favoriteButton.setOnClickListener(v -> {
            saveToFavorites(currentQuote);
            Toast.makeText(getActivity(), "Quote added to favorites", Toast.LENGTH_SHORT).show();
        });

        shareButton.setOnClickListener(v -> shareQuote());

        return view;
    }

    private void displayRandomQuote() {
        int randomIndex = new Random().nextInt(quotes.length);
        currentQuote = quotes[randomIndex];
        quoteTextView.setText(currentQuote);
    }




        private void saveToFavorites(String quote) {
            SharedPreferences prefs = getActivity().getSharedPreferences("favorites", getContext().MODE_PRIVATE);
            Set<String> favoritesSet = prefs.getStringSet("favoriteQuotes", new HashSet<>());
            favoritesSet.add(quote);
            prefs.edit().putStringSet("favoriteQuotes", favoritesSet).apply();
        }


    private void shareQuote() {
        if (currentQuote != null) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, currentQuote);
            startActivity(Intent.createChooser(shareIntent, "Share Quote via"));
        }
    }
}
