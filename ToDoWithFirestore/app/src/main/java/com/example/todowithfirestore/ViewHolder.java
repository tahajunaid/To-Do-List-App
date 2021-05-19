package com.example.todowithfirestore;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView mtask, mdetails;
    View mView;
    DisplayActivity mdisplayActivity;

    public ViewHolder(@NonNull View itemView, List<Model> modelList, DisplayActivity displayActivity) {
        super(itemView);
        mView = itemView;
        mdisplayActivity = displayActivity;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mdisplayActivity);
                String[] options = {"Update", "Delete"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            int pos = getAbsoluteAdapterPosition();
                            String id = modelList.get(pos).getId();
                            String task = modelList.get(pos).getTask();
                            String details = modelList.get(pos).getDetails();
                            Intent intent = new Intent(mdisplayActivity, MainActivity.class);
                            intent.putExtra("uId", id);
                            intent.putExtra("uTask", task);
                            intent.putExtra("uDetails", details);
                            mdisplayActivity.startActivity(intent);
                        } else {
                            mdisplayActivity.deleteData(getAbsoluteAdapterPosition());
                        }
                    }
                }).create().show();
                return true;
            }
        });

        mtask = itemView.findViewById(R.id.rtask);
        mdetails = itemView.findViewById(R.id.rdetails);

    }
}
