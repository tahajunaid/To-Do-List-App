package com.example.todowithfirestore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
    DisplayActivity displayActivity;
    List<Model> modelList;


    public CustomAdapter(DisplayActivity displayActivity, List<Model> modelList) {
        this.displayActivity = displayActivity;
        this.modelList = modelList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView, modelList, displayActivity);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mtask.setText(modelList.get(position).getTask());
        holder.mdetails.setText(modelList.get(position).getDetails());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
