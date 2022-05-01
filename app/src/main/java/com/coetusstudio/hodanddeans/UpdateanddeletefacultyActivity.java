package com.coetusstudio.hodanddeans;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.coetusstudio.hodanddeans.Adapter.FacultyAdapter;
import com.coetusstudio.hodanddeans.Models.AddFaculty;
import com.coetusstudio.hodanddeans.databinding.ActivityUpdateanddeletefacultyBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpdateanddeletefacultyActivity extends AppCompatActivity {


    ActivityUpdateanddeletefacultyBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    RecyclerView recview;
    ArrayList<AddFaculty> addFaculties;
    FacultyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateanddeletefacultyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Search here..");

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        addFaculties = new ArrayList<>();

        adapter = new FacultyAdapter(this, addFaculties);
        binding.recview.setAdapter(adapter);

        database.getReference().child("Faculty").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                addFaculties.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    AddFaculty addFaculty = snapshot.getValue(AddFaculty.class);
                    addFaculties.add(addFaculty);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}