package com.sanjit.sisu2.ui.Patientappointment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.sanjit.sisu2.R;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;


public class book_doctor extends Fragment implements book_doctor_recyclerViewInterface{

    private RecyclerView recyclerView;
    ArrayList<book_doctor_model> book_model_arr = new ArrayList<>();
    private final FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    public ArrayList<String> booking_id = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_book_doctor, container, false);
        recyclerView = view.findViewById(R.id.doctor_recycler_view);

        setDataModel();

        return view;
    }

    private void setDataModel(){

        db.collection("Doctors").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    //do this only if the data is not null
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                            Map<String, Object> data = documentSnapshot.getData();
                            String name = Objects.requireNonNull(data.get("FullName")).toString();
                            String spec = Objects.requireNonNull(data.get("Specialization")).toString();
                            String email = Objects.requireNonNull(data.get("Email")).toString();
                            String photo;
                            try{
                                photo = data.get("ProfilePic").toString();
                            }catch (Exception e){
                                photo = "https://firebasestorage.googleapis.com/v0/b/sisu-2.appspot.com/o/Profile%20Pictures%2Fdefault.png?alt=media&token=8b8b8b8b-8b8b-8b8b-8b8b-8b8b8b8b8b8b";
                            }
                            Log.d("TAG", "onSuccess: " + name);
                            String uid = Objects.requireNonNull(data.get("UId")).toString();
                            boolean isBooked = false;

                            ArrayList<String> appointment_id = (ArrayList<String>) data.get("appointment_id");
                            if(appointment_id != null){
                                if(appointment_id.contains(mAuth.getUid())){
                                    isBooked = true;
                                }
                            }

                            book_model_arr.add(new book_doctor_model(name, spec, email, uid, isBooked, photo));
                            booking_id.add(documentSnapshot.getId());
                        }
                    }


                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    book_doctor_recyclerViewInterface book_doctor_recyclerViewInterface = book_doctor.this;
                    recyclerView.setAdapter(new book_doctor_Adapter(getContext(), book_model_arr, book_doctor_recyclerViewInterface));

                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show());

    }

    @Override
    public void onDoctorClick(int position) {
        Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBookClick(int position, Button book) {
        Toast.makeText(getContext(), "Booked", Toast.LENGTH_SHORT).show();
        db.collection("Doctors").document((book_model_arr.get(position).getU_id())).get()
                .addOnSuccessListener(documentSnapshot -> {

                    Map<String, Object> data = documentSnapshot.getData();
                    assert data != null;
                    ArrayList<String> appointment_id = (ArrayList<String>) data.get("appointment_id");

                    Log.d("TAG", "Available appointments: "+appointment_id);

                    //if the array doesn't contain the appointment id then add it
                    //else show toast that appointment is already booked

                    if(appointment_id == null) {
                        db.collection("Doctors")
                                .document((book_model_arr.get(position).getU_id()))
                                .update("appointment_id", FieldValue.arrayUnion(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()))
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(getContext(), "Appointment Booked", Toast.LENGTH_SHORT).show();
                                    book.setText("Booked");
                                    book.setEnabled(false);
                                })
                                .addOnFailureListener(e -> {

                                });
                    }
                    else{
                        if(appointment_id.contains(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()))
                        {
                            Toast.makeText(getContext(), "Appointment already booked", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            db.collection("Doctors")
                                    .document((book_model_arr.get(position).getU_id()))
                                    .update("appointment_id", FieldValue.arrayUnion(mAuth.getCurrentUser().getUid()))
                                    .addOnSuccessListener(aVoid -> {
                                        Log.d("TAG", "Appointment booked");
                                        book.setTextColor(Color.parseColor("#FFFFFF"));
                                        book.setText("Booked");
                                        book.setEnabled(false);

                                    })
                                    .addOnFailureListener(e -> {

                                    });
                        }
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show());

        }
}