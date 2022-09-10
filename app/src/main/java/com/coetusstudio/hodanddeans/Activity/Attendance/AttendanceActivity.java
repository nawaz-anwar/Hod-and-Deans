package com.coetusstudio.hodanddeans.Activity.Attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.coetusstudio.hodanddeans.Models.Administrator.Section;
import com.coetusstudio.hodanddeans.Models.Administrator.Subject;
import com.coetusstudio.hodanddeans.Models.Faculty.AddFaculty;
import com.coetusstudio.hodanddeans.databinding.ActivityAttendanceBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity {

    ActivityAttendanceBinding binding;
    String facultySubject, facultySection;
    DatabaseReference dbSubjectRef, dbSectionRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAttendanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbSubjectRef = FirebaseDatabase.getInstance().getReference().child("Subject");
        dbSectionRef = FirebaseDatabase.getInstance().getReference().child("Section");
        dbSubjectRef.keepSynced(true);

        //Spinner for Section
        final List<String> listSection = new ArrayList<String>();
        listSection.add("Select Section");

        ArrayAdapter<String> SectionArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listSection);
        SectionArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerSectionAttendance.setAdapter(SectionArrayAdapter);

        binding.spinnerSectionAttendance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                facultySection = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        dbSectionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                    Section section = dsp.getValue(Section.class);

                    listSection.add(section.getSection());

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
        binding.spinnerSubjectAttendance.setAdapter(subjectArrayAdapter);

        binding.spinnerSubjectAttendance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                facultySubject=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dbSubjectRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dsp :dataSnapshot.getChildren()){

                    Subject br = dsp.getValue(Subject.class);

                    listSubject.add(br.getSubject());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        binding.btnViewCollectiveAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(AttendanceActivity.this, View_Attendance.class);
                intent1.putExtra("facultySubject1",facultySubject);
                intent1.putExtra("facultySection1",facultySection);
                startActivity(intent1);
            }
        });

        binding.btnDeleteAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttendanceActivity.this, Delete_Attendance.class);
                intent.putExtra("facultySubject2",facultySubject);
                intent.putExtra("facultySection2",facultySection);
                startActivity(intent);
            }
        });
    }
}