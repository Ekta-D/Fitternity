package com.android.fitternitytask.NetworkingCalls;



import com.android.fitternitytask.Utils.Constants;
import com.android.fitternitytask.DataModel.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET(Constants.MOVIE_PATH + "/popular")
    Call<Movie> popularMovies(
            @Query("page") int page);





    @GET(Constants.MOVIE_PATH+"/top_rated")
    Call<Movie> getTopRatedMovies(
            @Query("page") int page
    );

}
