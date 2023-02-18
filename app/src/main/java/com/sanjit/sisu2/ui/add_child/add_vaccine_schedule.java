package com.sanjit.sisu2.ui.add_child;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.sanjit.sisu2.models.custom_date;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class add_vaccine_schedule extends AsyncTask<Void , Integer, String> {
    private final String name;
    DocumentReference dr;

    public add_vaccine_schedule(String name) {
        this.name = name;
    }

    @Override
    protected String doInBackground(Void... voids) {


        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

         dr = db.collection("Users").document(uid)
                .collection("Child").document(name);

         //get the date from the database
            dr.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Map<String , Object> map = task.getResult().getData();
                    custom_date custom_date;
                    //within the data in dob field there is a map of custom_date
                    //so we need to get that map and convert it to custom_date object
                    if (map != null) {
                        map = (Map<String, Object>) map.get("dob");
                        custom_date = new custom_date((int)((long) map.get("day")), (int)(long) map.get("month"),(int) (long) map.get("year"));
                        Map<String, custom_date> update = mapper(custom_date);

                        dr.update("vaccine_schedule", update)
                                .onSuccessTask(task1 -> {
                                    Log.d("update", "success");

                                    return null;
                                })
                                .addOnFailureListener(e -> Log.d("update", "failure"));

                        //add the child to the list of children
                                db.collection("Users").document(uid)
                                        .update("Children_name", FieldValue.arrayUnion(name))
                                .addOnSuccessListener(unused -> Log.d("update", "success")).addOnFailureListener(e -> Log.d("update", "failure"));
                    }

                }
            });


        return null;
    }

    private Map<String , custom_date> mapper (custom_date custom_date){
        Map<String , custom_date> map = new HashMap<>();
        //this is the map of vaccine name and date to be inoculated on basis of dob in custom_date
        Calendar calendar = Calendar.getInstance();


        //BCG
        calendar.set(custom_date.getYear(),custom_date.getMonth(),custom_date.getDay());
        calendar.add(Calendar.WEEK_OF_YEAR, 4);

        map.put("BCG", new custom_date(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)));

        //Hepatitis B
        calendar.set(custom_date.getYear(),custom_date.getMonth(),custom_date.getDay());
        //1stdose
        calendar.add(Calendar.WEEK_OF_YEAR, 0);
        //2nd dose
        calendar.add(Calendar.WEEK_OF_YEAR, 4);
        map.put("HepB", new custom_date(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)));

        //Rotavirus
        calendar.set(custom_date.getYear(),custom_date.getMonth(),custom_date.getDay());
        //1stdose
        calendar.add(Calendar.WEEK_OF_YEAR, 9);
        //2nd dose
        calendar.add(Calendar.WEEK_OF_YEAR, 16);
        //3rd dose
        calendar.add(Calendar.WEEK_OF_YEAR, 24);
        map.put("RV", new custom_date(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)));

        //Tetanus
        calendar.set(custom_date.getYear(),custom_date.getMonth(),custom_date.getDay());
        //1st dose
        calendar.add(Calendar.WEEK_OF_YEAR, 8);
        //2nd dose
        calendar.add(Calendar.WEEK_OF_YEAR, 16);
        //3rd dose
        calendar.add(Calendar.WEEK_OF_YEAR, 24);
        //4th dose
        calendar.add(Calendar.WEEK_OF_YEAR, 216);
        map.put("Tetanus", new custom_date(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)));

        //PCV
        calendar.set(custom_date.getYear(),custom_date.getMonth(),custom_date.getDay());
        //1st
        calendar.add(Calendar.WEEK_OF_YEAR, 8);
        //2nd
        calendar.add(Calendar.WEEK_OF_YEAR, 16);
        //3rd
        calendar.add(Calendar.WEEK_OF_YEAR, 24);
        //4th
        calendar.add(Calendar.WEEK_OF_YEAR, 48);
        map.put("PCV-Pneumococcal Disease", new custom_date(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)));

        //Polio
        calendar.set(custom_date.getYear(),custom_date.getMonth(),custom_date.getDay());
        //1st
        calendar.add(Calendar.WEEK_OF_YEAR, 8);
        //2nd
        calendar.add(Calendar.WEEK_OF_YEAR, 16);
        //3rd
        calendar.add(Calendar.WEEK_OF_YEAR, 24);
        //4th
        calendar.add(Calendar.WEEK_OF_YEAR, 216);
        map.put("IPV-Polio", new custom_date(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)));

        //Covid
        calendar.set(custom_date.getYear(),custom_date.getMonth(),custom_date.getDay());
        calendar.add(Calendar.WEEK_OF_YEAR, 28);
        map.put("COVID-19", new custom_date(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)));

        //Flu
        calendar.set(custom_date.getYear(),custom_date.getMonth(),custom_date.getDay());
        calendar.add(Calendar.WEEK_OF_YEAR, 28);
        map.put("Influenza", new custom_date(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)));

        //MMR
        calendar.set(custom_date.getYear(),custom_date.getMonth(),custom_date.getDay());
        //1st
        calendar.add(Calendar.WEEK_OF_YEAR, 48);
        //2nd
        calendar.add(Calendar.WEEK_OF_YEAR, 217);
        map.put("MMR-Mumps,Measles & Rubella", new custom_date(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)));

        //Chickenpox
        calendar.set(custom_date.getYear(),custom_date.getMonth(),custom_date.getDay());
        //1st
        calendar.add(Calendar.WEEK_OF_YEAR, 48);
        //2nd
        calendar.add(Calendar.WEEK_OF_YEAR, 217);
        map.put("Chickenpox", new custom_date(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)));

        //Hepaptitis A
        calendar.set(custom_date.getYear(),custom_date.getMonth(),custom_date.getDay());
        //1st
        calendar.add(Calendar.WEEK_OF_YEAR, 48);
        //2nd
        calendar.add(Calendar.WEEK_OF_YEAR, 72);
        map.put("HepA", new custom_date(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)));
























































        return map;
    }
}
