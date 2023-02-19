package com.sanjit.sisu2.ui.login_register_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.sanjit.sisu2.R;
import com.sanjit.sisu2.ui.home.HomeFragment;

public class VerifyOtpActivity extends AppCompatActivity {

    EditText inputOtp1, inputOtp2, inputOtp3, inputOtp4, inputOtp5, inputOtp6;
    Button buttonVerify;
    String codeBySystem;

    //true after every 60 seconds
    private boolean resendEnable = false;

    //resend time in seconds
    private int resendTime = 60;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        TextView textMobile = findViewById(R.id.textMobile);
        buttonVerify = findViewById(R.id.btnverifyotp);

        TextView resendlabelBtn = findViewById(R.id.textResendOTP);

        textMobile.setText(String.format(
                "+977-%s", getIntent().getStringExtra("mobile")
        ));

        inputOtp1 = findViewById(R.id.inputCode1);
        inputOtp2 = findViewById(R.id.inputCode2);
        inputOtp3 = findViewById(R.id.inputCode3);
        inputOtp4 = findViewById(R.id.inputCode4);
        inputOtp5 = findViewById(R.id.inputCode5);
        inputOtp6 = findViewById(R.id.inputCode6);

        codeBySystem=getIntent().getStringExtra("verificationId"); // From SendOTPActivity.java
        final ProgressBar progressBar = findViewById(R.id.progressBar_Verify_otp);

        setupOTPInputs();

        startCounter();

        //any space of otp shouldnot be empty
        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!inputOtp1.getText().toString().trim().isEmpty()
                        && !inputOtp2.getText().toString().trim().isEmpty()
                        && !inputOtp3.getText().toString().trim().isEmpty()
                        && !inputOtp4.getText().toString().trim().isEmpty()
                        && !inputOtp5.getText().toString().trim().isEmpty()
                        && !inputOtp6.getText().toString().trim().isEmpty()){
                    String code = inputOtp1.getText().toString() +
                            inputOtp2.getText().toString() +
                            inputOtp3.getText().toString() +
                            inputOtp4.getText().toString() +
                            inputOtp5.getText().toString() +
                            inputOtp6.getText().toString();

                    if(codeBySystem!=null){
                        progressBar.setVisibility(View.VISIBLE);

                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(
                                codeBySystem, code
                        );
                        FirebaseAuth.getInstance().signInWithCredential(credential)
                                .addOnCompleteListener(task -> {
                                    if(task.isSuccessful()){
                                        Toast.makeText(VerifyOtpActivity.this, "OTP Verified.", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), Register.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        Toast.makeText(VerifyOtpActivity.this, "The verification code entered was invalid", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                });
                    }else{
                        Toast.makeText(VerifyOtpActivity.this, "Please check Internet Connection.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(VerifyOtpActivity.this, "Please fill all the spaces.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        resendlabelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(resendlabelBtn.isEnabled()){
                   //handle your resend code here.
                   //start count down timer
                   startCounter();
               }
            }
        });
    }


    private void startCounter(){

        resendEnable = false;
        buttonVerify.setTextColor(Color.parseColor("#FF0000"));

        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                buttonVerify.setText("Resend OTP ("+(+millisUntilFinished/6000)+")");
                buttonVerify.setEnabled(false);
                resendEnable = false;
            }

            @Override
            public void onFinish() {
                buttonVerify.setText("Resend OTP");
                buttonVerify.setEnabled(true);
                resendEnable = true;
                buttonVerify.setTextColor(getResources().getColor(R.color.active));
            }
        }.start();
    }

    //for otp input to move to next input as soon as user enters one digit of otp
    private void setupOTPInputs(){
        inputOtp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputOtp2.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        inputOtp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputOtp3.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        inputOtp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputOtp4.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        inputOtp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputOtp5.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        inputOtp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputOtp6.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}