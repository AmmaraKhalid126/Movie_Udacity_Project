package com.example.ammara.moviestage1;

import java.io.Serializable;

public class Trailer  implements Serializable {
    String id;
    String name;
    String key;

    public Trailer()
    {

    }

    public Trailer(String id, String name, String key) {
        this.id = id;
        this.name = name;
        this.key = key;

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }



}