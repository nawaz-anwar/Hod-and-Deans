package com.coetusstudio.hodanddeans.Activity.Administrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.coetusstudio.hodanddeans.databinding.ActivityCreateSessionalTitleBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Create_Sessional_Title extends AppCompatActivity {

    ActivityCreateSessionalTitleBinding binding;
    private DatabaseReference dbsessional;
    ProgressDialog mDialog;

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
}