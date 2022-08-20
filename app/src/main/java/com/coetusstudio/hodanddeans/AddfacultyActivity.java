package com.coetusstudio.hodanddeans;

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

import com.coetusstudio.hodanddeans.Models.Accountdata;
import com.coetusstudio.hodanddeans.Models.AddFaculty;
import com.coetusstudio.hodanddeans.Models.Branch;
import com.coetusstudio.hodanddeans.Models.Section;
import com.coetusstudio.hodanddeans.Models.Semester;
import com.coetusstudio.hodanddeans.Models.Subject;
import com.coetusstudio.hodanddeans.Models.Users;
import com.coetusstudio.hodanddeans.databinding.ActivityAddfacultyBinding;
import com.coetusstudio.hodanddeans.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AddfacultyActivity extends AppCompatActivity {

    ActivityAddfacultyBinding binding;
    ProgressDialog progressDialog;
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseStorage storage;
    Uri selectedImage;
    DatabaseReference dbbranchref,dbsemesterref,dbsection,dbDeleteAttendance,dbsubject,dbsubjectCode;
    String itemFaculty,item_subject,item_subjectCode,itemAdmissionYear,item_branch,item_division,item_classType;
    HashMap<String,String> hashMapBranch=new HashMap<>();
    HashMap<String,String> hashMapDivision=new HashMap<>();
    HashMap<String,String> hashMapClassType=new HashMap<>();
    HashMap<String,String> hashMapSubject=new HashMap<>();
    HashMap<String,String> hashMapSubjectCode=new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddfacultyBinding.inflate(getLayoutInflater());
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

        progressDialog = new ProgressDialog(AddfacultyActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We're adding new faculty");

        binding.imageFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 45);
            }
        });

        //Spinner for Branch
        final List<String> listBranch=new ArrayList<String>();
        listBranch.add("Select Branch");

        ArrayAdapter<String> branchArrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listBranch);
        branchArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerBranchFaculty.setAdapter(branchArrayAdapter);

        binding.spinnerBranchFaculty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item_branch=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dbbranchref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dsp :dataSnapshot.getChildren()){

                    Branch br = dsp.getValue(Branch.class);

                    hashMapBranch.put(br.getBranch(),br.getId());

                    listBranch.add(br.getBranch());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Spinner for Semester
        final List<String> listDivision=new ArrayList<String>();
        listDivision.add("Select Semester");

        ArrayAdapter<String> divisionArrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listDivision);
        divisionArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerSemesterFaculty.setAdapter(divisionArrayAdapter);

        binding.spinnerSemesterFaculty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item_division=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dbsemesterref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dsp :dataSnapshot.getChildren()){

                    Semester di = dsp.getValue(Semester.class);

                    hashMapDivision.put(di.getSemester(),di.getId());

                    listDivision.add(di.getSemester());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Spinner for Section
        final List<String> listClassType=new ArrayList<String>();
        listClassType.add("Select Section");

        ArrayAdapter<String> ClassTypeArrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listClassType);
        ClassTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerSectionFaculty.setAdapter(ClassTypeArrayAdapter);

        binding.spinnerSectionFaculty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item_classType=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dbsection.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dsp :dataSnapshot.getChildren()){

                    Section di = dsp.getValue(Section.class);

                    hashMapClassType.put(di.getSection(),di.getId());

                    listClassType.add(di.getSection());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Spinner for Subject
        final List<String> listSubject=new ArrayList<String>();
        listSubject.add("Select Subject");

        ArrayAdapter<String> subjectArrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listSubject);
        subjectArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerSubjectFaculty.setAdapter(subjectArrayAdapter);

        binding.spinnerSubjectFaculty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item_subject=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dbsubject.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dsp :dataSnapshot.getChildren()){

                    Subject br = dsp.getValue(Subject.class);

                    hashMapSubject.put(br.getSubject(),br.getId());

                    listSubject.add(br.getSubject());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Spinner for SubjectCode
        final List<String> listSubjectCode=new ArrayList<String>();
        listSubjectCode.add("Select Subject Code");

        ArrayAdapter<String> subjectCodeArrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listSubjectCode);
        subjectCodeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerSubjectCodeFaculty.setAdapter(subjectCodeArrayAdapter);

        binding.spinnerSubjectCodeFaculty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item_subjectCode=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dbsubjectCode.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dsp :dataSnapshot.getChildren()){

                    Subject br = dsp.getValue(Subject.class);

                    hashMapSubjectCode.put(br.getSubjectCode(),br.getId());

                    listSubjectCode.add(br.getSubjectCode());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        binding.btnAddFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                auth.createUserWithEmailAndPassword(binding.emailFaculty.getEditText().getText().toString(), binding.passwordFaculty.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(selectedImage != null) {
                            StorageReference reference = storage.getReference().child("Faculty Profiles").child(auth.getUid());
                            reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    if(task.isSuccessful()) {
                                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String imageUrl = uri.toString();

                                                AddFaculty addFaculty= new AddFaculty(imageUrl, binding.nameFaculty.getEditText().getText().toString(),binding.emailFaculty.getEditText().getText().toString(),
                                                        binding.idFaculty.getEditText().getText().toString(), item_subject, item_subjectCode, item_branch, item_division, item_classType, binding.passwordFaculty.getEditText().getText().toString(), auth.getUid());


                                                database.getReference().child("IIMTU").child("Faculty").child(FirebaseAuth.getInstance().getUid()).setValue(addFaculty);
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
                binding.imageFaculty.setImageURI(data.getData());
                selectedImage = data.getData();
            }
        }
    }
}