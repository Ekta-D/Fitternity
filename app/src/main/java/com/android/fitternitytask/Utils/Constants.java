package com.android.fitternitytask.Utils;

public class Constants {
    public static final String BASE_URL = "https://api.themoviedb.org";
    public static final String IMG_URL = "http://image.tmdb.org/t/p/w500";
    public static final String API_KEY = "f79644689e6db578717354c9188c7182";
    public static final String VERSION = "/3";
    public static final String MOVIE = "/movie";
    public static final String VIDEOS = "videos";
    public static final String REVIEWS = "reviews";
    public static final String LANG_EN = "en-US";
    public static final String DISCOVER = "/discover";
    public static final String APPLICATION_ID = "com.android.fitternitytask";
    public static final String ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmNzk2NDQ2ODllNmRiNTc4NzE3MzU0YzkxODhjNzE4MiIsInN1YiI6IjVkNDZhZjA0YTBiZTI4NjUyZmE0YjgxZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.kVRZplAgs0y2_sZb1b2pFR_A1SScDucdpcL-E_sIDho";
    public static final String MOVIE_PATH = VERSION + MOVIE;
    public static final String POPULAR_PATH = VERSION + DISCOVER;
    public static final String popularMoviesURL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=" + API_KEY;

    public static final String topRatedMoviesURL = "http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&api_key=" + API_KEY;

}


