package com.coetusstudio.hodanddeans;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.coetusstudio.hodanddeans.Activity.Faculty.UpdateanddeletefacultyActivity;
import com.coetusstudio.hodanddeans.Models.Administrator.Section;
import com.coetusstudio.hodanddeans.Models.Administrator.Semester;
import com.coetusstudio.hodanddeans.R;
import com.coetusstudio.hodanddeans.databinding.ActivitySelectSectionFacultyBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectSectionFacultyActivity extends AppCompatActivity {

    ActivitySelectSectionFacultyBinding binding;
    String semester, section;
    DatabaseReference dbSemesterRef, dbSectionRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectSectionFacultyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}