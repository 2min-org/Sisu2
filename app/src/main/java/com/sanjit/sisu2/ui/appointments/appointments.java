package com.sanjit.sisu2.ui.appointments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sanjit.sisu2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.lang.String;
import java.util.Objects;


public class appointments extends Fragment {

    ArrayList<appointment_model> appointment_model_arr = new ArrayList<>();
    private DatabaseReference databaseReference;
    private final FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    public ArrayList<String> appointment_id = new ArrayList<>();
    private RecyclerView recyclerView;

    public appointments() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_appointments, container, false);
        TextView doctor_name = view.findViewById(R.id.doctor_name);
        TextView doctor_spec = view.findViewById(R.id.doctor_spec);
        TextView doctor_email = view.findViewById(R.id.doctor_email);
        TextView doctor_phone = view.findViewById(R.id.doctor_phone);
        ImageView doctor_photo = view.findViewById(R.id.doctor_image);

        //setting up values from shared preferences

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
        String Email = sharedPreferences.getString("Email", "Not Specified");
        String FullName = sharedPreferences.getString("FullName", "Not Specified");
        String User_id = sharedPreferences.getString("User_id", "Not Specified");
        String ProfilePic = sharedPreferences.getString("ProfilePic", "Not Specified");
        String User_mode = sharedPreferences.getString("User_mode", "Not Specified");
        String Specialization = sharedPreferences.getString("Specialization", "Not Specified");
        String Phone = sharedPreferences.getString("Phone", "Not Specified");

        //end of setting up values from shared preferences

        //here we are displaying the doctor details above the recycler view

        doctor_name.setText(FullName);
        doctor_spec.setText(Specialization);
        doctor_email.setText(Email);
        doctor_phone.setText(Phone);
        Picasso.get().load(ProfilePic).into(doctor_photo);

        //here we are displaying the doctor details above the recycler view

        //here we are fetching the appointments from the database and displaying them in the recycler view
        recyclerView = view.findViewById(R.id.appointments_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        db.collection("Doctors").document(mAuth.getCurrentUser().getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        //getting appointment id from firebase
                        Map<String, Object> data = documentSnapshot.getData();
                        ArrayList<String> appointment_id = (ArrayList<String>) data.get("appointment_id");
                        //getting appointment id from firebase
                        if(appointment_id != null)
                        {
                            for (String appointment : appointment_id) {
                                String[] arr = appointment.split(" , ");
                            }
                            int count = 0;
                            for (String appointment : appointment_id) {

                                Log.d("2appointments", "fetching ids: " + appointment);

                                int finalCount = count;
                                db.collection("Users").document(appointment).get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                Map<String, Object> data = documentSnapshot.getData();
                                                assert data != null;
                                                String name = (String) data.get("Fullname");
                                                String phone = (String) data.get("Telephone");
                                                String profile_pic = (String) data.get("ProfilePic");

                                                appointment_model_arr.add(new appointment_model(name, phone, profile_pic));
                                                Log.d("appointments", "onSuccess: " + name + " " + phone);

                                                //set adapter at the end of iteration

                                                if(finalCount == appointment_id.size()-1){
                                                    appointmentsAdapter adapter = new appointmentsAdapter(getContext(), appointment_model_arr);
                                                    recyclerView.setAdapter(adapter);
                                                    Log.d("adapter", "after success: " + appointment_model_arr);
                                                }

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                count++;
                            }



                            Log.d("appointment_id", "is this first? " + appointment_id);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });

        // Inflate the layout for this fragment
        return view;
    }
    }