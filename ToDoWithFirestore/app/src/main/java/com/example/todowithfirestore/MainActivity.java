package com.example.todowithfirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    TextInputLayout task, details;
    Button add, display;
    ProgressDialog pd;
    FirebaseFirestore db;
    String uId, uTask, uDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        task = findViewById(R.id.task);
        details = findViewById(R.id.details);
        add = findViewById(R.id.add);
        display = findViewById(R.id.display);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            actionBar.setTitle("Update");
            uId = bundle.getString("uId");
            uTask = bundle.getString("uTask");
            uDetails = bundle.getString("uDetails");

            task.getEditText().setText(uTask);
            details.getEditText().setText(uDetails);
            add.setText("Update");
        } else {
            actionBar.setTitle("Add Data");
            add.setText("Add");
        }

        pd = new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DisplayActivity.class));
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String tasktext = task.getEditText().getText().toString().trim();
                String dettext = details.getEditText().getText().toString().trim();

                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null) {
                    String id = uId;
                    updateData(id, tasktext, dettext);
                } else {
                    addData(tasktext, dettext);
                }

            }
        });
    }

    public void addData(String tasktext, String dettext) {

        pd.setTitle("Adding Data to Firestore");
        pd.show();
        String id = UUID.randomUUID().toString();
        Map<String, Object> obj = new HashMap<>();
        obj.put("id", id);
        obj.put("task", tasktext);
        obj.put("details", dettext);

// Add a new document with a generated ID
        db.collection("ToDo").document(id).set(obj)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(MainActivity.this, "Data uploaded successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, DisplayActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(MainActivity.this, "Data upload failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void updateData(String id, String task, String details) {
        pd.setTitle("Updating Data..");
        pd.show();
        db.collection("ToDo").document(id)
                .update("task", task, "details", details)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(MainActivity.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, DisplayActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(MainActivity.this, "Data updation failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}