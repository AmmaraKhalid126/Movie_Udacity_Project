package com.example.ammara.moviestage1;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Details extends AppCompatActivity {

    TextView movieTitle, overView, rating, releaseDate;
    ImageView img;
    ListView trailersListView;
    private List<Trailer> trailerList;
    ListView reviewsListView;
    private List<Reviews> reviewList;
    Button trailerBtn, reviewsBtn,favBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
//        Intent i=getIntent();
//        Movie movieObj=(Movie)i.getSerializableExtra("movieObject");
        final Movie movieObj = getMovieObject();
        movieTitle = (TextView) findViewById(R.id.movieTitle);
        overView = (TextView) findViewById(R.id.movieOverView);
        rating = (TextView) findViewById(R.id.userRating);
        releaseDate = (TextView) findViewById(R.id.releaseDate);
        img = (ImageView) findViewById(R.id.imagePoster);

        Picasso.with(getApplicationContext()).load(movieObj.getImageUrl().trim()).into(img);
        movieTitle.setText(movieObj.getTitle());
        overView.setText(movieObj.getSynopsis());
        rating.setText(movieObj.getUserRating());
        releaseDate.setText(movieObj.getReleaseDate());

       // trailersListView = (ListView) findViewById(R.id.trailersList);
        trailerBtn=(Button) findViewById(R.id.movie_trailers);
        trailerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTrailers();
            }
        });
        reviewsBtn=(Button) findViewById(R.id.movie_reiews);
        reviewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getReviews();
            }
        });


        favBtn=(Button) findViewById(R.id.fav);
        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favBtn.getText().equals("Mark as Favorite"))
                {
                    favBtn.setText("Remove from Favorite");
                    ContentValues contentValues=new ContentValues();
                    Movie m= getMovieObject();
                    contentValues.put(FavoriteContract.FavoriteEntry.COLUMN_MOVIEID, movieObj.getId());
                    contentValues.put(FavoriteContract.FavoriteEntry.COLUMN_PLOT_SYNOPSIS,movieObj.getSynopsis());
                    contentValues.put(FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH,movieObj.getImageUrl());
                    contentValues.put(FavoriteContract.FavoriteEntry.COLUMN_TITLE,movieObj.getTitle());
                    contentValues.put(FavoriteContract.FavoriteEntry.COLUMN_USERRATING,movieObj.getUserRating());
                    contentValues.put(FavoriteContract.FavoriteEntry.COLUMN_RELEASE_DATE,movieObj.getReleaseDate());

                    Uri uri=getContentResolver().insert(FavoriteContract.FavoriteEntry.CONTENT_URI,contentValues);
                    if(uri!=null)
                    {
                        Toast.makeText(getBaseContext(),uri.toString(),Toast.LENGTH_SHORT).show();
                    }
                    finish();


                }
                else
                {
                    favBtn.setText("Mark as Favorite");
                }
            }
        });


//        trailersListView.setOnTouchListener(new ListView.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int action = event.getAction();
//                switch (action) {
//                    case MotionEvent.ACTION_DOWN:
//                        // Disallow ScrollView to intercept touch events.
//                        v.getParent().requestDisallowInterceptTouchEvent(true);
//                        break;
//
//                    case MotionEvent.ACTION_UP:
//                        // Allow ScrollView to intercept touch events.
//                        v.getParent().requestDisallowInterceptTouchEvent(false);
//                        break;
//                }
//
//                // Handle ListView touch events.
//                v.onTouchEvent(event);
//                return true;
//            }
//        }');

        //trailersListView.setOnTouchListener(this);

//        trailersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Trailer trailer = trailerList.get(position);
//
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + trailer.getKey()));
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("video_id", trailer.getKey());
//
//                startActivity(intent);
//
//              //  Toast.makeText(getApplicationContext(), "You clicked " + trailer.getName(), Toast.LENGTH_SHORT).show();
//
//            }
//        });


    }

    private Movie getMovieObject() {
        Intent i = getIntent();
        return (Movie) i.getSerializableExtra("movieObject");

    }

    private void getTrailers() {
        Movie movieObj = getMovieObject();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<MovieTrailers> call = api.getTrailers(movieObj.getId(), getString(R.string.movies_api));
        call.enqueue(new Callback<MovieTrailers>() {
            @Override
            public void onResponse(Call<MovieTrailers> call, Response<MovieTrailers> response) {

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Details.this, android.R.layout.select_dialog_singlechoice);
                if (response.isSuccessful()) {
                    try {
                        trailerList = response.body().results;
                        //String[] trailers = new String[trailerList.size()];
                        for (int i = 0; i < trailerList.size(); i++) {
                           // trailers[i] = trailerList.get(i).getName();
                            arrayAdapter.add(trailerList.get(i).getName());
                        }
//                        trailersListView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, trailers));
//                        setListViewHeightBasedOnChildren(trailersListView);

                        AlertDialog.Builder builderSingle = new AlertDialog.Builder(Details.this);
                        builderSingle.setIcon(R.drawable.ic_launcher_background);
                        builderSingle.setTitle("Trailers:-");
                        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String strName = arrayAdapter.getItem(which);
                                AlertDialog.Builder builderInner = new AlertDialog.Builder(Details.this);
                                builderInner.setMessage(strName);
                                builderInner.setTitle("Your Selected Item is");
                                builderInner.setPositiveButton("Play", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,int which) {
                                        dialog.dismiss();
                                        Trailer trailer=new Trailer();
                                        for(int i=0; i<trailerList.size(); i++)
                                        {
                                            if(strName.equals(trailerList.get(i).getName()))
                                            {
                                                trailer=trailerList.get(i);
                                                break;
                                            }

                                        }

                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + trailer.getKey()));
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.putExtra("video_id", trailer.getKey());
                                        startActivity(intent);
                                    }
                                });
                                builderInner.show();
                            }
                        });
                        builderSingle.show();

                    } catch (NullPointerException exception) {
                        Log.d("Error", exception.getMessage());
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Some thing is wrong", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MovieTrailers> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void getReviews()
    {
        Movie movieObj = getMovieObject();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ReviewsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ReviewsApi api = retrofit.create(ReviewsApi.class);

        Call<MovieReviews> call = api.getReviews(movieObj.getId(), getString(R.string.movies_api));
        call.enqueue(new Callback<MovieReviews>() {
            @Override
            public void onResponse(Call<MovieReviews> call, Response<MovieReviews> response) {
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Details.this, android.R.layout.select_dialog_singlechoice);


                if (response.isSuccessful()) {
                    try {
                        reviewList = response.body().results;
                       // ArrayList<Reviews> reviews=new ArrayList<Reviews>();

                        if (reviewList.size()>0) {
                            for (int i = 0; i < reviewList.size(); i++) {

                                arrayAdapter.add(reviewList.get(i).getAuthor());
                            }
                            // ReviewsAdapter r=new ReviewsAdapter(getApplicationContext(), reviews);
                            // reviewsListView.setAdapter(r);
                            //setListViewHeightBasedOnChildren(reviewsListView);
                            AlertDialog.Builder builderSingle = new AlertDialog.Builder(Details.this);
                            builderSingle.setIcon(R.drawable.ic_launcher_background);
                            builderSingle.setTitle("Reviews:-");
                            builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    final String strName = arrayAdapter.getItem(which);
                                    AlertDialog.Builder builderInner = new AlertDialog.Builder(Details.this);
                                    builderInner.setMessage(strName);
                                    builderInner.setTitle("Your Selected Item is");
                                    builderInner.setPositiveButton("View Content", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            Reviews review = new Reviews();
                                            for (int i = 0; i < reviewList.size(); i++) {
                                                if (strName.equals(reviewList.get(i).getAuthor()) ) {
                                                    review = reviewList.get(i);
                                                    break;
                                                }
                                            }


                                            // show reviews contcect
                                            AlertDialog.Builder contentView=new AlertDialog.Builder(Details.this);
                                            contentView.setMessage(review.getContent());
                                            contentView.setTitle(review.getAuthor()+ "'s reviews");
                                            contentView.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                            contentView.show();
                                        }
                                    });
                                    builderInner.show();
                                }
                            });
                            builderSingle.show();
                        }
                        else
                        {
                            AlertDialog.Builder builderSingle = new AlertDialog.Builder(Details.this);
                            builderSingle.setIcon(R.drawable.ic_launcher_background);
                            builderSingle.setTitle("Reviews:-");
                            builderSingle.setMessage("No reviews for this movie have been found");
                            builderSingle.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builderSingle.show();


                        }

                    } catch (NullPointerException exception) {
                        Log.d("Error", exception.getMessage());
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Some thing is wrong", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MovieReviews> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }






}
