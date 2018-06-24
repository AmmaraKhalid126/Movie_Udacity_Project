package com.example.ammara.moviestage1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MovieReviews implements Serializable {
    int id;
    List<Reviews> results;

    public MovieReviews() {
        results = new ArrayList<>();
    }

    public MovieReviews(int id, List<Reviews> results) {
        this.id = id;
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Reviews> getResults() {
        return results;
    }

    public void setResults(List<Reviews> results) {
        this.results = results;
    }
}