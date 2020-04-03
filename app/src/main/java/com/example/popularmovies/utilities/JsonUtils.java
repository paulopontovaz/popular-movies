package com.example.popularmovies.utilities;

import android.util.Log;

import com.example.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class JsonUtils {
    private static final String TAG = "JsonUtils";

    static List<Movie> getMoviesFromJson(String json) {
        final String RESULTS = "results";
        final String ORIGINAL_TITLE = "original_title";
        final String IMAGE_PATH = "poster_path";
        final String SYNOPSIS = "overview";
        final String USER_RATING = "vote_average";
        final String RELEASE_DATE = "release_date";

        List<Movie> movies = new ArrayList<>();

        try {
            JSONObject baseJson = new JSONObject(json);
            JSONArray moviesJson = baseJson.getJSONArray(RESULTS);

            for (int i = 0; i < moviesJson.length(); i++){
                JSONObject movieJson = moviesJson.getJSONObject(i);

                String originalTitle = movieJson.getString(ORIGINAL_TITLE);
                String imagePath = movieJson.getString(IMAGE_PATH);
                String synopsis = movieJson.getString(SYNOPSIS);
                Double userRating = movieJson.getDouble(USER_RATING);
                String releaseData = movieJson.getString(RELEASE_DATE);

                String formattedImagePath = ImageUtils.buildMovieImageUri(imagePath, ImageUtils.IMAGE_SIZE_THUMBNAIL);

                movies.add(new Movie(originalTitle, formattedImagePath, synopsis, userRating, releaseData));
            }
        } catch (JSONException e) {
            Log.e(TAG, "getMoviesFromJson", e);
        }

        return movies;
    }
}
