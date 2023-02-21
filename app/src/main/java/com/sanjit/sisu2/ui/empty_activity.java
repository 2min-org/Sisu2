package com.sanjit.sisu2.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import com.sanjit.sisu2.R;
import com.sanjit.sisu2.models.Home_hor_model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class empty_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        List<Home_hor_model> home_horizontal_modelList = null;

        ImageView imageView = findViewById(R.id.disease_image);

        //get disease name from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("disease", MODE_PRIVATE);
        String disease = sharedPreferences.getString("disease", "No disease");

        //set disease name as title
        setTitle(disease);

        //create a map of disease name and image
        Map<String, Integer> diseaseImageMap = new HashMap<>();
        diseaseImageMap.put("Polio", R.drawable.ic_logo);
        diseaseImageMap.put("DPT", R.drawable.ic_logo);
        diseaseImageMap.put("Measles", R.drawable.ic_logo);
        diseaseImageMap.put("Hepatitis B", R.drawable.ic_logo);
        diseaseImageMap.put("Hepatitis A", R.drawable.ic_logo);
        diseaseImageMap.put("Hib", R.drawable.ic_logo);
        diseaseImageMap.put("Rotavirus", R.drawable.ic_logo);
        diseaseImageMap.put("Pneumococcal", R.drawable.ic_logo);
        diseaseImageMap.put("Influenza", R.drawable.ic_logo);
        diseaseImageMap.put("HPV", R.drawable.ic_logo);
        diseaseImageMap.put("Meningococcal", R.drawable.ic_logo);
        diseaseImageMap.put("Tetanus", R.drawable.ic_logo);
        diseaseImageMap.put("Typhoid", R.drawable.ic_logo);
        diseaseImageMap.put("Tdap", R.drawable.ic_logo);


        //search disease name in map and set the disease image
        for (Map.Entry<String, Integer> entry : diseaseImageMap.entrySet()) {
            if (entry.getKey().equals(disease)) {
                imageView.setImageResource(entry.getValue());
            }
        }

    }
}