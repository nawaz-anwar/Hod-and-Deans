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
import com.coetusstudio.hodanddeans.Adapter.Adminstrator.SemesterAdapter;
import com.coetusstudio.hodanddeans.Models.Administrator.Section;
import com.coetusstudio.hodanddeans.Models.Administrator.Semester;
import com.coetusstudio.hodanddeans.R;
import com.coetusstudio.hodanddeans.databinding.ActivityCreateNewSectionBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Create_New_Section extends AppCompatActivity {

    ActivityCreateNewSectionBinding binding;
    private DatabaseReference dbbatchname;
    ProgressDialog mDialog;
    RecyclerView recviewSection;
    SectionAdapter sectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateNewSectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbbatchname= FirebaseDatabase.getInstance().getReference().child("Section");

        binding.btnNewSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.createSection.getEditText().getText().toString().isEmpty()) {
                    binding.createSection.setError("Empty");
                    binding.createSection.requestFocus();
                } else {
                    sendlink();
                }
            }
        });

        recviewSection=(RecyclerView)findViewById(R.id.rcsection);
        recviewSection.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Section> options =
                new FirebaseRecyclerOptions.Builder<Section>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Section"), Section.class)
                        .build();

        sectionAdapter=new SectionAdapter(options);
        recviewSection.setAdapter(sectionAdapter);
    }
    private void sendlink() {

        String lectureName = binding.createSection.getEditText().getText().toString().toUpperCase().trim();
        String id = dbbatchname.push().getKey();

        HashMap<String,String> hashMap=new HashMap<>();

        hashMap.put("section",lectureName);
        hashMap.put("id",id);


        dbbatchname.child(id).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Create_New_Section.this, "Added Successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Create_New_Section.this, "Please, try again later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        sectionAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        sectionAdapter.stopListening();
    }
}