package com.sanjit.sisu2.ui.login_register_user;

public class User {
    public String Fullname,Email,Birthday,Telephone,Gender,Address;

    public User(String fullname, String email){

    }

    public User(String fullname, String email, String birthday, String telephone,String gender,String address) {
        this.Fullname = fullname;
        this.Email = email;
        this.Birthday = birthday;
        this.Telephone = telephone;
        this.Gender=gender;
        this.Address=address;
    }
}