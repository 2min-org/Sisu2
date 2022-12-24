package com.sanjit.sisu2.ui.VaccineTracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sanjit.sisu2.R;

import java.util.ArrayList;

public class RecyclerVaccineAdapter extends RecyclerView.Adapter<RecyclerVaccineAdapter.ViewHolder> {
    Context context;
    ArrayList<Vaccine_cardview_model> vaccine_cardview_modelArrayList;
    int lastPosition = -1;
    public RecyclerVaccineAdapter(Context context, ArrayList<Vaccine_cardview_model> vaccine_cardview_modelArrayList) {
        this.context = context;
        this.vaccine_cardview_modelArrayList = vaccine_cardview_modelArrayList;
    }
    @androidx.annotation.NonNull
    @Override
    public ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(context).inflate(R.layout.recycle_vaccine_card,parent,false);
       ViewHolder viewHolder = new ViewHolder(view);
       return viewHolder;
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull ViewHolder holder, int position) {

        holder.tvaccineImage.setImageResource(vaccine_cardview_modelArrayList.get(position).image);
        holder.tvaccineName.setText(vaccine_cardview_modelArrayList.get(position).vaccineName);
        holder.tvaccineDate.setText(vaccine_cardview_modelArrayList.get(position).vaccineDate);
        holder.aSwitch.setChecked(vaccine_cardview_modelArrayList.get(position).isSwitchOn);
        holder.aSwitch.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               boolean isSwitchOn;
               if(holder.aSwitch.isChecked()){
                   holder.aSwitch.setBackgroundColor(context.getResources().getColor(android.R.color.background_light));
                   holder.aSwitch.setText("On");
                     isSwitchOn = true;

               }else{
                   holder.aSwitch.setText("Off");
                     holder.aSwitch.setBackgroundColor(context.getResources().getColor(android.R.color.black));
                     holder.aSwitch.setChecked(false);
                        isSwitchOn = false;
               }
               vaccine_cardview_modelArrayList.set(holder.getAbsoluteAdapterPosition(), new Vaccine_cardview_model(isSwitchOn));
           }


       });
        setAnimation(holder.itemView,position);

    }

    @Override
    public int getItemCount() {
        return vaccine_cardview_modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvaccineName,tvaccineDate,tvaccineData;
        ImageView tvaccineImage;
        Switch aSwitch;
        //this class will pass the view to the adapter i.e to the view holder of adapter
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvaccineName = itemView.findViewById(R.id.vaccineNames);
            tvaccineDate = itemView.findViewById(R.id.vaccineDate);
            tvaccineImage = itemView.findViewById(R.id.vaccineImage);
            aSwitch=itemView.findViewById(R.id.switch1);

        }
    }
    private void setAnimation(View viewToAnimate, int position)
    {

        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > -1)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
