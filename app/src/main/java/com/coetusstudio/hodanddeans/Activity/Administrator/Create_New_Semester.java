package com.coetusstudio.hodanddeans.Activity.Administrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.coetusstudio.hodanddeans.Adapter.Adminstrator.BranchAdapter;
import com.coetusstudio.hodanddeans.Adapter.Adminstrator.SemesterAdapter;
import com.coetusstudio.hodanddeans.Models.Administrator.Branch;
import com.coetusstudio.hodanddeans.Models.Administrator.Semester;
import com.coetusstudio.hodanddeans.R;
import com.coetusstudio.hodanddeans.databinding.ActivityCreateNewSemesterBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Create_New_Semester extends AppCompatActivity {

    ActivityCreateNewSemesterBinding binding;
    private DatabaseReference dbbatchname;
    ProgressDialog mDialog;
    RecyclerView recviewSemester;
    SemesterAdapter semesterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateNewSemesterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbbatchname= FirebaseDatabase.getInstance().getReference().child("Semester");

        binding.btnNewSemester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.createSemester.getEditText().getText().toString().isEmpty()) {
                    binding.createSemester.setError("Empty");
                    binding.createSemester.requestFocus();
                } else {
                    sendlink();
                }
            }
        });

        recviewSemester=(RecyclerView)findViewById(R.id.rcsemester);
        recviewSemester.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Semester> options =
                new FirebaseRecyclerOptions.Builder<Semester>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Semester"), Semester.class)
                        .build();

        semesterAdapter=new SemesterAdapter(options);
        recviewSemester.setAdapter(semesterAdapter);
    }
    private void sendlink() {

        String lectureName = binding.createSemester.getEditText().getText().toString().toUpperCase().trim();
        String id = dbbatchname.push().getKey();

        HashMap<String,String> hashMap=new HashMap<>();

        hashMap.put("semester",lectureName);
        hashMap.put("id",id);


        dbbatchname.child(id).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Create_New_Semester.this, "Added Successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Create_New_Semester.this, "Please, try again later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        semesterAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        semesterAdapter.stopListening();
    }
}