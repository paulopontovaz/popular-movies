package com.example.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import com.example.popularmovies.BuildConfig;
import com.example.popularmovies.model.Movie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class NetworkUtils {
    private static final String TAG = "NetworkUtils";

    private static final String BASE_API_URL ="https://api.themoviedb.org/3/movie/";


    private static final String QUERY_PARAM_API_KEY = "api_key";

    public static List<Movie> fetchMovies(String sortOrder) {
        URL url = buildMovieListUrl(sortOrder);
        String json = getResponseFromHttpUrl(url);

        return JsonUtils.getMoviesFromJson(json);
    }

    private static URL buildMovieListUrl(String sortOrder) {
        Uri builtUri = Uri.parse(BASE_API_URL).buildUpon()
                .appendPath(sortOrder)
                .appendQueryParameter(QUERY_PARAM_API_KEY, BuildConfig.API_KEY)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.e(TAG, "buildMovieListUrl", e);
        }

        return url;
    }

    private static String getResponseFromHttpUrl(URL url) {
        if (url == null) {
            return null;
        }

        HttpURLConnection connection = null;
        InputStream inputStream = null;
        String response = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                inputStream = connection.getInputStream();
                response = readFromStream(inputStream);
            } else {
                Log.e(TAG, "getResponseFromHttpUrl: Error response code not 200. Actual value: " + responseCode);
            }
        } catch (IOException e) {
            Log.e(TAG, "getResponseFromHttpUrl: on response handling", e);
        }

        try {
            if (connection != null) {
                connection.disconnect();
            }

            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            Log.e(TAG, "getResponseFromHttpUrl: on clean-up", e);
        }

        return response;
    }

    private static String readFromStream(InputStream in) throws IOException {
        StringBuilder output = new StringBuilder();

        if (in != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line = reader.readLine();

            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }
}
