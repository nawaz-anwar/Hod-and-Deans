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
import com.coetusstudio.hodanddeans.Adapter.Student.StudentAdapter;
import com.coetusstudio.hodanddeans.Models.Administrator.Branch;
import com.coetusstudio.hodanddeans.Models.Students.StudentDetails;
import com.coetusstudio.hodanddeans.R;
import com.coetusstudio.hodanddeans.databinding.ActivityCreateNewBranchBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Create_New_Branch extends AppCompatActivity {

    ActivityCreateNewBranchBinding binding;
    private DatabaseReference dbbatchname;
    ProgressDialog mDialog;
    RecyclerView recviewBranch;
    BranchAdapter branchAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateNewBranchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbbatchname= FirebaseDatabase.getInstance().getReference().child("Branch");

        binding.btnNewBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.createBranch.getEditText().getText().toString().isEmpty()) {
                    binding.createBranch.setError("Empty");
                    binding.createBranch.requestFocus();
                } else {
                    sendlink();
                }
            }
        });

        recviewBranch=(RecyclerView)findViewById(R.id.rcbranch);
        recviewBranch.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Branch> options =
                new FirebaseRecyclerOptions.Builder<Branch>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Branch"), Branch.class)
                        .build();

        branchAdapter=new BranchAdapter(options);
        recviewBranch.setAdapter(branchAdapter);
    }
    private void sendlink() {

        String lectureName = binding.createBranch.getEditText().getText().toString().toUpperCase().trim();
        String id = dbbatchname.push().getKey();

        HashMap<String,String> hashMap=new HashMap<>();

        hashMap.put("branch",lectureName);
        hashMap.put("id",id);


        dbbatchname.child(id).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Create_New_Branch.this, "Added Successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Create_New_Branch.this, "Please, try again later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        branchAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        branchAdapter.stopListening();
    }
}