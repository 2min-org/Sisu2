package com.sanjit.sisu2.ui.login_register_user;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.sanjit.sisu2.R;

public class VerifyOtpActivity extends AppCompatActivity {

    private EditText inputOtp1, inputOtp2, inputOtp3, inputOtp4, inputOtp5, inputOtp6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        TextView textMobile = findViewById(R.id.textMobile);
        textMobile.setText(String.format(
                "+977-%s", getIntent().getStringExtra("mobile")
        ));

        inputOtp1 = findViewById(R.id.inputCode1);
        inputOtp2 = findViewById(R.id.inputCode2);
        inputOtp3 = findViewById(R.id.inputCode3);
        inputOtp4 = findViewById(R.id.inputCode4);
        inputOtp5 = findViewById(R.id.inputCode5);
        inputOtp6 = findViewById(R.id.inputCode6);

        setupOTPInputs();
    }

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