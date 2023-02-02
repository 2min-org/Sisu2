package com.sanjit.sisu2.ui.login_register_user;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sanjit.sisu2.MainActivity;
import com.sanjit.sisu2.R;

import java.util.Locale;
import java.util.Objects;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText loginEmail, loginPassword;
    private Button login;
    private ProgressBar progressBar;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadLocale();

        setContentView(R.layout.activity_login);

        TextView changeLanguage = findViewById(R.id.Change_language);
        changeLanguage.setOnClickListener(this);


        TextView register = findViewById(R.id.CreateAccount);
        register.setOnClickListener(this);

        login = findViewById(R.id.login);
        login.setOnClickListener(this);

        loginEmail = findViewById(R.id.loginemail);
        loginPassword = findViewById(R.id.loginpassword);

        progressBar = findViewById(R.id.loginprogressBar);
        progressBar.setVisibility(View.GONE);
        TextView forgotPassword = findViewById(R.id.forgotpassword);
        forgotPassword.setOnClickListener(this);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Intent i = new Intent(Login.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.CreateAccount:
                startActivity(new Intent(this, SendOTPActivity.class));
                break;
            case R.id.login:
                userLogin();
                break;
            case R.id.forgotpassword:
                startActivity(new Intent(this, ForgotPassword.class));
                break;
            case R.id.Change_language:
                showChangeLanguageDialog();
                break;
        }
    }

    private void showChangeLanguageDialog() {
        final String[] listItems = {"English", "नेपाली"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Login.this);
        mBuilder.setTitle("Choose Language...");
        mBuilder.setSingleChoiceItems(listItems, -1, (dialogInterface, i) -> {
            if (i == 0) {
                //English
                setLocale("en");
                recreate();
            } else if (i == 1) {
                //Hindi
                setLocale("hi");
                recreate();
            }
            //dismiss alert dialog when language selected
            dialogInterface.dismiss();
        });
        AlertDialog mDialog = mBuilder.create();
        //show alert dialog
        mDialog.show();

    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        //save data to shared preferences
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    private void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);

    }

    private void userLogin() {

        String email = loginEmail.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        if (email.isEmpty()) {
            loginEmail.setError("Email is required");
            loginEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            loginPassword.setError("Password is required");
            loginPassword.requestFocus();
            return;
        }
        if (password.length() < 8) {
            loginPassword.setError("Minimum length of password should be 8");
            loginPassword.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginEmail.setError("Please provide valid email");
            loginEmail.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        login.setEnabled(false);
        login.setText("Logging in...");

        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                //reading user_mode field of user document in users collection
                DocumentReference docref = db.collection("Users").document(Objects.requireNonNull(auth.getCurrentUser()).getUid());

                docref.get().addOnCompleteListener(task1 -> {

                    if (task1.isSuccessful()) {

                        DocumentSnapshot document = task1.getResult();
                        if (document.exists()) {

                            String user_mode = document.getString("user_mode");

                            //Setting up shared preferences

                            SharedPreferences User = getSharedPreferences("User", MODE_PRIVATE);
                            SharedPreferences.Editor editor = User.edit();

                            editor.putString("Email", email);
                            editor.putString("FullName", document.getString("Fullname"));
                            editor.putString("User_id", auth.getCurrentUser().getUid());
                            editor.putString("ProfilePic", document.getString("ProfilePic"));
                            editor.putString("User_mode", document.getString("user_mode"));
                            editor.putString("Specialization", document.getString("Specialization"));
                            editor.putString("Phone", document.getString("Telephone"));
                            editor.apply();

                            login.setEnabled(true);
                            login.setText("Login");
                            //end of shared preferences

                            switch (Objects.requireNonNull(user_mode)) {
                                case "Doctor": {

                                    Intent i = new Intent(Login.this, MainActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    Toast.makeText(Login.this, "Welcome Doctor!", Toast.LENGTH_LONG).show();
                                    startActivity(i);
                                    break;
                                }
                                case "Patient": {
                                    Intent i = new Intent(Login.this, MainActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    Toast.makeText(Login.this, "Welcome Patient!", Toast.LENGTH_LONG).show();
                                    startActivity(i);
                                    break;
                                }
                                case "Admin": {
                                    Intent i = new Intent(Login.this, MainActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    Toast.makeText(Login.this, "Welcome Admin!", Toast.LENGTH_LONG).show();
                                    startActivity(i);
                                    break;
                                }
                            }
                        }
                    }
                })
                        .addOnFailureListener(e -> {
                            Toast.makeText(Login.this, "Error!", Toast.LENGTH_LONG).show();
                            login.setEnabled(true);
                            login.setText("Login");
                        });

            } else {
                Toast.makeText(Login.this, "Failed to login", Toast.LENGTH_SHORT).show();
                login.setEnabled(true);
                login.setText("Login");
            }
        });


    }
}
