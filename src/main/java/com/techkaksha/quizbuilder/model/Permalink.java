package com.techkaksha.quizbuilder.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class Permalink {

    @Id
    private String id;
    @Column(name = "short_url")
    private String shortURL;
    @Column(name = "long_url")
    private String longURL;
    public Permalink(){}

    public Permalink(String id, String shortURL, String longURL) {
        this.id = id;
        this.shortURL = shortURL;
        this.longURL = longURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortURL() {
        return shortURL;
    }

    public void setShortURL(String shortURL) {
        this.shortURL = shortURL;
    }

    public String getLongURL() {
        return longURL;
    }

    public void setLongURL(String longURL) {
        this.longURL = longURL;
    }


}
