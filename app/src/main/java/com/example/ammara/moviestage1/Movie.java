package com.example.ammara.moviestage1;

import java.io.Serializable;

public class Movie implements Serializable{

    private String imageUrl;
    private String title;
    private String Id;
    private String synopsis;
    private String userRating;
    private String releaseDate;

    public  Movie()
    {

    }

    public Movie(String imageUrl, String title, String id, String synopsis, String userRating, String releaseDate) {
        this.imageUrl = imageUrl;
        this.title = title;
        Id = id;
        this.synopsis = synopsis;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
    }
    public String getSynopsis() {
        return synopsis;
    }
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
    public String getUserRating() {
        return userRating;
    }
    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }
    public String getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}

