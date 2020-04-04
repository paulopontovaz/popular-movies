package com.example.popularmovies.utilities;

import android.util.Log;

import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.Review;
import com.example.popularmovies.model.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class JsonUtils {
    private static final String TAG = "JsonUtils";

    static List<Movie> getMoviesFromJson(String json) {
        final String RESULTS = "results";
        final String ID = "id";
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

                int id = movieJson.getInt(ID);
                String originalTitle = movieJson.getString(ORIGINAL_TITLE);
                String imagePath = movieJson.getString(IMAGE_PATH);
                String synopsis = movieJson.getString(SYNOPSIS);
                Double userRating = movieJson.getDouble(USER_RATING);
                String releaseData = movieJson.getString(RELEASE_DATE);

                String formattedImagePath = ImageUtils.buildMovieImageUri(imagePath, ImageUtils.IMAGE_SIZE_THUMBNAIL);

                movies.add(new Movie(id, originalTitle, formattedImagePath, synopsis, userRating, releaseData));
            }
        } catch (JSONException e) {
            Log.e(TAG, "getMoviesFromJson", e);
        }

        return movies;
    }

    static List<Review> getReviewsFromJson(String json) {
        final String RESULTS = "results";
        final String ID = "id";
        final String CONTENT = "content";
        final String AUTHOR = "author";

        List<Review> review = new ArrayList<>();

        try {
            JSONObject baseJson = new JSONObject(json);
            JSONArray reviewsJson = baseJson.getJSONArray(RESULTS);

            for (int i = 0; i < reviewsJson.length(); i++){
                JSONObject reviewJson = reviewsJson.getJSONObject(i);

                String id = reviewJson.getString(ID);
                String content = reviewJson.getString(CONTENT);
                String author = reviewJson.getString(AUTHOR);

                review.add(new Review(id, content, author));
            }
        } catch (JSONException e) {
            Log.e(TAG, "getReviewsFromJson", e);
        }

        return review;
    }

    static List<Trailer> getTrailersFromJson(String json) {
        final String RESULTS = "results";
        final String ID = "id";
        final String KEY = "key";
        final String NAME = "name";
        final String SITE = "site";
        final String TYPE = "type";

        List<Trailer> trailers = new ArrayList<>();

        try {
            JSONObject baseJson = new JSONObject(json);
            JSONArray trailersJson = baseJson.getJSONArray(RESULTS);

            for (int i = 0; i < trailersJson.length(); i++){
                JSONObject movieJson = trailersJson.getJSONObject(i);

                String id = movieJson.getString(ID);
                String key = movieJson.getString(KEY);
                String name = movieJson.getString(NAME);
                String site = movieJson.getString(SITE);
                String type = movieJson.getString(TYPE);

                trailers.add(new Trailer(id, key, name, site, type));
            }
        } catch (JSONException e) {
            Log.e(TAG, "getTrailersFromJson", e);
        }

        return trailers;
    }
}
