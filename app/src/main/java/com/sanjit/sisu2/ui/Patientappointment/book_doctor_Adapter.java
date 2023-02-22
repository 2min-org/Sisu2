package com.sanjit.sisu2.ui.Patientappointment;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sanjit.sisu2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class book_doctor_Adapter extends RecyclerView.Adapter<book_doctor_Adapter.book_doctor_ViewHolder>{

    private final Context context;
    private final ArrayList<book_doctor_model> book_doctor_arr;
    private final book_doctor_recyclerViewInterface book_doctor_recyclerViewInterface;

    book_doctor_Adapter(Context context,
                        ArrayList<book_doctor_model> book_doctor_arr,
                        book_doctor_recyclerViewInterface book_doctor_recyclerViewInterface)
    {
        this.context = context;
        this.book_doctor_arr = book_doctor_arr;
        this.book_doctor_recyclerViewInterface = book_doctor_recyclerViewInterface;
    }

    @NonNull
    @Override
    public book_doctor_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflater = LayoutInflater.from(context).inflate(R.layout.doctor_card_view, parent, false);
        return new book_doctor_ViewHolder(inflater, book_doctor_recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull book_doctor_ViewHolder holder, int position) {

        holder.name.setText(book_doctor_arr.get(position).getName());
        holder.phone.setText(book_doctor_arr.get(position).getPhone());
        holder.photo.setImageResource(R.drawable.facebook);

        if(book_doctor_arr.get(position).getBooked())
        {
            holder.book.setTextColor(Color.parseColor("#6fc276"));
            holder.book.setText("Booked");
            holder.book.setEnabled(false);
        }
        else
        {
            holder.book.setText("Book");
            holder.book.setEnabled(true);
        }
        //map appointment from firebase user collection
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        //get User document from firebase
        db.collection("Users").document(auth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                //get the user document
                Map<String, Object> user = task.getResult().getData();
                Log.d("user", "onComplete: " + user);
                //get the user appointments
                ArrayList<String> appointments = (ArrayList<String>) user.get("appointments");
                Log.d("appointments", "onComplete: " + appointments);
                if(appointments != null) {
                    for (String appointment : appointments) {
                        if (appointment.equals(book_doctor_arr.get(position).getU_id())) {
                            holder.book.setTextColor(Color.parseColor("#6fc276"));
                            holder.book.setText("Booked");
                            holder.book.setEnabled(false);
                        }
                    }
                }
            }
        });


        Picasso.get().load(book_doctor_arr.get(position).getPhoto()).into(holder.photo);

    }

    @Override
    public int getItemCount() {
        return book_doctor_arr.size();
    }

    public static class book_doctor_ViewHolder extends RecyclerView.ViewHolder {

        TextView name, phone;
        ImageView photo;
        Button book;

            public book_doctor_ViewHolder(View itemView, book_doctor_recyclerViewInterface book_doctor_recyclerViewInterface) {

                super(itemView);
                name = itemView.findViewById(R.id.patient_name);
                phone = itemView.findViewById(R.id.patient_phone);
                photo = itemView.findViewById(R.id.patient_image);
                book = itemView.findViewById(R.id.book_button);

                    itemView.setOnClickListener(v -> {
                        //make sure that the interface is not null
                        if (book_doctor_recyclerViewInterface != null) {
                            int position = getAbsoluteAdapterPosition();
                            //check if the position is valid
                            if (position != RecyclerView.NO_POSITION) {
                                book_doctor_recyclerViewInterface.onDoctorClick(position);
                            }
                        }
                    });

                    book.setOnClickListener(v -> {
                        //make sure that the interface is not null
                        if (book_doctor_recyclerViewInterface != null) {
                            int position = getAbsoluteAdapterPosition();
                            //check if the position is valid
                            if (position != RecyclerView.NO_POSITION) {
                                book_doctor_recyclerViewInterface.onBookClick(position, book);
                            }
                        }
                    });

            }
    }
}
