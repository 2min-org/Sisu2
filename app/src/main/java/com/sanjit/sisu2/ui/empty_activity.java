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
        diseaseImageMap.put("Polio", R.drawable.polio);
        diseaseImageMap.put("Diphtheria", R.drawable.dipthdis);
        diseaseImageMap.put("Measles", R.drawable.measlesdis);
        diseaseImageMap.put("Hepatitis B", R.drawable.hepatitisb);
        diseaseImageMap.put("Hepatitis A", R.drawable.hepa);
        diseaseImageMap.put("Hib", R.drawable.influenza);
        diseaseImageMap.put("Rotavirus", R.drawable.rotavirus);
        diseaseImageMap.put("Pneumococcal", R.drawable.pnemo);
        diseaseImageMap.put("Influenza", R.drawable.influenza);
        diseaseImageMap.put("Meningococcal", R.drawable.meningdis);
        diseaseImageMap.put("Tetanus", R.drawable.tetanusdis);
        diseaseImageMap.put("Typhoid", R.drawable.ic_logo);
        diseaseImageMap.put("Pertussis", R.drawable.pertusisdis);

        diseaseImageMap.put("Polio Vaccine", R.drawable.pv);
        diseaseImageMap.put("Diphtheria Vaccine", R.drawable.diptheriavacc);
        diseaseImageMap.put("Measles Vaccine", R.drawable.mmrvacc);
        diseaseImageMap.put("Hepatitis B Vaccine", R.drawable.hepatitisbvacc);
        diseaseImageMap.put("Hepatitis A Vaccine", R.drawable.hepavacc);
        diseaseImageMap.put("Hib Vaccine", R.drawable.iv);
        diseaseImageMap.put("Rotavirus Vaccine", R.drawable.rotavirusvacc);
        diseaseImageMap.put("Pneumococcal Vaccine", R.drawable.pnemovacc);
        diseaseImageMap.put("Influenza Vaccine", R.drawable.iv);
        diseaseImageMap.put("Meningococcal Vaccine", R.drawable.meningdis);
        diseaseImageMap.put("Tetanus Vaccine", R.drawable.tetanusvacc);
        diseaseImageMap.put("Typhoid Vaccine", R.drawable.ic_logo);
        diseaseImageMap.put("Pertussis Vaccine", R.drawable.pertusisvacc);


        //search disease name in map and set the disease image
        for (Map.Entry<String, Integer> entry : diseaseImageMap.entrySet()) {
            if (entry.getKey().equals(disease)) {
                imageView.setImageResource(entry.getValue());
            }
        }

    }
}