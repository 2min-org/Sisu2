package com.sanjit.sisu2.ui.VaccineTracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sanjit.sisu2.R;

import java.util.ArrayList;

public class cardview extends AppCompatActivity {

    ArrayList<Vaccine_cardview_model> vaccine_cardview_modelArrayList=new ArrayList<>();
    RecyclerView reacycleVaccine;
    FloatingActionButton fabcardview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardview);
        reacycleVaccine= findViewById(R.id.recyclerVaccine);//create object
        reacycleVaccine.setLayoutManager(new LinearLayoutManager(this));//set layout manager i.e. the layout of the recycler view
        //create array list
        vaccine_cardview_modelArrayList.add(new Vaccine_cardview_model(R.drawable.ic_vaccine_name,"Vaccine 1","Date 1",false));
        vaccine_cardview_modelArrayList.add(new Vaccine_cardview_model(R.drawable.ic_vaccine_name,"Vaccine 2","Date 2",false));
        vaccine_cardview_modelArrayList.add(new Vaccine_cardview_model(R.drawable.ic_vaccine_name,"Vaccine 3","Date 3",false));
        vaccine_cardview_modelArrayList.add(new Vaccine_cardview_model(R.drawable.ic_vaccine_name,"Vaccine 4","Date 4",false));
        vaccine_cardview_modelArrayList.add(new Vaccine_cardview_model(R.drawable.ic_vaccine_name,"Vaccine 5","Date 5",false));
        vaccine_cardview_modelArrayList.add(new Vaccine_cardview_model(R.drawable.ic_vaccine_name,"Vaccine 6","Date 6",false));
        vaccine_cardview_modelArrayList.add(new Vaccine_cardview_model(R.drawable.ic_vaccine_name,"Vaccine 7","Date 7",false));
        vaccine_cardview_modelArrayList.add(new Vaccine_cardview_model(R.drawable.ic_vaccine_name,"Vaccine 8","Date 8",false));
        vaccine_cardview_modelArrayList.add(new Vaccine_cardview_model(R.drawable.ic_vaccine_name,"Vaccine 9","Date 9",false));
        vaccine_cardview_modelArrayList.add(new Vaccine_cardview_model(R.drawable.ic_vaccine_name,"Vaccine 10","Date 10",false));
        vaccine_cardview_modelArrayList.add(new Vaccine_cardview_model(R.drawable.ic_vaccine_name,"Vaccine 11","Date 11",false));
        vaccine_cardview_modelArrayList.add(new Vaccine_cardview_model(R.drawable.ic_vaccine_name,"Vaccine 12","Date 12",false));

        RecyclerVaccineAdapter recyclerVaccineAdapter=new RecyclerVaccineAdapter(this,vaccine_cardview_modelArrayList);
        reacycleVaccine.setAdapter(recyclerVaccineAdapter);

        fabcardview=findViewById(R.id.fabcardview);
        fabcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}