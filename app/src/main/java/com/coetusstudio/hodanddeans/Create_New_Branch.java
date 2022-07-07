package com.coetusstudio.hodanddeans;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.coetusstudio.hodanddeans.Models.Lecture;
import com.coetusstudio.hodanddeans.databinding.ActivityCreateNewBranchBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Create_New_Branch extends AppCompatActivity {

    ActivityCreateNewBranchBinding binding;
    private DatabaseReference dbbatchname;
    ProgressDialog mDialog;


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
}