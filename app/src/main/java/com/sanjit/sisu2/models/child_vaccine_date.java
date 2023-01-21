package com.sanjit.sisu2.models;

public class child_vaccine_date {
    private String name;
    private custom_date custom_date;

    public child_vaccine_date(String name, custom_date custom_date) {
        this.name = name;
        this.custom_date = custom_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public custom_date getCustom_date() {
        return custom_date;
    }

    public void setCustom_date(custom_date custom_date) {
        this.custom_date = custom_date;
    }
}
