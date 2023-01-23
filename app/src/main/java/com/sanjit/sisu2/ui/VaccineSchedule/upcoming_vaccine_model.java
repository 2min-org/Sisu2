package com.sanjit.sisu2.ui.VaccineSchedule;

public class upcoming_vaccine_model {
    private String vaccine_name;
    private String vaccine_day;
    private String vaccine_month_year;
    private String vaccine_type;
    private String vaccine_month;
    private String vaccine_year;

    public upcoming_vaccine_model(String vaccine_name, String vaccine_day, String vaccine_month_year, String vaccine_type) {
        this.vaccine_name = vaccine_name;
        this.vaccine_day = vaccine_day;
        this.vaccine_month_year = vaccine_month_year;
        this.vaccine_type = vaccine_type;

        String[] parts = vaccine_month_year.split(" ");
        this.vaccine_month = parts[0];
        this.vaccine_year = parts[1];
    }

    public String getVaccine_day() {
        return vaccine_day;
    }

    public void setVaccine_day(String vaccine_day) {
        this.vaccine_day = vaccine_day;
    }

    public String getVaccine_month_year() {
        return vaccine_month_year;
    }

    public void setVaccine_month_year(String vaccine_month_year) {
        this.vaccine_month_year = vaccine_month_year;
    }

    public String getVaccine_name() {
        return vaccine_name;
    }

    public void setVaccine_name(String vaccine_name) {
        this.vaccine_name = vaccine_name;
    }

    public String getVaccine_type() {
        return vaccine_type;
    }

    public void setVaccine_type(String vaccine_type) {
        this.vaccine_type = vaccine_type;
    }
}
