package com.harshan.moviebox.Model;

import com.google.gson.annotations.SerializedName;

public class MovieDatabase {
    @SerializedName("results")
    private MovieResults[] results;

    public MovieResults[] getResults() {
        return results;
    }
}
