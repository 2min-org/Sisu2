package com.sanjit.sisu2.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toolbar;

import com.sanjit.sisu2.R;

import java.util.Objects;

import io.grpc.Context;

public class Setting extends AppCompatActivity {

    Switch switcher;
    boolean nightMode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        toolbar=(Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(findViewById(R.id.toolbar));

        getSupportActionBar().hide();
        switcher= findViewById(R.id.switcher);

        // We used shared preference to save mode if exit the app and go back again

        sharedPreferences = getSharedPreferences("MODE", MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("night", false); // Light mode is the default mode

        if(nightMode){
            switcher.setChecked(true);
        }
        switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nightMode){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor= sharedPreferences.edit();
                    editor.putBoolean("night", false);
            }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor= sharedPreferences.edit();
                    editor.putBoolean("night", true);
                }
                editor.apply();
            }
        });

    }
}