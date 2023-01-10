package com.sanjit.sisu2.ui.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.sanjit.sisu2.R;
import com.sanjit.sisu2.ui.login_register_user.Login;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler handler = new Handler();

        SharedPreferences preferences = getSharedPreferences("PREFERENCES", MODE_PRIVATE);
        String FirstTime = preferences.getString("FirstTimeInstall", "");
        if (!FirstTime.equals("No")) {
            handler.postDelayed(new Runnable() {
                @Override

                public void run() {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("FirstTimeInstall", "No");
                    editor.apply();
                    Intent intent = new Intent(SplashScreenActivity.this, one_time_activity.class);
                    startActivity(intent);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                }
            }, 3000);

        }
        else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreenActivity.this, Login.class);
                    startActivity(intent);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                }
            }, 3000);
        }
    }
}