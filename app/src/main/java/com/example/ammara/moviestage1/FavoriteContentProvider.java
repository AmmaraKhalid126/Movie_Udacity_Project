package com.example.ammara.moviestage1;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.net.URI;

import static com.example.ammara.moviestage1.FavoriteContract.FavoriteEntry.TABLE_NAME;

public class FavoriteContentProvider extends ContentProvider {
    FavoriteDbHelper favoriteDbHelper;
    public static final int FAVORITES=100;
    public static final int FAVORITE_WITH_ID=101;

    public static final UriMatcher sUriMatcher=buildUriMatcher();

    public static UriMatcher buildUriMatcher()
    {
        UriMatcher uriMatcher= new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(FavoriteContract.Authority,FavoriteContract.PATH_FAVORITES,FAVORITES);
        uriMatcher.addURI(FavoriteContract.Authority,FavoriteContract.PATH_FAVORITES + "/#",FAVORITE_WITH_ID);
        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        Context context= getContext();
        favoriteDbHelper= new FavoriteDbHelper(context);
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

         final SQLiteDatabase db=favoriteDbHelper.getWritableDatabase();
         int match=sUriMatcher.match(uri);

        Uri returnUri;

        switch(match){
            case FAVORITES :
                long id=db.insert(TABLE_NAME, null, values);
                if(id>0)
                {
                    returnUri= ContentUris.withAppendedId(FavoriteContract.FavoriteEntry.CONTENT_URI,id);
                }
                else{
                    throw new android.database.sqlite.SQLiteException("Failed to save favorites"+uri);
                }
                break;
                default:
                    throw new UnsupportedOperationException("Unknown Uri "+uri);


        }
        getContext().getContentResolver().notifyChange(uri,null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
