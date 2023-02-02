package com.sanjit.sisu2.ui.Patientappointment;


import android.widget.Button;

public class book_doctor_model {
    private Boolean isBooked;
    private String name;
    private String phone;
    private String photo_url;
    private final String specializations;
    private final String u_id;

    public book_doctor_model(String name, String phone, String specializations, String u_id, Boolean isBooked,String photo_url)  {
        this.name = name;
        this.phone = phone;
        this.photo_url = photo_url;
        this.specializations = specializations;
        this.u_id = u_id;
        this.isBooked = isBooked;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPhoto() {
        return photo_url;
    }

    public String getSpecializations() {
        return specializations;
    }

    public String getU_id() {
        return u_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getBooked() {
        return isBooked;
    }

    public void setBooked(Boolean booked) {
        isBooked = booked;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPhoto_url (String photo_url) {
        this.photo_url = photo_url;
    }
}
