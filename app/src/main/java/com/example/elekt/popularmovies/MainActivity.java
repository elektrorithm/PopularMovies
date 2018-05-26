package com.example.elekt.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

// REVIEWER:
// ***** set API_KEY in MovieDbAPIContract.java file
public class MainActivity extends AppCompatActivity implements IMovieTransfer {

    private View mProgressBar;
    private GridView mGridView;
    private RelativeLayout mErrorFrame;
    private TextView mErrorMessage;
    private Button mTryAgain;
    private PosterAdapter mPosterAdapter;

    private int mSort;

    private final static int SORT_POPULAR = 0;
    private final static int SORT_TOP_RATED = 1;

    private final static String STATE_SORT = "state_sort";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (View)findViewById(R.id.busy_spinner);
        mGridView = (GridView)findViewById(R.id.posters_gv);
        mErrorFrame = (RelativeLayout)findViewById(R.id.error_frame);
        mErrorMessage = (TextView)findViewById(R.id.error_message);
        mTryAgain = (Button)findViewById(R.id.try_again_btn);

        loadMovies();

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie clicked = (Movie)parent.getItemAtPosition(position);
                showMovieDetails(clicked);
            }
        });

        mTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideError();
                loadMovies();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.ranking_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.popular_option:
                mSort = this.SORT_POPULAR;
                break;
            case R.id.toprated_option:
                mSort = this.SORT_TOP_RATED;
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        loadMovies();
        return false;
    }

    public void onStartIndicator() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void onCompleted(Movie[] movies) {
        //mMovies = movies;

        if(mPosterAdapter == null) {
            mPosterAdapter = new PosterAdapter(this, movies);
            mGridView.setAdapter(mPosterAdapter);
        } else {
            mPosterAdapter.updateMoviesAndNotify(movies);
        }

        mProgressBar.setVisibility(View.GONE);
    }

    public void onError(String error) {
        showError(error);
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void loadMovies() {
        if(mSort == this.SORT_POPULAR) loadPopularMovies();
        if(mSort == this.SORT_TOP_RATED) loadTopRatedMovies();
    }

    private void loadPopularMovies() {
        if(isOnline()) {
            this.setTitle(R.string.title_main_activity_popular);
            (new MovieDbAPIAsync(this)).execute(MovieDbAPIContract.getPopularURI().toString());
        } else {
            showError(getString(R.string.offline_error_message));
        }
    }

    private void loadTopRatedMovies() {
        if(isOnline()) {
            this.setTitle(R.string.title_main_activity_top_rated);
            (new MovieDbAPIAsync(this)).execute(MovieDbAPIContract.getTopRatedURI().toString());
        } else {
            showError(getString(R.string.offline_error_message));
        }
    }

    private void showError(String message) {
        mErrorMessage.setText(message);

        mErrorFrame.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mGridView.setVisibility(View.GONE);
    }

    private void hideError() {
        mErrorFrame.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mGridView.setVisibility(View.VISIBLE);
    }

    private void showMovieDetails(Movie movie) {
        Intent intent = new Intent(this, MovieDetails.class);
        intent.putExtra(MovieDbAPIContract.EXTRA_PARCEL, movie);
        startActivity(intent);
    }
}
