package com.sanjit.sisu2.ui.add_child;

import com.sanjit.sisu2.models.child_vaccine_date;
import com.sanjit.sisu2.models.custom_date;

public class Child {
    private String name;
    private custom_date custom_date;
    private String url;
    private child_vaccine_date[] child_vaccine_dates;

    public Child(String name, custom_date custom_date, String url) {
        this.name = name;
        this.custom_date = custom_date;
        this.url = url;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public custom_date getDob() {
        return custom_date;
    }

    public void setDob(custom_date custom_date) {
        this.custom_date = custom_date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
