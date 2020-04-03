package com.example.popularmovies.utilities;

import android.net.Uri;

class ImageUtils {
    private static final String TAG = "ImageUtils";

    private static final String BASE_API_IMAGE_URL ="https://image.tmdb.org/t/p/";
    static final String IMAGE_SIZE_THUMBNAIL = "w342";
    static final String IMAGE_SIZE_POSTER = "w500";

    static String buildMovieImageUri(String imageId, String size) {
        return Uri.parse(BASE_API_IMAGE_URL).buildUpon()
                .appendEncodedPath(size + imageId)
                .build()
                .toString();
    }
}
