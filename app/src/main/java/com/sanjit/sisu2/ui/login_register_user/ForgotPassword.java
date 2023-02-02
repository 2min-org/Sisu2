package com.sanjit.sisu2.ui.login_register_user;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.sanjit.sisu2.R;

public class ForgotPassword extends AppCompatActivity {

    private EditText forgotemail;
    private ProgressBar progressBar;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgotemail=(EditText)findViewById(R.id.forgotemail);
        Button resetpassword = (Button) findViewById(R.id.resetpassword);
        progressBar=(ProgressBar)findViewById(R.id.progressBar2);

        mAuth= FirebaseAuth.getInstance();

        resetpassword.setOnClickListener(v -> resetPassword());
    }

    private void resetPassword(){
        String email=forgotemail.getText().toString().trim();
        if(email.isEmpty()){
            forgotemail.setError("Email is required");
            forgotemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            forgotemail.setError("Please provide valid email");
            forgotemail.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ForgotPassword.this,"Check your email to reset your password",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(ForgotPassword.this,"Try again! Something wrong happened",Toast.LENGTH_LONG).show();
            }
        });


    }
}