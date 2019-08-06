package com.android.fitternitytask.NetworkingCalls;


import com.android.fitternitytask.DataModel.MovieDetail;
import com.android.fitternitytask.DataModel.Review;
import com.android.fitternitytask.Utils.Constants;
import com.android.fitternitytask.DataModel.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET(Constants.MOVIE_PATH + "/popular")
    Call<Movie> popularMovies(
            @Query("page") int page);


    @GET(Constants.MOVIE_PATH + "/{movie_id}")
    Call<MovieDetail> movieDetail(
            @Path("movie_id") int movieId);

    @GET(Constants.MOVIE_PATH + "/top_rated")
    Call<Movie> getTopRatedMovies(
            @Query("page") int page
    );

    @GET(Constants.MOVIE_PATH + "/{movie_id}/" + Constants.REVIEWS)
    Call<Review> reviews(
            @Path("movie_id") int movieId);
}
