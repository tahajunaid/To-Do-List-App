package com.example.todowithfirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {
    RecyclerView mRV;
    List<Model> modelList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore db;
    CustomAdapter adapter;
    ProgressDialog pd;

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.layout.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(DisplayActivity.this, MainActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("To-Do List");
        db = FirebaseFirestore.getInstance();
        mRV = findViewById(R.id.recyclerview);
        mRV.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRV.setLayoutManager(layoutManager);
        pd = new ProgressDialog(this);
        showData();


    }

    private void showData() {
        pd.setTitle("Loading data..");
        pd.show();
        db.collection("ToDo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        modelList.clear();
                        pd.dismiss();
                        for (DocumentSnapshot doc : task.getResult()) {
                            Model model = new Model(doc.getString("id"), doc.getString("task"), doc.getString("details"));
                            modelList.add(model);

                        }
                        adapter = new CustomAdapter(DisplayActivity.this, modelList);
                        mRV.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(DisplayActivity.this, e.getMessage(), Toast.LENGTH_SHORT);
                    }
                });
    }

    public void deleteData(int index) {
        pd.setTitle("Deleting Data...");
        pd.show();
        db.collection("ToDo").document(modelList.get(index).getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(DisplayActivity.this, "Deleted Successfully", Toast.LENGTH_LONG);
                        showData();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(DisplayActivity.this, "Deletion Failed", Toast.LENGTH_LONG);

                    }
                });
    }

}