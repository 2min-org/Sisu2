package com.sanjit.sisu2.ui.login_register_user;

public class User {
    public String Fullname,Email,Birthday,Telephone,Gender,Address,user_mode,spec;

    public User(String fullname, String email){

    }

    public User(String fullname, String email, String birthday, String telephone,String gender,String address,String user_mode) {
        this.Fullname = fullname;
        this.Email = email;
        this.Birthday = birthday;
        this.Telephone = telephone;
        this.Gender=gender;
        this.Address=address;
        this.user_mode=user_mode;
        this.spec=null;
    }
    public void setspec(String spec){
        this.spec=spec;
    }

}