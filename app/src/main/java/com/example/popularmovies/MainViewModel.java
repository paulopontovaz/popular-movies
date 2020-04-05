package com.example.popularmovies;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.popularmovies.database.AppDatabase;
import com.example.popularmovies.model.Movie;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> movies;

    public MainViewModel(@NonNull Application application) {
        super(application);
        movies = AppDatabase.getInstance(getApplication()).favoriteMovieDao().loadAllFavorites();
    }

    LiveData<List<Movie>> getMovies() {
        return movies;
    }
}
