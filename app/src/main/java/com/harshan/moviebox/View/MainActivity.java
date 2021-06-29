package com.harshan.moviebox.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import com.facebook.shimmer.ShimmerFrameLayout;
import com.harshan.moviebox.Services.EventInjectionManager;
import com.harshan.moviebox.ViewModel.MovieDataViewmodel;
import com.harshan.moviebox.Model.MovieDatabase;
import com.harshan.moviebox.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity implements EventInjectionManager.InjectedEventNotifier {
    public List<String> horizontalImages, verticalImages, titles, releaseDates;
    private MovieDataViewmodel mMovieDataViewmodel;
    private HorizontalRecyclerviewAdapter mHorizontalRecyclerviewAdapter;
    public VerticalRecyclerViewAdapter mVerticalRecyclerviewAdapter;
    private RecyclerView horizontalRecyclerView, verticalRecyclerView;
    private Toolbar mToolbar;
    private ShimmerFrameLayout mShimmerFrameLayout, mShimmerFrameLayout2;
    private EventInjectionManager mEventInjectionManager;
    private int page = 1;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        horizontalRecyclerView = findViewById(R.id.horizontal_recyclerview);
        verticalRecyclerView = findViewById(R.id.vertical_recyclerview);
        mMovieDataViewmodel = new MovieDataViewmodel();
        horizontalImages = new ArrayList<>();
        verticalImages = new ArrayList<>();
        titles = new ArrayList<>();
        releaseDates = new ArrayList<>();
        mToolbar = findViewById(R.id.toolbar);
        mEventInjectionManager = EventInjectionManager.getInstance();
        mEventInjectionManager.addListener(1, MainActivity.this);
        mProgressBar = findViewById(R.id.progressBar);

        mShimmerFrameLayout = findViewById(R.id.shimmerLayout);//for vertical recycler view
        mShimmerFrameLayout2 = findViewById(R.id.shimmerLayout2);//for horizontal recycler view

        mHorizontalRecyclerviewAdapter = new HorizontalRecyclerviewAdapter(MainActivity.this, horizontalImages);
        horizontalRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        horizontalRecyclerView.setAdapter(mHorizontalRecyclerviewAdapter);

        mVerticalRecyclerviewAdapter = new VerticalRecyclerViewAdapter(MainActivity.this, verticalImages, titles, releaseDates);
        verticalRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        verticalRecyclerView.setAdapter(mVerticalRecyclerviewAdapter);

        verticalRecyclerView.addOnScrollListener(new PageListener());

    }

    @Override
    protected void onResume() {
        super.onResume();

        horizontalRecyclerView.setVisibility(View.GONE);
        mShimmerFrameLayout2.setVisibility(View.VISIBLE);
        mShimmerFrameLayout2.startShimmer();

        verticalRecyclerView.setVisibility(View.GONE);
        mShimmerFrameLayout.setVisibility(View.VISIBLE);
        mShimmerFrameLayout.startShimmer();

        //Loads horizontal recycler view with playing now movies
        loadPlayingNowMovies();

        //Loads vertical recycler view with popular movies of page 1
        loadPopularMovies(page);
    }

    @Override
    protected void onStop() {
        super.onStop();

        mShimmerFrameLayout2.stopShimmer();
        mShimmerFrameLayout2.setVisibility(View.GONE);

        mShimmerFrameLayout.stopShimmer();
        mShimmerFrameLayout.setVisibility(View.GONE);
    }

    public void loadPopularMovies(int page) {
        mMovieDataViewmodel.getPopularMoviesDB(page).observe(MainActivity.this, new Observer<MovieDatabase>() {
            @Override
            public void onChanged(MovieDatabase movieDatabase) {
                mProgressBar.setVisibility(View.GONE);
                verticalRecyclerView.setVisibility(View.VISIBLE);
                mShimmerFrameLayout.stopShimmer();
                mShimmerFrameLayout.setVisibility(View.GONE);
//             i am fetching only 10 items instead of movieDatabase.getResults().length from the page to simplify the testing of the app
                for (int i = 0; i < 10; i++) {
                    verticalImages.add(movieDatabase.getResults()[i].getPosterPath());
                    titles.add(movieDatabase.getResults()[i].getTitle());
                    releaseDates.add(movieDatabase.getResults()[i].getReleaseDate());
                    mVerticalRecyclerviewAdapter.notifyDataSetChanged();
                }


            }
        });
    }

    private void loadPlayingNowMovies() {

        mMovieDataViewmodel.getPlayingNowMoviesDB().observe(MainActivity.this, new Observer<MovieDatabase>() {
            @Override
            public void onChanged(MovieDatabase movieDatabase) {
                horizontalRecyclerView.setVisibility(View.VISIBLE);
                mShimmerFrameLayout2.stopShimmer();
                mShimmerFrameLayout2.setVisibility(View.GONE);
                for (int i = 0; i < movieDatabase.getResults().length; i++) {
//                    Log.i("mainActivity", "onChanged: " + movieDatabase.getResults()[i].getPosterPath());
                    horizontalImages.add(movieDatabase.getResults()[i].getPosterPath());
                    mHorizontalRecyclerviewAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onReceiveEvent(int eventType, RecyclerView recyclerView) {
        if (eventType == 1 && recyclerView != null) {
            mProgressBar.setVisibility(VISIBLE);
            page++;
            loadPopularMovies(page);
        }
    }



}