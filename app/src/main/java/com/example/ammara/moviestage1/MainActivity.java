package com.example.ammara.moviestage1;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private GridView gridView;
    private MovieAdapter mMovieAdapter;
    ArrayList<Movie> mMovies=new ArrayList<>();
    Spinner spinner;
    String sortOrder;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {// setHasOptionsMenu(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.movieGrids);

        spinner=(Spinner) findViewById(R.id.sortSpinner);
        spinner.setOnItemSelectedListener(this);
        List<String> order=new ArrayList<String>();
        order.add("Popularity");
        order.add("Rating");

        ArrayAdapter<String> sortAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,order);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(sortAdapter);

        new FetchMovieTask().execute();

        mMovieAdapter = new MovieAdapter(getApplicationContext(), mMovies);
        gridView.setAdapter(mMovieAdapter);
        Intent i= new Intent(this, Details.class);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();
                Intent i  = new Intent(view.getContext(), Details.class);
                i.putExtra("movieObject", mMovies.get(position));
                startActivity(i);

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sortOrder = parent.getItemAtPosition(position).toString();
        new FetchMovieTask().execute();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public class FetchMovieTask extends AsyncTask<String, Void, Movie[]> {


        final String LOG_TAG = this.getClass().getSimpleName();

        @Override
        protected Movie[] doInBackground(String... params) {

            //Retrieve the Popular Movies Json String
            String moviesJsonString = getMoviesJsonString();

            //Parse the Json String to get important data
            try {
                return getPopularMoviesInfo(moviesJsonString);

            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Movie[] movies) {
            if (movies != null) {
                mMovieAdapter.clear();
                mMovieAdapter.addAll(movies);
            }
        }


        private String getMoviesJsonString() {
            HttpURLConnection urlConnection = null;
            BufferedReader bufferedReader = null;
            String moviesJsonString = null;
            String sort="";
//            if (sortOrder=="Popularity")
//            {
//                sort="popularity";
//            }
//
//            else if(sortOrder=="Rating")
//            {
//                sort="vote_average";
//            }

            try {
                 String MOVIES_BASE_URL;

                if(sortOrder=="Popularity")
                {
                    //Set up the URI
                     MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/popular";
                }
                else
                {
                    //Set up the URI
                    MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/top_rated";
                }


               // final String SORT_PARAM = "sort_by";
                final String API_PARAM = "api_key";

                Uri moviesUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                       // .appendQueryParameter(SORT_PARAM, sort)
                        .appendQueryParameter(API_PARAM, getString(R.string.movies_api))
                        .build();

                //Open the connection
                URL url = new URL(moviesUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                //Read the input stream into a string
                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream == null) {
                    return null;
                }

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                StringBuffer buffer = new StringBuffer();
                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    //Stream was empty
                    return null;
                }

                moviesJsonString = buffer.toString();

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error", e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            return moviesJsonString;
        }


        private Movie[] getPopularMoviesInfo(String jsonString) throws JSONException {

            //Base URL of thumbnail
            final String THUMBNAIL_BASE_URL = "http://image.tmdb.org/t/p/w185";

            //The attribute names we are interested in the JSON String
            final String M_RESULTS = "results";
            final String M_THUMBNAIL = "backdrop_path";
            final String M_TITLE = "original_title";
            final String M_ID = "id";
            final String M_SYPNOSIS = "overview";
            final String M_USER_RATING = "vote_average";
            final String M_RELEASE_DATE = "release_date";


            //Convert Json String to Json Object
            JSONObject moviesJsonObject = new JSONObject(jsonString);

            //Get the Json Array
            JSONArray moviesJsonArray = moviesJsonObject.getJSONArray(M_RESULTS);

            Movie[] movies = new Movie[moviesJsonArray.length()];
            for (int i = 0; i < moviesJsonArray.length(); i++) {
                JSONObject movie = moviesJsonArray.getJSONObject(i);

                movies[i] = new Movie(THUMBNAIL_BASE_URL + movie.getString(M_THUMBNAIL),
                        movie.getString(M_TITLE),
                        movie.getString(M_ID),
                        movie.getString(M_SYPNOSIS),
                        movie.getString(M_USER_RATING),
                        movie.getString(M_RELEASE_DATE));
                mMovies.add(movies[i]);
            }

            return movies;

        }

    }




}
