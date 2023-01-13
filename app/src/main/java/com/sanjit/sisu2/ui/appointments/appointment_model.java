package com.sanjit.sisu2.ui.appointments;



public class appointment_model {
    private String name;
    private String phone;
    private String photo_url;

    public appointment_model(String name, String phone, String photo_url) {
        this.name = name;
        this.phone = phone;
        this.photo_url = photo_url;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPhoto_url (String photo_url) {
        this.photo_url = photo_url;
    }
}
