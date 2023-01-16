package com.sanjit.sisu2.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.sanjit.sisu2.R;
import com.sanjit.sisu2.models.Home_hor_model;
import com.sanjit.sisu2.ui.Polio;

import java.util.List;

public class home_horizontal_adapter extends RecyclerView.Adapter<home_horizontal_adapter.ViewHolder> {

    Context context;
    List<Home_hor_model> home_horizontal_modelList;

    public home_horizontal_adapter(FragmentActivity activity, List<Home_hor_model> home_horizontal_modelList) {
        this.context = activity;
        this.home_horizontal_modelList = home_horizontal_modelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_horizontal_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageResource(home_horizontal_modelList.get(position).getImage());
        holder.name.setText(home_horizontal_modelList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return home_horizontal_modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.hor_img);
            name = itemView.findViewById(R.id.hor_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Intent intent = new Intent(context, Polio.class);
            Toast.makeText(context, "Disease" + position, Toast.LENGTH_SHORT).show();
            context.startActivity(intent);
        }
    }
}