package com.example.ammara.moviestage1;

import android.net.Uri;
import android.provider.BaseColumns;

public class FavoriteContract {

    public static final String Authority="com.example.ammara.moviestage1";

    public static final Uri BASE_CONTENT_URI=Uri.parse("content://"+ Authority);

    public static final String PATH_FAVORITES="favorite";
    public static final class FavoriteEntry implements BaseColumns {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();
        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_MOVIEID = "movieid";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_USERRATING = "userrating";
        public static final String COLUMN_POSTER_PATH = "posterpath";
        public static final String COLUMN_PLOT_SYNOPSIS = "overview";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";


    }
}
