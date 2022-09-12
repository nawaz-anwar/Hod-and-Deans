package com.coetusstudio.hodanddeans.Activity.Administrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.coetusstudio.hodanddeans.Adapter.Adminstrator.SectionAdapter;
import com.coetusstudio.hodanddeans.Adapter.Adminstrator.SubjectAdapter;
import com.coetusstudio.hodanddeans.Models.Administrator.Section;
import com.coetusstudio.hodanddeans.Models.Administrator.Subject;
import com.coetusstudio.hodanddeans.R;
import com.coetusstudio.hodanddeans.databinding.ActivityCreateNewSubjectBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Create_New_Subject extends AppCompatActivity {

    ActivityCreateNewSubjectBinding binding;
    private DatabaseReference dbsubject;
    ProgressDialog mDialog;
    RecyclerView recviewSubject;
    SubjectAdapter subjectAdapter;

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

        recviewSubject=(RecyclerView)findViewById(R.id.rcsubject);
        recviewSubject.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Subject> options =
                new FirebaseRecyclerOptions.Builder<Subject>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Subject"), Subject.class)
                        .build();

        subjectAdapter=new SubjectAdapter(options);
        recviewSubject.setAdapter(subjectAdapter);
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

    @Override
    protected void onStart() {
        super.onStart();
        subjectAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        subjectAdapter.stopListening();
    }
}