package com.sanjit.sisu2.ui.VaccineSchedule;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sanjit.sisu2.R;

import java.util.ArrayList;

public class upcoming_vaccine_adapter extends RecyclerView.Adapter<upcoming_vaccine_adapter.ViewHolder>{

    private final ArrayList<upcoming_vaccine_model> list_data;
    private final Context context;

    upcoming_vaccine_adapter(ArrayList<upcoming_vaccine_model> list_data, Context context) {
        this.list_data = list_data;
        this.context = context;
    }

    @NonNull
    @Override
    public upcoming_vaccine_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_vaccine_cardview, parent, false);
        ViewHolder holder = new ViewHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull upcoming_vaccine_adapter.ViewHolder holder, int position) {

        upcoming_vaccine_model listData = list_data.get(position);

        holder.date_day.setText(listData.getVaccine_day());
        holder.date_month_year.setText(listData.getVaccine_month_year());
        holder.vaccine_name.setText(listData.getVaccine_name());
        holder.vaccine_type.setText(listData.getVaccine_type());
        Log.d("TAG", "onBindViewHolder: " + listData.getVaccine_day());
        Log.d("TAG", "onBindViewHolder: " + listData.getVaccine_day());


    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView date_day, date_month_year, vaccine_name, vaccine_type;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date_day = itemView.findViewById(R.id.date_day);
            date_month_year = itemView.findViewById(R.id.date_month_year);
            vaccine_name = itemView.findViewById(R.id.vaccine_name);
            vaccine_type = itemView.findViewById(R.id.vaccine_type);
        }
    }
}
