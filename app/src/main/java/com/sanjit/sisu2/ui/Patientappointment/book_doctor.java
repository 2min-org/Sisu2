package com.sanjit.sisu2.ui.Patientappointment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sanjit.sisu2.R;
import com.sanjit.sisu2.ui.appointments.appointment_model;

import java.util.ArrayList;
import java.util.Map;


public class book_doctor extends Fragment {

    private RecyclerView recyclerView;
    ArrayList<book_doctor_model> book_arr = new ArrayList<>();
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    public ArrayList<String> booking_id = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_book_doctor, container, false);
        recyclerView = view.findViewById(R.id.doctor_recycler_view);

       db.collection("Doctors").get()
               .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                   @Override
                   public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                          for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Map<String, Object> data = documentSnapshot.getData();
                            String name = data.get("FullName").toString();
                            String spec = data.get("Specialization").toString();
                            String email = data.get("Email").toString();
                            Log.d("TAG", "onSuccess: "+name);
                            String uid = data.get("UId").toString();
                            book_arr.add(new book_doctor_model(name,spec,email,uid));
                            booking_id.add(documentSnapshot.getId());
                          }
                          recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                          recyclerView.setAdapter(new book_doctor_Adapter(getContext(),book_arr));

                   }
               })
               .addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                          Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                   }
               });



        return view;
    }
}