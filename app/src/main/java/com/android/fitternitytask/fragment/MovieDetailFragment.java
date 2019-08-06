package com.android.fitternitytask.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.fitternitytask.DataModel.Genre;
import com.android.fitternitytask.DataModel.MovieData;
import com.android.fitternitytask.DataModel.MovieDetail;
import com.android.fitternitytask.DataModel.Review;
import com.android.fitternitytask.DataModel.ReviewInfo;
import com.android.fitternitytask.NetworkingCalls.ApiService;
import com.android.fitternitytask.R;
import com.android.fitternitytask.Utils.Constants;
import com.squareup.picasso.Picasso;

import java.net.SocketTimeoutException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailFragment extends Fragment {

    private Toolbar toolbar;
    private ImageView imgPoster;
    private TextView tvMovieTitle;
    private TextView tvMovieDate;
    private TextView tvMovieDuration;
    private TextView tvMovieOverview;
    private LinearLayout viewTrailers;
    private LinearLayout viewReviews;

    private ProgressBar pgTrailers;
    private ProgressBar pgReviews;

    private MovieData movieData;

    private ApiService apiService;

    public static MovieDetailFragment newInstance(MovieData movieData) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(MovieDetailFragment.class.getSimpleName(), movieData);
        MovieDetailFragment detailFragment = new MovieDetailFragment();
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_details, container, false);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        imgPoster = (ImageView) view.findViewById(R.id.img_poster);
        tvMovieTitle = (TextView) view.findViewById(R.id.movie_title);
        tvMovieDate = (TextView) view.findViewById(R.id.movie_date);
        tvMovieDuration = (TextView) view.findViewById(R.id.movie_duration);

        tvMovieOverview = (TextView) view.findViewById(R.id.movie_overview);
        viewReviews = (LinearLayout) view.findViewById(R.id.view_reviews);
        pgReviews = (ProgressBar) view.findViewById(R.id.pg_reviews);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.app_name);
            actionBar.setSubtitle("Movie Detail");
        }

        movieData = getArguments().getParcelable(MovieDetailFragment.class.getSimpleName());

        apiService = new ApiService();

        if (Constants.isConnected(getContext())) {
            if (movieData != null) {
                loadMovieDetail(movieData.getId());
                loadReviews(movieData.getId());
            }
        } else {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void loadMovieDetail(int id) {
        apiService.getMovieDetail(id, new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                MovieDetail movieDetail = (MovieDetail) response.body();

                if (movieDetail != null) {
                    Picasso.with(getContext())
                            .load(Constants.IMG_URL + movieDetail.getPosterPath())
                            .into(imgPoster);

                    tvMovieTitle.setText(movieDetail.getOriginalTitle());
                    tvMovieDate.setText(movieDetail.getReleaseDate());
                    tvMovieDuration.setText(movieDetail.getRuntime() + " Minutes");
                    tvMovieOverview.setText(movieDetail.getOverview());
                } else {
                    Toast.makeText(getContext(), "No Data!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(getContext(), "Request Timeout. Please try again!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Connection Error!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void loadReviews(int id) {
        pgReviews.setVisibility(View.VISIBLE);

        apiService.getReviews(id, new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Review review = (Review) response.body();

                if (review != null) {
                    showReviews(review.getResults());
                } else {
                    Toast.makeText(getContext(), "No Data!", Toast.LENGTH_LONG).show();
                }

                pgReviews.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(getContext(), "Request Timeout. Please try again!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Connection Error!", Toast.LENGTH_LONG).show();
                }

                pgReviews.setVisibility(View.GONE);
            }
        });
    }

    private void showReviews(List<ReviewInfo> reviewDatas) {
        viewReviews.removeAllViews();

        for (int i = 0; i < reviewDatas.size(); i++) {

            ReviewInfo reviewData = reviewDatas.get(i);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_review, viewReviews, false);

            TextView reviewers = (TextView) view.findViewById(R.id.reviewers);
            TextView content = (TextView) view.findViewById(R.id.content);

            reviewers.setText(reviewData.getAuthor());
            content.setText(reviewData.getContent().length() > 100 ?
                    reviewData.getContent().substring(0, 100) + "..." : reviewData.getContent());

            viewReviews.addView(view);
        }
    }
}


