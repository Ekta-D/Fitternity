package com.android.fitternitytask;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.fitternitytask.DataModel.MovieData;
import com.android.fitternitytask.fragment.MovieDetailFragment;

public class MovieDetailActivity extends AppCompatActivity {

    public static void start(Context context, MovieData movieData) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.class.getSimpleName(), movieData);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        MovieData movieData = getIntent().getParcelableExtra(MovieDetailActivity.class.getSimpleName());

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container1, MovieDetailFragment.newInstance(movieData))
                    .commit();
        }
    }
}
