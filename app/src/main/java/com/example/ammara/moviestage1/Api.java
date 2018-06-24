package com.example.ammara.moviestage1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    String BASE_URL = "http://api.themoviedb.org/3/";

    @GET("movie/{id}/videos")
    Call<MovieTrailers> getTrailers(@Path("id") String id, @Query("api_key") String apiKey);
}
