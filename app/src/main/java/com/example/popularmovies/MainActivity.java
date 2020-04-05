package com.example.popularmovies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.popularmovies.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MainActivity
        extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Movie>>, MoviesAdapter.ListItemClickListener, Spinner.OnItemSelectedListener {

    private static final String TAG = "MainActivity";
    private static final int LOADER_ID = 1;
    private static final int FAVORITE_OPTION_INDEX = 2;

    private Spinner mSortOrderSpinner;
    private List<Movie> mFavoriteMovies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSortOrderSpinner = findViewById(R.id.spinner_sort_order);
        mSortOrderSpinner.setOnItemSelectedListener(this);
        setupViewModel();
    }

    private void updateList(List<Movie> newMovieList) {
        RecyclerView mMoviesRecyclerView = findViewById(R.id.movies_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        MoviesAdapter adapter = new MoviesAdapter(newMovieList, this);

        mMoviesRecyclerView.setLayoutManager(layoutManager);
        mMoviesRecyclerView.setAdapter(adapter);
    }

    @NonNull
    @Override
    public Loader<List<Movie>> onCreateLoader(int id, @Nullable Bundle args) {
        String sortOrder = getResources().getStringArray(R.array.sort_order_values)[mSortOrderSpinner.getSelectedItemPosition()];
        return new MovieLoader(this, sortOrder);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Movie>> loader, List<Movie> data) {
        updateList(data);
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.destroyLoader(LOADER_ID);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Movie>> loader) {
        updateList(new ArrayList<Movie>());
    }

    @Override
    public void onListItemClick(Movie movie) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(Movie.MOVIE_EXTRA, movie);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == FAVORITE_OPTION_INDEX) {
            Log.d(TAG, "onItemSelected: Favorite selected");
            updateList(mFavoriteMovies);
        } else {
            LoaderManager loaderManager = LoaderManager.getInstance(this);
            loaderManager.initLoader(LOADER_ID, null, this);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void setupViewModel() {
        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                mFavoriteMovies = movies;
            }
        });
    }
}
