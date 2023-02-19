package com.sanjit.sisu2.ui.login_register_user;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sanjit.sisu2.MainActivity;
import com.sanjit.sisu2.R;

import com.sanjit.sisu2.ui.Polio;
import com.sanjit.sisu2.ui.Setting;

import java.util.Locale;
import java.util.Objects;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private TextView forgotPassword,register,changeLanguage;
    private EditText loginEmail, loginPassword;
    private Button login;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String CHANNEL_ID = "SISU";

    private static final int REQUEST_CODE = 100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadLocale();

        setContentView(R.layout.activity_login);

        Drawable drawable= ResourcesCompat.getDrawable(getResources(),R.drawable.bachha1,null);
        BitmapDrawable bitmapDrawable= (BitmapDrawable) drawable;
        Bitmap largeIcon= bitmapDrawable.getBitmap();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(getApplicationContext(), Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent contentIntent = PendingIntent.getActivity(this, REQUEST_CODE, intent,PendingIntent.FLAG_IMMUTABLE);

        //BigText Style Notification
        Notification.BigPictureStyle bigPictureStyle = new Notification.BigPictureStyle()
                .bigPicture(((BitmapDrawable) drawable).getBitmap())
                .bigLargeIcon(largeIcon)
                .setBigContentTitle("Thank you for using SISU.")
                .setSummaryText("We are working hard to make it better");

        //Inbox Style Notification
        Notification.InboxStyle inboxStyle = new Notification.InboxStyle()
                .addLine("Thank you for using SISU.")
                .addLine("We are working hard to make it better")
                .addLine("A: Thank you for using SISU.")
                .addLine("A: We are working hard to make it better")
                .addLine("B: Thank you for using SISU.")
                .addLine("B: We are working hard to make it better")
                .setBigContentTitle("I: Thank you for using SISU.")
                .setSummaryText("I:We are working hard to make it better");

        Notification thank= null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            thank = new Notification.Builder(this)
                    .setLargeIcon(largeIcon)
                    .setContentTitle("Thank you for using SISU.")
                    .setContentText("We are working hard to make it better")
                    .setChannelId(CHANNEL_ID)
                    .setAutoCancel(false)
                    .setContentIntent(contentIntent)
                    .setStyle(bigPictureStyle)
                    .setSmallIcon(R.drawable.bachha1)
                    .build();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel(CHANNEL_ID,"SISU",NotificationManager.IMPORTANCE_DEFAULT));
        }
        notificationManager.notify(1,thank);

        changeLanguage =findViewById(R.id.Change_language);
        changeLanguage.setOnClickListener(this);


        register= findViewById(R.id.CreateAccount);
        register.setOnClickListener(this);

        login=findViewById(R.id.login);
        login.setOnClickListener(this);

        loginEmail =findViewById(R.id.loginemail);
        loginPassword =findViewById(R.id.loginpassword);

        progressBar=findViewById(R.id.loginprogressBar);
        progressBar.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();

        forgotPassword =findViewById(R.id.forgotpassword);
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
        switch (v.getId()){
            case R.id.CreateAccount:
                startActivity(new Intent(this, Register.class));
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
            final String [] listItems = {"English","नेपाली"};
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(Login.this);
            mBuilder.setTitle("Choose Language...");
            mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
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
                }
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

        String email= loginEmail.getText().toString().trim();
        String password= loginPassword.getText().toString().trim();

        if(email.isEmpty()){
            loginEmail.setError("Email is required");
            loginEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            loginPassword.setError("Password is required");
            loginPassword.requestFocus();
            return;
        }
        if(password.length()<8){
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

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //reading user_mode field of user document in users collection
                    DocumentReference docref = db.collection("Users").document(auth.getCurrentUser().getUid());

                    docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                            if(task.isSuccessful()){

                                DocumentSnapshot document = task.getResult();
                                if(document.exists()){

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

                                    if(user_mode.equals("Doctor")){

                                        Intent i = new Intent(Login.this, MainActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        Toast.makeText(Login.this, "Welcome Doctor!" , Toast.LENGTH_LONG).show();
                                        startActivity(i);
                                    }
                                    else if(user_mode.equals("Patient")){
                                        Intent i = new Intent(Login.this, MainActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        Toast.makeText(Login.this, "Welcome Patient!" , Toast.LENGTH_LONG).show();
                                        startActivity(i);
                                    }
                                    else if(user_mode.equals("Admin")){
                                        Intent i = new Intent(Login.this, MainActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        Toast.makeText(Login.this, "Welcome Admin!" , Toast.LENGTH_LONG).show();
                                        startActivity(i);
                                    }
                                }
                            }
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Login.this, "Error!" , Toast.LENGTH_LONG).show();
                                    login.setEnabled(true);
                                    login.setText("Login");
                                }
                            });

                }
                else{
                    Toast.makeText(Login.this, "Failed to login", Toast.LENGTH_SHORT).show();
                    login.setEnabled(true);
                    login.setText("Login");
                }
            }
        });


    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==1234){
//            Task<GoogleSignInAccount> task= GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                GoogleSignInAccount account=task.getResult(ApiException.class);
//
//                AuthCredential credential= GoogleAuthProvider.getCredential(account.getIdToken(),null);
//                FirebaseAuth.getInstance().signInWithCredential(credential)
//                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if(task.isSuccessful()){
//                                    isUser();
//                                }else{
//                                    Toast.makeText(Login.this,"Failed to login",Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        });
//
//            } catch (ApiException e) {
//                e.printStackTrace();
//            }
//        }
//    }

}

