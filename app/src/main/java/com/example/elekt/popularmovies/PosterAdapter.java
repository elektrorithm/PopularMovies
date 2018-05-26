package com.example.elekt.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PosterAdapter extends BaseAdapter {
    private final Context mContext;
    private Movie[] mMovies;

    public PosterAdapter(Context context, Movie[] movies) {
        mContext = context;
        mMovies = movies;
    }

    @Override
    public int getCount() {
        //return mMovies.length;
        return 20;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Movie getItem(int position) {
        return mMovies[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Movie movie = mMovies[position];

        if (convertView == null) {
            final LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.poster_item, null);
        }
        ImageView iv = (ImageView)convertView.findViewById(R.id.poster_iv);

        Picasso.with(mContext).load(movie.getPosterPath()).into(iv);

        return convertView;
    }

    public void updateMoviesAndNotify(Movie[] movies) {
        mMovies = movies;
        this.notifyDataSetChanged();
    }
}
