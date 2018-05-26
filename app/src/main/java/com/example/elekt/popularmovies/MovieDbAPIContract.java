package com.example.elekt.popularmovies;

import android.content.ContentUris;
import android.net.Uri;

public class MovieDbAPIContract {

    private static final String API_AUTHORITY = "api.themoviedb.org";
    private static final String API_ACTION_POPULAR = "popular";
    private static final String API_ACTION_TOPRATED = "top_rated";

    // set api key here
    private static final String API_KEY = "api_key";

    public static final String EXTRA_PARCEL = "parcel_movie";

    public static Uri getPopularURI() {
        return (new Uri.Builder())
                .scheme("http")
                .authority(API_AUTHORITY)
                .appendPath("3")
                .appendPath("movie")
                .appendPath(API_ACTION_POPULAR)
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter("page", "1")
                .build();
    }

    public static Uri getTopRatedURI() {
        return (new Uri.Builder())
                .scheme("http")
                .authority(API_AUTHORITY)
                .appendPath("3")
                .appendPath("movie")
                .appendPath(API_ACTION_TOPRATED)
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter("page", "1")
                .build();
    }
}
