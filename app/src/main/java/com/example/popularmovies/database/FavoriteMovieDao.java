package com.example.popularmovies.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.popularmovies.model.Movie;

import java.util.List;

@Dao
public interface FavoriteMovieDao {

    @Query("SELECT * FROM movie")
    LiveData<List<Movie>> loadAllFavorites();

    @Query("SELECT COUNT(*) FROM movie WHERE id = :id")
    LiveData<Integer> favoriteExists(int id);

    @Insert
    void insertFavorite(Movie favorite);

    @Delete
    void deleteFavorite(Movie favorite);
}
