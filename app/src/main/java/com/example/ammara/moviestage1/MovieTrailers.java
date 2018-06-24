package com.example.ammara.moviestage1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MovieTrailers implements Serializable{
    int id;
    List<Trailer> results;

    public MovieTrailers() {
        results = new ArrayList<>();
    }

    public MovieTrailers(int id, List<Trailer> results) {
        this.id = id;
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Trailer> getResults() {
        return results;
    }

    public void setResults(List<Trailer> results) {
        this.results = results;
    }
}
