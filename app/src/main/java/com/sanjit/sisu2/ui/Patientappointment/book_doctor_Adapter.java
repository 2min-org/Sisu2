package com.sanjit.sisu2.ui.Patientappointment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sanjit.sisu2.R;
import com.sanjit.sisu2.ui.appointments.appointment_model;

import java.util.ArrayList;
import java.util.Map;

public class book_doctor_Adapter extends RecyclerView.Adapter<book_doctor_Adapter.book_doctor_ViewHolder>{

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final Context context;
    private final ArrayList<book_doctor_model> book_doctor_arr;

    book_doctor_Adapter(Context context, ArrayList<book_doctor_model> book_doctor_arr) {
        this.context = context;
        this.book_doctor_arr = book_doctor_arr;
    }

    @NonNull
    @Override
    public book_doctor_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflater = LayoutInflater.from(context).inflate(R.layout.doctor_card_view, parent, false);
        book_doctor_ViewHolder holder = new book_doctor_ViewHolder(inflater);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull book_doctor_ViewHolder holder, int position) {

        holder.name.setText(book_doctor_arr.get(position).getName());
        holder.phone.setText(book_doctor_arr.get(position).getPhone());
        holder.photo.setImageResource(R.drawable.facebook);

        Log.d("TAG", "Is button being initialized?: "+holder.book);

        holder.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Doctors").document((book_doctor_arr.get(holder.getAbsoluteAdapterPosition()).getU_id())).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                Map<String, Object> data = documentSnapshot.getData();
                                ArrayList<String> appointment_id = (ArrayList<String>) data.get("appointment_id");

                                Log.d("TAG", "Available appointments: "+appointment_id);

                                //if the array doesn't contain the appointment id then add it
                                //else show toast that appointment is already booked

                                if(appointment_id == null) {
                                    db.collection("Doctors")
                                            .document((book_doctor_arr.get(holder.getAbsoluteAdapterPosition()).getU_id()))
                                            .update("appointment_id", mAuth.getCurrentUser().getUid())
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                }
                                            });
                                }
                                else{
                                        if(appointment_id.contains(mAuth.getCurrentUser().getUid()))
                                        {
                                            Toast.makeText(context, "Appointment already booked", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            db.collection("Doctors")
                                                    .document((book_doctor_arr.get(holder.getAbsoluteAdapterPosition()).getU_id()))
                                                    .update("appointment_id", FieldValue.arrayUnion(mAuth.getCurrentUser().getUid()))
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                         Log.d("TAG", "Appointment booked");
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {

                                                        }
                                                    });
                                        }
                                    }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
    }

    @Override
    public int getItemCount() {
        return book_doctor_arr.size();
    }

    public class book_doctor_ViewHolder extends RecyclerView.ViewHolder {

        TextView name, phone;
        ImageView photo;
        Button book;

        public book_doctor_ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.patient_name);
            phone = itemView.findViewById(R.id.patient_phone);
            photo = itemView.findViewById(R.id.patient_image);
            book = itemView.findViewById(R.id.book_button);

        }
    }
}
