package com.example.popularmovies;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.popularmovies.model.Movie;
import com.example.popularmovies.utilities.NetworkUtils;

import java.util.List;

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {
    private String mSortOrder;

    MovieLoader(@NonNull Context context, String mSortOrder) {
        super(context);
        this.mSortOrder = mSortOrder;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<Movie> loadInBackground() {
        if (mSortOrder == null) {
            return null;
        }

        return NetworkUtils.fetchMovies(mSortOrder);
    }
}
