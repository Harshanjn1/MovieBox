package com.harshan.moviebox.Model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.harshan.moviebox.Services.ApiCaller;
import com.harshan.moviebox.Services.RetrifitClass;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieDBRepository {
    ExecutorService executorService = Executors.newFixedThreadPool(2);
    private static MovieDBRepository movieDBRepository;
    private static final String TAG = "response";
    private MutableLiveData<MovieDatabase> nowPlayingMoviesLiveData ;
    private MutableLiveData<MovieDatabase> popularMoviesLiveData ;

    private MovieDBRepository() {
        //creating empty constructor to achieve singleton
    }
    public static MovieDBRepository getInstance() {
        //Allowing only one thread at a time to access database
        synchronized (MovieDBRepository.class) {
            if (movieDBRepository == null) {
                movieDBRepository = new MovieDBRepository();
            }
        }
        return movieDBRepository;
    }

    public MutableLiveData<MovieDatabase> getPlayingNowMoviesDBFromAPI(){
        nowPlayingMoviesLiveData = new MutableLiveData<>();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit = RetrifitClass.getRetrofitInstance(ApiCaller.BASE_URL);
                ApiCaller apiCaller = retrofit.create(ApiCaller.class);
                Call<MovieDatabase> movieDatabaseCall = apiCaller.getNowPlayingMovies();
                movieDatabaseCall.enqueue(new Callback<MovieDatabase>() {
                    @Override
                    public void onResponse(Call<MovieDatabase> call, Response<MovieDatabase> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            nowPlayingMoviesLiveData.postValue(response.body());
                            Log.i(TAG, "onResponse: " + response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieDatabase> call, Throwable t) {
                        Log.i(TAG, "onFailure " + t.getMessage());
                    }
                });


            }
        });

        return nowPlayingMoviesLiveData;
    }

    public MutableLiveData<MovieDatabase> getPopularMoviesDBFromAPI(int page){
        popularMoviesLiveData = new MutableLiveData<>();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit = RetrifitClass.getRetrofitInstance(ApiCaller.BASE_URL);
                ApiCaller apiCaller = retrofit.create(ApiCaller.class);
                Call<MovieDatabase> movieDatabaseCall = apiCaller.getPopularMovies(page);
                movieDatabaseCall.enqueue(new Callback<MovieDatabase>() {
                    @Override
                    public void onResponse(Call<MovieDatabase> call, Response<MovieDatabase> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            popularMoviesLiveData.postValue(response.body());
                            Log.i(TAG, "onResponse: " + response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieDatabase> call, Throwable t) {
                        Log.i(TAG, "onFailure " + t.getMessage());
                    }
                });


            }
        });

        return popularMoviesLiveData;
    }


}
