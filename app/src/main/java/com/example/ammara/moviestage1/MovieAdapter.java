package com.example.ammara.moviestage1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieAdapter extends BaseAdapter {
    private final Context mContext;
    private final Movie[] movies;

    public MovieAdapter(Context context, Movie[] movies) {
        this.mContext = context;
        this.movies = movies;
    }
    @Override
    public int getCount() {
        return movies.length;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Movie movie = movies[position];
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.movie_layout, null);
        }
        final ImageView imageView =
                (ImageView)convertView.findViewById(R.id.imageview_cover_art);
        final TextView nameTextView =
                (TextView)convertView.findViewById(R.id.textview_movie_name);


        Picasso.with(mContext).load(movie.getImageUrl()).resize(300,500).into(imageView);

        nameTextView.setText(movie.getName());
        return convertView;
    }
}