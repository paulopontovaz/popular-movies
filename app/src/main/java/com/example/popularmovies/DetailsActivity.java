package com.example.popularmovies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.database.AppDatabase;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.MovieDetailType;
import com.example.popularmovies.model.Review;
import com.example.popularmovies.model.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {
    private static final String TAG = "DetailsActivity";
    private static final int TRAILER_LIST_LOADER_ID = 115;
    private static final int REVIEW_LIST_LOADER_ID = 539;

    private Movie mMovie;
    private boolean isFavorite = false;
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        if (intent.hasExtra(Movie.MOVIE_EXTRA)) {
            mDb = AppDatabase.getInstance(getApplicationContext());
            mMovie = intent.getParcelableExtra(Movie.MOVIE_EXTRA);
            fillUi();

            DetailsViewModelFactory factory = new DetailsViewModelFactory(mDb, mMovie.getId());
            final DetailsViewModel viewModel = new ViewModelProvider(this, factory).get(DetailsViewModel.class);
            viewModel.getCount().observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer count) {
                    isFavorite = count > 0;
                    updateFavoriteImageResource();
                }
            });
        }
    }

    public void setFavorite(View view) {
        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (isFavorite) {
                    mDb.favoriteMovieDao().deleteFavorite(mMovie);
                } else {
                    mDb.favoriteMovieDao().insertFavorite(mMovie);
                }
            }
        });
    }

    private void fillUi() {
        ImageView posterImageView = findViewById(R.id.iv_movie_poster);
        TextView titleTextView = findViewById(R.id.tv_title);
        TextView synopsisTextView = findViewById(R.id.tv_synopsis);
        TextView ratingTextView = findViewById(R.id.tv_user_rating);
        TextView releaseDateTextView = findViewById(R.id.tv_release_date);
        ImageButton setFavoriteImageButton = findViewById(R.id.ib_set_favorite);

        Picasso.get()
                .load(Uri.parse(mMovie.getImagePath()))
                .resize(400, 0)
                .into(posterImageView);

        titleTextView.setText(mMovie.getOriginalTitle());
        synopsisTextView.setText(mMovie.getSynopsis());
        ratingTextView.setText(String.format(Locale.getDefault(), "%.1f", mMovie.getUserRating()));
        releaseDateTextView.setText(mMovie.getReleaseDate());

        if (isFavorite) {
            setFavoriteImageButton.setImageResource(R.drawable.ic_star);
        }

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(TRAILER_LIST_LOADER_ID, null, this);
        loaderManager.initLoader(REVIEW_LIST_LOADER_ID, null, this);
    }

    private void updateReviews(List<Review> newReviewList) {
        Log.d(TAG, "updateReviews: newReviewList " + newReviewList.toString());
        RecyclerView mReviewsRecyclerView = findViewById(R.id.rv_reviews);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        ReviewsAdapter adapter = new ReviewsAdapter(newReviewList);

        mReviewsRecyclerView.setLayoutManager(layoutManager);
        mReviewsRecyclerView.setAdapter(adapter);
    }

    private void updateTrailers(List<Trailer> newTrailerList) {
        Log.d(TAG, "updateTrailers: trailers " + newTrailerList.toString());
        RecyclerView mTrailersRecyclerView = findViewById(R.id.rv_trailers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        TrailersAdapter adapter = new TrailersAdapter(this, newTrailerList);

        mTrailersRecyclerView.setLayoutManager(layoutManager);
        mTrailersRecyclerView.setAdapter(adapter);
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {
        Log.d(TAG, "updateReviews: mMovie.getId() " + mMovie.getId());
        switch (id) {
            case TRAILER_LIST_LOADER_ID:
                return new MovieDetailLoader<Trailer>(this, mMovie.getId(), MovieDetailType.TRAILER);
            case REVIEW_LIST_LOADER_ID:
                return new MovieDetailLoader<Review>(this, mMovie.getId(), MovieDetailType.REVIEW);
            default:
                return new Loader(this);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object data) {
        int loaderId = loader.getId();

        switch (loaderId) {
            case TRAILER_LIST_LOADER_ID:
                List<Trailer> trailers = (List<Trailer>) data;
                updateTrailers(trailers);
                break;
            case REVIEW_LIST_LOADER_ID:
                List<Review> reviews = (List<Review>) data;
                updateReviews(reviews);
                break;
            default:
                break;
        }

        LoaderManager.getInstance(this).destroyLoader(loaderId);
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        switch (loader.getId()) {
            case TRAILER_LIST_LOADER_ID:
                updateTrailers(new ArrayList<Trailer>());
                break;
            case REVIEW_LIST_LOADER_ID:
                updateReviews(new ArrayList<Review>());
                break;
            default:
                break;
        }
    }

    private void updateFavoriteImageResource() {
        ImageButton setFavoriteImageButton = findViewById(R.id.ib_set_favorite);

        if (isFavorite) {
            setFavoriteImageButton.setImageResource(R.drawable.ic_star);
        } else {
            setFavoriteImageButton.setImageResource(R.drawable.ic_star_border);
        }
    }
}
