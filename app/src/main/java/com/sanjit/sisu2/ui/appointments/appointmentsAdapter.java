package com.sanjit.sisu2.ui.appointments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    appointmentsAdapter(Context context,
                        ArrayList<appointment_model> appointment_arr){
        this.context = context;
        this.appointment_arr = appointment_arr;
    }

    @NonNull
    @Override
    public appointmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflater = LayoutInflater.from(context).inflate(R.layout.patient_card_view, parent, false);
        appointmentsViewHolder holder = new appointmentsViewHolder(inflater);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull appointmentsViewHolder holder, int position) {

        holder.name.setText(appointment_arr.get(position).getName());
        holder.phone.setText(appointment_arr.get(position).getPhone());
        Picasso.get().load(appointment_arr.get(position).getPhoto()).into(holder.photo);

    }

    @Override
    public int getItemCount() {
        return appointment_arr.size();
    }

    public class appointmentsViewHolder extends RecyclerView.ViewHolder {

        TextView name, phone;
        ImageView photo;

        public appointmentsViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.patient_name);
            phone = itemView.findViewById(R.id.patient_phone);
            photo = itemView.findViewById(R.id.patient_image);

        }
    }
}
