package com.sanjit.sisu2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sanjit.sisu2.R;
import com.sanjit.sisu2.models.HomeVerModel;

import java.util.List;

public class HomeVerAdapter extends RecyclerView.Adapter<HomeVerAdapter.ViewHolder> {

    Context context;
    List<HomeVerModel> homeVerModelList;

    public HomeVerAdapter(Context context, List<HomeVerModel> homeVerModelList) {
        this.context = context;
        this.homeVerModelList = homeVerModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_vertical_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageResource(homeVerModelList.get(position).getImage());
        holder.name.setText(homeVerModelList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return homeVerModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.hvc_image);
            name = itemView.findViewById(R.id.hvc_title);
        }

    }
}

