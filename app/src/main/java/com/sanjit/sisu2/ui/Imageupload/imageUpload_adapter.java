package com.sanjit.sisu2.ui.Imageupload;

import android.content.Context;
import android.util.Log;
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

public class imageUpload_adapter extends RecyclerView.Adapter<imageUpload_adapter.imageViewHolder>{

    private final Context context;
    private final ArrayList<basic_model> basic_models_arr;
    private final uploadimage_interface uploadimage_interface;

    imageUpload_adapter(Context context,
                        ArrayList<basic_model> basic_models_arr,
                        uploadimage_interface uploadimage_interface) {
        this.context = context;
        this.basic_models_arr = basic_models_arr;
        this.uploadimage_interface = uploadimage_interface;
    }

    @NonNull
    @Override
    public imageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflater = LayoutInflater.from(context).inflate(R.layout.uploded_image_card, parent, false);
        imageViewHolder holder = new imageViewHolder(inflater, uploadimage_interface);
        Log.d("TAG", "onCreateViewHolder: "+ holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull imageViewHolder holder, int position) {

        Log.d("TAG", "onBindViewHolder: "+basic_models_arr.get(position).getUrl());
        Log.d("TAG", "onBindViewHolder: "+basic_models_arr.get(position).getName());
        Log.d("TAG", "onBindViewHolder: "+basic_models_arr.get(position).getDescription());

        Log.d("TAG", "onBindViewHolder: "+holder);

        holder.name.setText(basic_models_arr.get(position).getName());
        holder.date.setText(basic_models_arr.get(position).getDescription());
        Picasso.get().load(basic_models_arr.get(position).getUrl())
                .resize(600,300)
                .centerInside()
                .config(android.graphics.Bitmap.Config.RGB_565)
                .into(holder.photo);

    }

    @Override
    public int getItemCount() {
        return basic_models_arr.size();
    }

    public static class imageViewHolder extends RecyclerView.ViewHolder {

        TextView name, description, date;
        ImageView photo;

        public imageViewHolder(View itemView, uploadimage_interface uploadimage_interface) {
            super(itemView);
            name = itemView.findViewById(R.id.uploadedImageName);
            date = itemView.findViewById(R.id.uploadedImageDate);
            photo = itemView.findViewById(R.id.uploadedImageView);

            itemView.setOnClickListener(v -> {
                if(uploadimage_interface != null){
                    int position = getAbsoluteAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        uploadimage_interface.onItemClick(position);
                    }
                }
            });


        }
    }
}
