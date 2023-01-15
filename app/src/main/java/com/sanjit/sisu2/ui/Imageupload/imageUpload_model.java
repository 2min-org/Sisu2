package com.sanjit.sisu2.ui.Imageupload;

public class imageUpload_model {
    private String name;
    private String url;

    public imageUpload_model(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
