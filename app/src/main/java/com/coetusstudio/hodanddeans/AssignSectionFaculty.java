package com.coetusstudio.hodanddeans;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.coetusstudio.hodanddeans.Models.Administrator.Branch;
import com.coetusstudio.hodanddeans.Models.Administrator.Section;
import com.coetusstudio.hodanddeans.Models.Administrator.Semester;
import com.coetusstudio.hodanddeans.Models.Administrator.Subject;
import com.coetusstudio.hodanddeans.Models.Faculty.AddFaculty;
import com.coetusstudio.hodanddeans.databinding.ActivityAddfacultyBinding;
import com.coetusstudio.hodanddeans.databinding.ActivityAssignSectionFacultyBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AssignSectionFaculty extends AppCompatActivity {

    ActivityAssignSectionFacultyBinding binding;
    ProgressDialog progressDialog;
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseStorage storage;
    Uri selectedImage;
    DatabaseReference dbbranchref,dbsemesterref,dbsection,dbFacultyRef,dbDeleteAttendance,dbsubject,dbsubjectCode;
    String itemFaculty,item_subject,item_subjectCode,facultyName,item_branch,item_division,item_classType;
    HashMap<String,String> hashMapBranch=new HashMap<>();
    HashMap<String,String> hashMapDivision=new HashMap<>();
    HashMap<String,String> hashMapClassType=new HashMap<>();
    HashMap<String,String> hashMapSubject=new HashMap<>();
    HashMap<String,String> hashMapSubjectCode=new HashMap<>();
    HashMap<String,String> hashMapFaculty=new HashMap<>();
    HashMap<String,String> hashMapFacultyEmailName=new HashMap<>();
    HashMap<String,String> hashMapFacultyImageID=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAssignSectionFacultyBinding.inflate(getLayoutInflater());
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
        dbFacultyRef=FirebaseDatabase.getInstance().getReference("Faculty");

        //Spinner for facultyName
        final List<String> listFacultyName=new ArrayList<String>();
        listFacultyName.add("Select Faculty Name");

        ArrayAdapter<String> facultyNameArrayAdapter=new ArrayAdapter<String>(AssignSectionFaculty.this,android.R.layout.simple_spinner_item,listFacultyName);
        facultyNameArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerFacultyNameBasic.setAdapter(facultyNameArrayAdapter);

        binding.spinnerFacultyNameBasic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                facultyName=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dbFacultyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dsp :dataSnapshot.getChildren()){

                    AddFaculty br = dsp.getValue(AddFaculty.class);

                    hashMapFaculty.put(br.getFacultyMessage(),br.getFacultyName());
                    hashMapFaculty.put(br.getFacultyEmail(),br.getFacultyId());
                    hashMapFaculty.put(br.getFacultyImage(),br.getFacultyPassword());

                    listFacultyName.add(br.getFacultyName());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Spinner for Branch
        final List<String> listBranch=new ArrayList<String>();
        listBranch.add("Select Branch");

        ArrayAdapter<String> branchArrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listBranch);
        branchArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerBranchFacultyBasic.setAdapter(branchArrayAdapter);

        binding.spinnerBranchFacultyBasic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        binding.spinnerSemesterFacultyBasic.setAdapter(divisionArrayAdapter);

        binding.spinnerSemesterFacultyBasic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        binding.spinnerSectionFacultyBasic.setAdapter(ClassTypeArrayAdapter);

        binding.spinnerSectionFacultyBasic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        binding.spinnerSubjectFacultyBasic.setAdapter(subjectArrayAdapter);

        binding.spinnerSubjectFacultyBasic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        binding.spinnerSubjectCodeFacultyBasic.setAdapter(subjectCodeArrayAdapter);

        binding.spinnerSubjectCodeFacultyBasic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        binding.btnAssignSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Faculty Assigned").setValue(hashMapFaculty).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Assigned Section to Faculty",Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }
}