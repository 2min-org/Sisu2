package com.sanjit.sisu2.ui.login_register_user;

import java.util.List;

public class Doctor_info {
    public String FullName,Email, Specialization;
    private List <String> appointment_id;

    public Doctor_info(String fullName, String email,String spec,List<String> appointment_id) {
        this.FullName=fullName;
        this.Email=email;
        this.Specialization =spec;
        this.appointment_id=appointment_id;
    }
}
