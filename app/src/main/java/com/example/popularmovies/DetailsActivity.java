package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {
    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        if (intent.hasExtra(Movie.MOVIE_EXTRA)) {
            mMovie = intent.getParcelableExtra(Movie.MOVIE_EXTRA);
            fillUi();
        }
    }

    private void fillUi() {
        ImageView posterImageView = findViewById(R.id.iv_movie_poster);
        TextView titleTextView = findViewById(R.id.tv_title);
        TextView synopsisTextView = findViewById(R.id.tv_synopsis);
        TextView ratingTextView = findViewById(R.id.tv_user_rating);
        TextView releaseDateTextView = findViewById(R.id.tv_release_date);

        Picasso.get()
                .load(Uri.parse(mMovie.getImagePath()))
                .resize(700, 0)
                .into(posterImageView);

        titleTextView.setText(mMovie.getOriginalTitle());
        synopsisTextView.setText(mMovie.getSynopsis());
        ratingTextView.setText(String.format(Locale.getDefault(), "%.1f", mMovie.getUserRating()));
        releaseDateTextView.setText(mMovie.getReleaseDate());
    }
}
