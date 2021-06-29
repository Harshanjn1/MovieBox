package com.harshan.moviebox.ViewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.harshan.moviebox.Model.MovieDBRepository;
import com.harshan.moviebox.Services.ApiCaller;
import com.harshan.moviebox.Model.MovieDatabase;
import com.harshan.moviebox.Services.RetrifitClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieDataViewmodel extends ViewModel {

    MovieDBRepository mMovieDBRepository = MovieDBRepository.getInstance();

    //To fetch data related to playing now movies
    MutableLiveData<MovieDatabase> playingNowMoviesLiveData = new MutableLiveData<>();
    public MutableLiveData<MovieDatabase> getPlayingNowMoviesDB() {
        playingNowMoviesLiveData = mMovieDBRepository.getPlayingNowMoviesDBFromAPI();
        return playingNowMoviesLiveData;
    }

    //To fetch data related to popular movies
    MutableLiveData<MovieDatabase> popularMoviesLiveData = new MutableLiveData<>();
    public MutableLiveData<MovieDatabase> getPopularMoviesDB(int page) {
        popularMoviesLiveData = mMovieDBRepository.getPopularMoviesDBFromAPI(page);
        return popularMoviesLiveData;
    }
}
