package com.example.elekt.popularmovies;

public interface IMovieTransfer {
    void onStartIndicator();
    void onCompleted(Movie[] movies);
    void onError(String error);
}
