package com.harshan.moviebox.Services;

import com.harshan.moviebox.Model.MovieDatabase;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiCaller {
    String BASE_URL = "https://api.themoviedb.org/3/movie/";
    @GET("now_playing?language=en-US&page=undefined&api_key=da4da2a01f37665b403b547f2551cf34")
    Call<MovieDatabase> getNowPlayingMovies();

    @GET("popular?language=en-US&page=undefined&api_key=da4da2a01f37665b403b547f2551cf34")
    Call<MovieDatabase> getPopularMovies(@Query("page") int page);


}
