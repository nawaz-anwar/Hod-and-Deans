package com.coetusstudio.hodanddeans;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.coetusstudio.hodanddeans.Activity.Administrator.Create_New_Subject;
import com.coetusstudio.hodanddeans.databinding.ActivityCreateNewSubjectBinding;
import com.coetusstudio.hodanddeans.databinding.ActivityCredentialPasswordBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CredentialPassword extends AppCompatActivity {
    
    ActivityCredentialPasswordBinding binding;
    private DatabaseReference dbCredentialRef;
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCredentialPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbCredentialRef = FirebaseDatabase.getInstance().getReference().child("Credential");

        binding.btnNewCredentials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.createUsername.getEditText().getText().toString().isEmpty()) {
                    binding.createUsername.setError("Empty");
                    binding.createUsername.requestFocus();
                } else {
                    sendlink();
                }
            }
        });
    }
    private void sendlink() {

        String username = binding.createUsername.getEditText().getText().toString();
        String password = binding.createPassword.getEditText().getText().toString();

        HashMap<String,String> hashMap=new HashMap<>();

        hashMap.put("username",username);
        hashMap.put("password",password);


        dbCredentialRef.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(CredentialPassword.this, "Added Successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CredentialPassword.this, "Please, try again later!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}