package com.example.ammara.moviestage1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ReviewsAdapter extends ArrayAdapter<Reviews> {
    private Context mContext;
    private List<Reviews> reviewsList;

    public ReviewsAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<Reviews> list)

    {
        super(context, 0, list);
        mContext = context;
        reviewsList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.review_layout,parent,false);

        Reviews review = reviewsList.get(position);


        TextView author = (TextView) listItem.findViewById(R.id.authorTextView);
        author.setText(review.getAuthor());

        TextView content = (TextView) listItem.findViewById(R.id.contentTextView);
        content.setText(review.getContent());

        return listItem;
    }
}


