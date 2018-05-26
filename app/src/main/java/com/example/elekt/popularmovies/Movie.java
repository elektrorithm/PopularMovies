package com.example.elekt.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private String movieTitle;
    private String originalTitle;
    private String posterPath;
    private String overview;
    private Double voteAverage;
    private Double popularity;
    private String releaseDate;

    public Movie() {
        // empty
    }

    public Movie(Parcel in) {
        movieTitle = in.readString();
        originalTitle = in.readString();
        posterPath = in.readString();
        overview = in.readString();
        voteAverage = in.readDouble();
        popularity = in.readDouble();
        releaseDate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieTitle);
        dest.writeString(originalTitle);
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeDouble(voteAverage);
        dest.writeDouble(popularity);
        dest.writeString(releaseDate);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public void setMovieTitle(String _title) {
        movieTitle = _title;
    }

    public void setOriginalTitle(String _title) {
        originalTitle = _title;
    }

    public void setPosterPath(String _path) {
        posterPath = "http://image.tmdb.org/t/p/w185/" + _path;
    }

    public void setOverview(String _overview) {
        overview = _overview;
    }

    public void setVoteAverage(Double _rating) {
        voteAverage = _rating;
    }

    public void setPopularity(Double _rating) {
        popularity = _rating;
    }

    public void setReleaseDate(String _date) {
        releaseDate = _date.substring(0, 4);
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public Double getPopularity() {
        return popularity;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
