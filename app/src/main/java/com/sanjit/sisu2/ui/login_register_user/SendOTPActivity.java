package com.sanjit.sisu2.ui.login_register_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.sanjit.sisu2.R;

public class SendOTPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otpactivity);

        final EditText inputmobile = findViewById(R.id.inputmobile);
        Button buttonGetOTP = findViewById(R.id.btnsendotp);
        ProgressBar progressBar = findViewById(R.id.progressBar_Send_otp);

        //condition of mobile number length and validity
        buttonGetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!inputmobile.getText().toString().trim().isEmpty()) {
                    if ((inputmobile.getText().toString().trim()).length() == 10) {

                        progressBar.setVisibility(View.VISIBLE);
                        buttonGetOTP.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+977" + inputmobile.getText().toString(),
                                60,
                                java.util.concurrent.TimeUnit.SECONDS,
                                SendOTPActivity.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBar.setVisibility(View.GONE);
                                        buttonGetOTP.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBar.setVisibility(View.GONE);
                                        buttonGetOTP.setVisibility(View.VISIBLE);
                                        Toast.makeText(SendOTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBar.setVisibility(View.GONE);
                                        buttonGetOTP.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(getApplicationContext(), VerifyOtpActivity.class);
                                        intent.putExtra("mobile", inputmobile.getText().toString());
                                        intent.putExtra("verificationId", verificationId);
                                        startActivity(intent);
                                    }

                                }
                        );
                    } else {
                        Toast.makeText(SendOTPActivity.this, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SendOTPActivity.this, "Please enter mobile number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}