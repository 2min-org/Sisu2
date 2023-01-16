package com.sanjit.sisu2.ui.Imageupload;


import com.google.firebase.Timestamp;

public class basic_model {

    private String name;
    private String url;
    private String description;
    private Timestamp timestamp;


    public basic_model(String name, String description, String url, Timestamp timestamp) {
        this.name = name;
        this.url = url;
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
