package com.sanjit.sisu2.ui.login_register_user;

import android.util.Log;

import java.util.ArrayList;

public class User {
    public String user_id,Fullname,Email,Birthday,Telephone,Gender,Address,user_mode,ProfilePic,Specialization;
    public ArrayList<String> appointments = new ArrayList<>();

    public User(String user_id,String fullname, String email, String birthday, String telephone,String gender,String address
            ,String user_mode,String ProfilePic,String Specialization,ArrayList<String> appointments) {
        this.user_id=user_id;
        this.Fullname = fullname;
        this.Email = email;
        this.Birthday = birthday;
        this.Telephone = telephone;
        this.Gender=gender;
        this.Address=address;
        this.user_mode=user_mode;
        this.ProfilePic= ProfilePic;
        this.Specialization=Specialization;
        this.appointments=appointments;
    }
    public void set_ProfilePic(String setProfilePic) {
        this.ProfilePic = setProfilePic;

    }
}