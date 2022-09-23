package com.coetusstudio.hodanddeans;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.coetusstudio.hodanddeans.Activity.Faculty.UpdateanddeletefacultyActivity;
import com.coetusstudio.hodanddeans.Activity.Lecture.LivemeetingActivity;
import com.coetusstudio.hodanddeans.Models.Administrator.Section;
import com.coetusstudio.hodanddeans.Models.Administrator.Semester;
import com.coetusstudio.hodanddeans.R;
import com.coetusstudio.hodanddeans.databinding.ActivitySelectLectureTypeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectLectureType extends AppCompatActivity {

    ActivitySelectLectureTypeBinding binding;
    String semester, section, userImage;
    DatabaseReference dbSemesterRef, dbSectionRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectLectureTypeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        userImage = intent.getStringExtra("image");

        dbSemesterRef = FirebaseDatabase.getInstance().getReference().child("Semester");
        dbSectionRef = FirebaseDatabase.getInstance().getReference().child("Section");
        dbSectionRef.keepSynced(true);


        //Spinner for Semester
        final List<String> listSemester = new ArrayList<String>();
        listSemester.add("Select Semester");

        ArrayAdapter<String> SemesterArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listSemester);
        SemesterArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerSemesterLecture.setAdapter(SemesterArrayAdapter);

        binding.spinnerSemesterLecture.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                semester = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        dbSemesterRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                    Semester semester = dsp.getValue(Semester.class);

                    listSemester.add(semester.getSemester());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Spinner for Section
        final List<String> listSection = new ArrayList<String>();
        listSection.add("Select Section");

        ArrayAdapter<String> SectionArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listSection);
        SectionArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerSectionLecture.setAdapter(SectionArrayAdapter);

        binding.spinnerSectionLecture.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                section = parent.getItemAtPosition(position).toString();
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

        binding.btnLectureDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LivemeetingActivity.class);
                intent.putExtra("semester", semester);
                intent.putExtra("section", section);
                intent.putExtra("image", userImage);
                startActivity(intent);
                finish();
            }
        });

    }
}