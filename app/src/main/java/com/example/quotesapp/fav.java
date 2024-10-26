package com.example.quotesapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class fav extends Fragment { // Renamed class for convention

    private ListView favoritesListView;
    private ArrayAdapter<String> favoritesAdapter;
    private ArrayList<String> favoriteQuotes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav, container, false);
        favoritesListView = view.findViewById(R.id.favoritesListView);

        // Load favorite quotes
        favoriteQuotes = loadFavorites();

        // Set up the adapter
        favoritesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, favoriteQuotes);
        favoritesListView.setAdapter(favoritesAdapter);

        // Handle long click to remove favorite quote
        favoritesListView.setOnItemLongClickListener((parent, view1, position, id) -> {
            String quoteToRemove = favoriteQuotes.get(position);
            removeFromFavorites(quoteToRemove);
            favoriteQuotes.remove(position);
            favoritesAdapter.notifyDataSetChanged();
            Toast.makeText(getActivity(), "Removed from favorites", Toast.LENGTH_SHORT).show();
            return true;
        });

        return view;
    }

    // Load favorites from SharedPreferences
    private ArrayList<String> loadFavorites() {
        SharedPreferences prefs = getActivity().getSharedPreferences("favorites", Context.MODE_PRIVATE);
        Set<String> favoritesSet = prefs.getStringSet("favoriteQuotes", new HashSet<>());
        return new ArrayList<>(favoritesSet);
    }

    // Remove a quote from favorites in SharedPreferences
    private void removeFromFavorites(String quote) {
        SharedPreferences prefs = getActivity().getSharedPreferences("favorites", Context.MODE_PRIVATE);
        Set<String> favoritesSet = prefs.getStringSet("favoriteQuotes", new HashSet<>());
        if (favoritesSet != null && favoritesSet.contains(quote)) {
            favoritesSet.remove(quote);
            prefs.edit().putStringSet("favoriteQuotes", favoritesSet).apply();
        }
    }
}

