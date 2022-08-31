package com.coetusstudio.hodanddeans.Activity.Administrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.coetusstudio.hodanddeans.databinding.ActivityCreateNewSubjectBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Create_New_Subject extends AppCompatActivity {

    ActivityCreateNewSubjectBinding binding;
    private DatabaseReference dbsubject;
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateNewSubjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbsubject= FirebaseDatabase.getInstance().getReference().child("Subject");

        binding.btnNewSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.createSubject.getEditText().getText().toString().isEmpty()) {
                    binding.createSubject.setError("Empty");
                    binding.createSubject.requestFocus();
                } else {
                    sendlink();
                }
            }
        });
    }
    private void sendlink() {

        String subject = binding.createSubject.getEditText().getText().toString().toUpperCase().trim();
        String subjectCode= binding.createSubjectCode.getEditText().getText().toString().toUpperCase().trim();
        String id = dbsubject.push().getKey();

        HashMap<String,String> hashMap=new HashMap<>();

        hashMap.put("subject",subject);
        hashMap.put("subjectCode",subjectCode);
        hashMap.put("id",id);


        dbsubject.child(id).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Create_New_Subject.this, "Added Successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Create_New_Subject.this, "Please, try again later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}