package com.coetusstudio.hodanddeans.Activity.Home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.coetusstudio.hodanddeans.Activity.Faculty.AddfacultyActivity;
import com.coetusstudio.hodanddeans.Models.Admin.User;
import com.coetusstudio.hodanddeans.Models.Administrator.Branch;
import com.coetusstudio.hodanddeans.Models.Administrator.Section;
import com.coetusstudio.hodanddeans.Models.Administrator.Semester;
import com.coetusstudio.hodanddeans.Models.Administrator.Subject;
import com.coetusstudio.hodanddeans.Models.Faculty.AddFaculty;
import com.coetusstudio.hodanddeans.R;
import com.coetusstudio.hodanddeans.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    ProgressDialog progressDialog;
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseStorage storage;
    Uri image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We're adding new User");

        binding.userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 46);
            }
        });


        binding.btnUserRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                auth.createUserWithEmailAndPassword(binding.userEmail.getEditText().getText().toString(), binding.userPassword.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(image != null) {
                            StorageReference reference = storage.getReference().child("User Profiles").child(auth.getUid());
                            reference.putFile(image).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    if(task.isSuccessful()) {
                                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                Toast.makeText(getApplicationContext(), "New Account Created", Toast.LENGTH_SHORT).show();
                                                String imageUrl = uri.toString();

                                                User user= new User(imageUrl, binding.userName.getEditText().getText().toString(),binding.userEmail.getEditText().getText().toString(),
                                                        binding.userID.getEditText().getText().toString(), binding.userPosition.getEditText().getText().toString(), binding.userSchool.getEditText().getText().toString(), binding.userPassword.getEditText().getText().toString(), auth.getUid());


                                                database.getReference().child("IIMTU").child("User").child(FirebaseAuth.getInstance().getUid()).setValue(user);
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
                binding.userImage.setImageURI(data.getData());
                image = data.getData();
            }
        }
    }
}