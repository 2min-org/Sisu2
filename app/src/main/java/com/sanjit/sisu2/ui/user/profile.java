package com.sanjit.sisu2.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sanjit.sisu2.MainActivity;
import com.sanjit.sisu2.R;
import com.sanjit.sisu2.ui.login_register_user.ForgotPassword;

public class profile extends AppCompatActivity implements View.OnClickListener{
    private TextView nameTxtView,emailTxtView,phoneTxtView,addressTxtView,genderTxtView,birthdayTxtView,passwordTxtView;
    private ImageView photoImageView;


    private FirebaseUser user;
    private DatabaseReference mDatabase;

    private Button logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mero_profile);

        getSupportActionBar().hide();
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);


        logout = (Button) findViewById(R.id.logout);

        passwordTxtView = (TextView) findViewById(R.id.changepassword);
        nameTxtView = (TextView) findViewById(R.id.aayoname);
        emailTxtView = (TextView) findViewById(R.id.aayomail);
        phoneTxtView = (TextView) findViewById(R.id.aayotelephone);
        addressTxtView = (TextView) findViewById(R.id.aayoaddress);
        genderTxtView = (TextView) findViewById(R.id.aayogender);
        birthdayTxtView = (TextView) findViewById(R.id.aayobirthday);



        passwordTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile.this, ForgotPassword.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        //Show all data
        showallUserData();

    }

    private void showallUserData() {
        Intent intent=getIntent();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        String user_fullname=intent.getStringExtra("Fullname");
        String user_email=intent.getStringExtra("Email");
        String user_phone=intent.getStringExtra("Telephone");
        String user_address=intent.getStringExtra("Address");
        String user_gender= intent.getStringExtra("Gender");
        String user_birthday=intent.getStringExtra("Birthday");

        nameTxtView.setText(user_fullname);
        emailTxtView.setText(user_email);
        phoneTxtView.setText(user_phone);
        addressTxtView.setText(user_address);
        genderTxtView.setText(user_gender);
        birthdayTxtView.setText(user_birthday);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}