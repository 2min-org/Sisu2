package com.sanjit.sisu2.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.sanjit.sisu2.ui.empty_activity;

import java.util.List;

public class home_horizontal_adapter extends RecyclerView.Adapter<home_horizontal_adapter.ViewHolder> {

    Context context;
    List<Home_hor_model> home_horizontal_modelList;

    public home_horizontal_adapter(FragmentActivity activity, List<Home_hor_model> home_horizontal_modelList) {
        this.context = activity;
        this.home_horizontal_modelList = home_horizontal_modelList;
    }

    @NonNull
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
            int position = getAbsoluteAdapterPosition();
            Intent intent = new Intent(context, empty_activity.class);
            Toast.makeText(context, "Disease" + position, Toast.LENGTH_SHORT).show();

            SharedPreferences sharedPreferences = context.getSharedPreferences("disease", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("disease", home_horizontal_modelList.get(position).getName());
            editor.apply();

            context.startActivity(intent);
        }
    }
}