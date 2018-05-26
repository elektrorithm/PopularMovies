package com.example.elekt.popularmovies;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class MovieDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Movie movie = (Movie)getIntent().getParcelableExtra(MovieDbAPIContract.EXTRA_PARCEL);

        TextView title_tv = (TextView)findViewById(R.id.title_tv);
        ImageView poster_iv = (ImageView)findViewById(R.id.poster_iv);
        TextView date_tv = (TextView)findViewById(R.id.date_tv);
        TextView vote_tv = (TextView)findViewById(R.id.vote_tv);
        TextView overview_tv = (TextView)findViewById(R.id.overview_tv);

        String vote = this.getString(R.string.vote_average_with_denominator, movie.getVoteAverage().toString());

        title_tv.setText(movie.getMovieTitle());
        date_tv.setText(movie.getReleaseDate());
        vote_tv.setText(vote);
        overview_tv.setText(movie.getOverview());
        Picasso.with(this).load(movie.getPosterPath()).into(poster_iv);
    }

}
