package com.example.elekt.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MovieDbAPIAsync extends AsyncTask<String, Void, String> {
    private final IMovieTransfer mTransfer;

    public MovieDbAPIAsync(IMovieTransfer transfer) {
        mTransfer = transfer;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        // turn on the progress indicator
        mTransfer.onStartIndicator();
    }

    @Override
    protected String doInBackground(String... params) {
        String address = params[0];
        String response = null;
        HttpURLConnection cx = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(address);
            cx = (HttpURLConnection)url.openConnection();
            cx.setRequestMethod("GET");
            cx.connect();

            InputStream stream = cx.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if(stream == null) return null;
            reader = new BufferedReader(new InputStreamReader(stream));

            int statusCode = cx.getResponseCode();
            if(statusCode == HttpURLConnection.HTTP_OK) {
                String _line;
                while ((_line = reader.readLine()) != null) {
                    buffer.append(_line);
                }
                if(buffer.length() == 0) return null;
                reader.close();

                response = buffer.toString();
            } else {
                mTransfer.onError(cx.getResponseMessage());
            }
        }
        catch(Exception ex) {
            mTransfer.onError(ex.getMessage());
        }
        finally {
            if(cx != null) cx.disconnect();
        }

        return response;
    }

    @Override
    protected void onPostExecute(String results) {
        super.onPostExecute(results);

        Movie[] tempMovies = null;

        if(results != null) {
            try {
                JSONArray json = (new JSONObject(results)).getJSONArray("results");
                tempMovies = new Movie[json.length()];
                for (int i = 0; i < json.length(); i++) {
                    JSONObject j = json.getJSONObject(i);

                    Movie movie = new Movie();
                    movie.setMovieTitle(j.getString("title"));
                    movie.setOriginalTitle(j.getString("original_title"));
                    movie.setOverview(j.getString("overview"));
                    movie.setPopularity(j.getDouble("popularity"));
                    movie.setVoteAverage(j.getDouble("vote_average"));
                    movie.setReleaseDate(j.getString("release_date"));
                    movie.setPosterPath(j.getString("poster_path"));

                    tempMovies[i] = movie;
                }
            } catch (Exception ex) {
                mTransfer.onError(ex.getMessage());
            }

            mTransfer.onCompleted(tempMovies);
        }
    }
}