package com.coetusstudio.hodanddeans.Activity.Attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.coetusstudio.hodanddeans.Models.Administrator.Section;
import com.coetusstudio.hodanddeans.Models.Faculty.AddFaculty;
import com.coetusstudio.hodanddeans.databinding.ActivitySelectSubjectAttendanceBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Select_Subject_Attendance extends AppCompatActivity {

    ActivitySelectSubjectAttendanceBinding binding;
    String facultySubject, facultySection;
    DatabaseReference dbSubjectRef, dbSectionRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectSubjectAttendanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbSubjectRef = FirebaseDatabase.getInstance().getReference().child("IIMTU").child("Faculty");
        dbSectionRef = FirebaseDatabase.getInstance().getReference().child("Section");
        dbSubjectRef.keepSynced(true);

        //Spinner for Section
        final List<String> listSection = new ArrayList<String>();
        listSection.add("Select Section");

        ArrayAdapter<String> SectionArrayAdapter = new ArrayAdapter<String>(Select_Subject_Attendance.this, android.R.layout.simple_spinner_item, listSection);
        SectionArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerAttendanceSection.setAdapter(SectionArrayAdapter);

        binding.spinnerAttendanceSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        //Spinner for subject
        final List<String> listSubject = new ArrayList<String>();
        listSubject.add("Select Subject");

        ArrayAdapter<String> subjectArrayAdapter = new ArrayAdapter<String>(Select_Subject_Attendance.this, android.R.layout.simple_spinner_item, listSubject);
        subjectArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerAttendanceSubject.setAdapter(subjectArrayAdapter);

        binding.spinnerAttendanceSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                facultySubject = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        dbSubjectRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                    AddFaculty addFaculty=dsp.getValue(AddFaculty.class);

                    listSubject.add(addFaculty.getFacultySubject());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /*
        //Spinner for Date And Time
        final List<String> listDate = new ArrayList<String>();
        listDate.add("Select Date");

        ArrayAdapter<String> dateArrayAdapter = new ArrayAdapter<String>(Select_Subject_Attendance.this, android.R.layout.simple_spinner_item, listDate);
        dateArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerAttendanceDate.setAdapter(dateArrayAdapter);

        binding.spinnerAttendanceDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                attendanceDate = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        dbDateRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.child(facultySection).child(facultySubject).getChildren()) {

                    listSubject.add(dsp.getKey());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

         */


        binding.btnViewAttendanceByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), View_Collective_Attendance.class);
                intent.putExtra("facultySubject1",facultySubject);
                intent.putExtra("facultySection1",facultySection);
                startActivity(intent);
            }
        });
    }
}