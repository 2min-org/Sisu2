package com.sanjit.sisu2.ui.VaccineTracker;

public class Vaccine_cardview_model
{
    public int color;
    int image;
    boolean isSwitchOn;
    String vaccineName, vaccineDate;

    public Vaccine_cardview_model(int image, String vaccineName, String vaccineDate, boolean isSwitchOn) {
        this.image = image;
        this.vaccineName = vaccineName;
        this.vaccineDate = vaccineDate;
        this.isSwitchOn = isSwitchOn;

    }

    public Vaccine_cardview_model(boolean isSwitchOn) {
        this.isSwitchOn = isSwitchOn;
    }
}
