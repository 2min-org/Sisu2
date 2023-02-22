package com.sanjit.sisu2.ui.appointments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sanjit.sisu2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class appointmentsAdapter extends RecyclerView.Adapter<appointmentsAdapter.appointmentsViewHolder>{

    private final Context context;
    private final ArrayList<appointment_model> appointment_arr;
    private final doctor_appointment_interface doctor_appointment_interface;

    appointmentsAdapter(Context context,
                        ArrayList<appointment_model> appointment_arr,doctor_appointment_interface doctor_appointment_interface)
    {
        this.context = context;
        this.appointment_arr = appointment_arr;
        this.doctor_appointment_interface = doctor_appointment_interface;
    }

    @NonNull
    @Override
    public appointmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflater = LayoutInflater.from(context).inflate(R.layout.patient_card_view, parent, false);
        appointmentsViewHolder holder = new appointmentsViewHolder(inflater,doctor_appointment_interface);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull appointmentsViewHolder holder, int position ) {

        holder.name.setText(appointment_arr.get(position).getName());
        holder.phone.setText(appointment_arr.get(position).getPhone());
        Picasso.get().load(appointment_arr.get(position).getPhoto()).into(holder.photo);

    }

    @Override
    public int getItemCount() {
        return appointment_arr.size();
    }

    public static class appointmentsViewHolder extends RecyclerView.ViewHolder {

        TextView name, phone;
        ImageView photo;
        Button complete,cancel;

        public appointmentsViewHolder(View itemView,doctor_appointment_interface doctor_appointment_interface) {
            super(itemView);
            name = itemView.findViewById(R.id.patient_name);
            phone = itemView.findViewById(R.id.patient_phone);
            photo = itemView.findViewById(R.id.patient_image);
            complete = itemView.findViewById(R.id.complete_button);
            cancel = itemView.findViewById(R.id.cancel_button);

            complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(doctor_appointment_interface!=null){
                        if (getAbsoluteAdapterPosition() != RecyclerView.NO_POSITION) {
                            doctor_appointment_interface.onCompletedClick(getAbsoluteAdapterPosition());
                        }
                     }
                    }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (doctor_appointment_interface != null && getAbsoluteAdapterPosition() != RecyclerView.NO_POSITION)
                        doctor_appointment_interface.onCancelClick(getAbsoluteAdapterPosition());
                }
            });
        }
    }
}
