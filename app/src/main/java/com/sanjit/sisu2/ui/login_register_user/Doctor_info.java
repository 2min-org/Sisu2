package com.sanjit.sisu2.ui.login_register_user;

import java.util.List;

public class Doctor_info {
    public String FullName,Email, Specialization,UId,Phone,ProfilePic;
    private List <String> appointment_id;


    public Doctor_info(String fullName, String email,String spec,List<String> appointment_id,String UId,String PhotoUrl) {
        this.FullName=fullName;
        this.Email=email;
        this.Specialization =spec;
        this.appointment_id=appointment_id;
        this.UId=UId;
        this.ProfilePic=PhotoUrl;
    }
}
