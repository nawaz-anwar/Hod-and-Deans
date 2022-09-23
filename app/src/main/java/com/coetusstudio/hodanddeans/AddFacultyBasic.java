package com.coetusstudio.hodanddeans;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.coetusstudio.hodanddeans.Activity.Faculty.AddfacultyActivity;
import com.coetusstudio.hodanddeans.Models.Faculty.AddFaculty;
import com.coetusstudio.hodanddeans.databinding.ActivityAddFacultyBasicBinding;
import com.coetusstudio.hodanddeans.databinding.ActivityAddfacultyBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddFacultyBasic extends AppCompatActivity {

    ActivityAddFacultyBasicBinding binding;
    ProgressDialog progressDialog;
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseStorage storage;
    Uri selectedImage;
    DatabaseReference  dbbranchref,dbsemesterref,dbsection,dbDeleteAttendance,dbsubject,dbsubjectCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddFacultyBasicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        dbbranchref=FirebaseDatabase.getInstance().getReference("Branch");
        dbsemesterref=FirebaseDatabase.getInstance().getReference("Semester");
        dbsection=FirebaseDatabase.getInstance().getReference("Section");
        dbDeleteAttendance=FirebaseDatabase.getInstance().getReference("DeleteAttendance");
        dbsubject=FirebaseDatabase.getInstance().getReference("Subject");
        dbsubjectCode=FirebaseDatabase.getInstance().getReference("Subject");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We're adding new faculty");

        binding.imageFacultyBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 45);
            }
        });

        binding.btnAddFacultyBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                auth.createUserWithEmailAndPassword(binding.emailFacultyBasic.getEditText().getText().toString(), binding.passwordFacultyBasic.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(selectedImage != null) {
                            StorageReference reference = storage.getReference().child("Faculty Testing").child(auth.getUid());
                            reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    if(task.isSuccessful()) {
                                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String imageUrl = uri.toString();

                                                AddFaculty addFaculty= new AddFaculty(imageUrl, binding.nameFacultyBasic.getEditText().getText().toString(), binding.emailFacultyBasic.getEditText().getText().toString(),
                                                        binding.idFacultyBasic.getEditText().getText().toString(), binding.passwordFacultyBasic.getEditText().getText().toString(), auth.getUid());


                                                database.getReference().child("Faculty").child(FirebaseAuth.getInstance().getUid()).setValue(addFaculty);
                                            }
                                        });
                                    }
                                }
                            });
                        }

                    }
                });
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null) {
            if(data.getData() != null) {
                binding.imageFacultyBasic.setImageURI(data.getData());
                selectedImage = data.getData();
            }
        }
    }
}