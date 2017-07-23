package com.ffl.flickster;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ffl.flickster.Adapters.MovieArrayAdapter;
import com.ffl.flickster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {
    ArrayList<Movie> movies;
    MovieArrayAdapter adaptermovie;
    ListView lvMovies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        lvMovies=(ListView) findViewById(R.id.lvMovies);
        movies = new ArrayList<>();
        adaptermovie = new MovieArrayAdapter(this,movies);
        lvMovies.setAdapter(adaptermovie);

        String url="https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(url,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJSONResults=null;
                try {
                    movieJSONResults=response.getJSONArray("results");
                    movies.addAll(Movie.fromJSONArray(movieJSONResults));
                    adaptermovie.notifyDataSetChanged();
                    Log.d("DEBUG",movies.toString());
                }catch (JSONException e){e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });


        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String title=movies.get(position).getOriginalTitle();
                final String overview=movies.get(position).getOverview();
                final String dater=movies.get(position).getRelease_date();
                final double vote=movies.get(position).getVote()/2;
                final String imagdet=movies.get(position).getPosterPath();
                Intent intent=new Intent(view.getContext(),DetailsMovieActivity.class);
                intent.putExtra("TITLE",title);
                intent.putExtra("OVERVIEW",overview);
                intent.putExtra("DATE",dater);
                intent.putExtra("VOTE",vote);
                intent.putExtra("IMAGE",imagdet);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        adaptermovie.notifyDataSetChanged();
    }
}
