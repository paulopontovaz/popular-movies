package com.example.popularmovies;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.popularmovies.model.MovieDetailType;
import com.example.popularmovies.utilities.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailLoader<T> extends AsyncTaskLoader<List<T>> {
    private static final String TAG = "ReviewLoader";
    private int mMovieId;
    private MovieDetailType mType;

    MovieDetailLoader(@NonNull Context context, int movieId, MovieDetailType type) {
        super(context);
        mMovieId = movieId;
        mType = type;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<T> loadInBackground() {
        switch(mType) {
            case REVIEW:
                return (List<T>) NetworkUtils.fetchReviews(mMovieId);
            case TRAILER:
                return (List<T>) NetworkUtils.fetchTrailers(mMovieId);
            default:
                return new ArrayList();
        }
    }
}
