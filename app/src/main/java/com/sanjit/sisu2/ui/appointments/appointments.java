package com.sanjit.sisu2.ui.appointments;

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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sanjit.sisu2.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;


public class appointments extends Fragment {

    ArrayList<appointment_model> appointment_arr = new ArrayList<>();
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    public ArrayList<String> appointment_id = new ArrayList<>();

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

        db.collection("Users").document(mAuth.getCurrentUser().getUid()).get()
            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    Map<String,Object> data = documentSnapshot.getData();
                    doctor_name.setText(data.get("Fullname").toString());
                    doctor_spec.setText(data.get("Specialization").toString());
                    doctor_email.setText(data.get("Email").toString());
                    doctor_phone.setText(data.get("Telephone").toString());
                    doctor_photo.setImageResource(R.drawable.facebook);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.appointments_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //taking array data from firebase and inserting them in array list of appointment model

        db.collection("Doctors").document(mAuth.getCurrentUser().getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        //getting appointment id from firebase
                        Map<String, Object> data = documentSnapshot.getData();
                        appointment_id = (ArrayList<String>) data.get("appointment_id");

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
                                                    String name = (String) data.get("Fullname");
                                                    String phone = (String) data.get("Telephone");

                                                    appointment_arr.add(new appointment_model(name, phone, null));
                                                    Log.d("appointments", "onSuccess: " + name + " " + phone);

                                                    //set adapter at the end of iteration
                                                    if(finalCount == appointment_id.size()-1){
                                                        appointmentsAdapter adapter = new appointmentsAdapter(getContext(), appointment_arr);
                                                        recyclerView.setAdapter(adapter);
                                                        Log.d("adapter", "after success: " + appointment_arr);
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