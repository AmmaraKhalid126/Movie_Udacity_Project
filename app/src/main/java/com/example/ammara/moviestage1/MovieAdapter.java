package com.example.ammara.moviestage1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends ArrayAdapter {
    private final Context context;
    private final ArrayList<Movie>movies;
    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        super(context,0,movies);
        this.context = context;
        this.movies=movies;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.i("M",movies.get(position).getImageUrl());
        View rowView = convertView;
        ViewHolder holder = new ViewHolder();
        if(rowView == null) {
            //Inflate the XML based Layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.movie_layout, parent, false);
            //Get the ImageView
            holder.movieThumbnail = (ImageView) rowView.findViewById(R.id.gridItemImg);
            rowView.setTag(holder);
        }
        else
        {
            holder=(ViewHolder)convertView.getTag();

        }
          //Load Image into the ImageView
                Picasso.with(context).load(movies.get(position).getImageUrl().trim()).into(holder.movieThumbnail);
                TextView t=(TextView) rowView.findViewById(R.id.textview_movie_name);
                t.setText(movies.get(position).getTitle());

            Log.v("Populating", movies.get(position).getImageUrl());
        return rowView;
    }
    @Override
    public int getCount() {
        return super.getCount();
    }
    public int getPosition(Movie item) {
        return super.getPosition(item);
    }
  public void add (Movie m)
    {
        movies.add(m);
    }
    public void clear()
    {
        movies.clear();
    }
    static class ViewHolder
    {
        ImageView movieThumbnail;
    }
}
