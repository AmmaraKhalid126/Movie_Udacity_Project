package com.example.ammara.moviestage1;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReviewsApi {
    String BASE_URL = "http://api.themoviedb.org/3/";

    @GET("movie/{id}/reviews")
    Call<MovieReviews> getReviews(@Path("id") String id, @Query("api_key") String apiKey);
}