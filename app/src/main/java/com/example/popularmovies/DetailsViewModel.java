package com.example.popularmovies;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.popularmovies.database.AppDatabase;

class DetailsViewModel extends ViewModel {

    private LiveData<Integer> count;

    DetailsViewModel(AppDatabase appDatabase, int movieId) {
        count = appDatabase.favoriteMovieDao().favoriteExists(movieId);
    }

    LiveData<Integer> getCount() {
        return count;
    }

}
