package com.coetusstudio.hodanddeans;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.coetusstudio.hodanddeans.Adapter.FacultyAdapter;
import com.coetusstudio.hodanddeans.Models.AddFaculty;
import com.coetusstudio.hodanddeans.databinding.ActivityUpdateanddeletefacultyBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpdateanddeletefacultyActivity extends AppCompatActivity {


    ActivityUpdateanddeletefacultyBinding binding;
    FirebaseAuth auth;
    DatabaseReference database;
    ArrayList<AddFaculty> addFaculties;
    FacultyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateanddeletefacultyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Search here..");

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference().child("IIMTU").child("Faculty");

        addFaculties = new ArrayList<>();

        adapter = new FacultyAdapter(this, addFaculties);
        binding.rcFactulty.setAdapter(adapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        AddFaculty data = snapshot1.getValue(AddFaculty.class);
                        addFaculties.add(data);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("UpdateActivity", error.toString());
            }
        });

    }

//    private void adapterSetup(ArrayList<AddFaculty> addFaculties){
//
//        Log.d("list", String.valueOf(addFaculties.size()));
//
//        rcView = binding.rcFactulty;
//        rcView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new FacultyAdapter(this, addFaculties);
//        rcView.setAdapter(adapter);
//    }

    
}