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
import com.coetusstudio.hodanddeans.Adapter.Adminstrator.TitleAdapter;
import com.coetusstudio.hodanddeans.Models.Administrator.Section;
import com.coetusstudio.hodanddeans.Models.Administrator.SessionalTitle;
import com.coetusstudio.hodanddeans.R;
import com.coetusstudio.hodanddeans.databinding.ActivityCreateSessionalTitleBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Create_Sessional_Title extends AppCompatActivity {

    ActivityCreateSessionalTitleBinding binding;
    private DatabaseReference dbsessional;
    ProgressDialog mDialog;
    RecyclerView recviewTitle;
    TitleAdapter titleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateSessionalTitleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbsessional= FirebaseDatabase.getInstance().getReference().child("Sessional");

        binding.btnNewSessionalTitlte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.createSessional.getEditText().getText().toString().isEmpty()) {
                    binding.createSessional.setError("Empty");
                    binding.createSessional.requestFocus();
                } else {
                    sendlink();
                }
            }
        });

        recviewTitle=(RecyclerView)findViewById(R.id.rcsessional);
        recviewTitle.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<SessionalTitle> options =
                new FirebaseRecyclerOptions.Builder<SessionalTitle>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Sessional"), SessionalTitle.class)
                        .build();

        titleAdapter=new TitleAdapter(options);
        recviewTitle.setAdapter(titleAdapter);
    }
    private void sendlink() {

        String sessionalTitle = binding.createSessional.getEditText().getText().toString().toUpperCase().trim();
        String id = dbsessional.push().getKey();

        HashMap<String,String> hashMap=new HashMap<>();

        hashMap.put("sessionalTitle",sessionalTitle);
        hashMap.put("id",id);


        dbsessional.child(id).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Create_Sessional_Title.this, "Added Successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Create_Sessional_Title.this, "Please, try again later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        titleAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        titleAdapter.stopListening();
    }
}