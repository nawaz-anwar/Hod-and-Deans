package com.coetusstudio.hodanddeans;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.coetusstudio.hodanddeans.Activity.Attendance.AttendanceActivity;
import com.coetusstudio.hodanddeans.Activity.Attendance.Delete_Attendance;
import com.coetusstudio.hodanddeans.Activity.Attendance.View_Attendance;
import com.coetusstudio.hodanddeans.Models.Administrator.Section;
import com.coetusstudio.hodanddeans.Models.Administrator.Subject;
import com.coetusstudio.hodanddeans.databinding.ActivityAttendanceBinding;
import com.coetusstudio.hodanddeans.databinding.ActivityFacultyBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FacultyActivity extends AppCompatActivity {

    ActivityFacultyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFacultyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.btnAddFacultyBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(FacultyActivity.this, AddFacultyBasic.class);
                startActivity(intent1);
            }
        });

        binding.btnAssignSectionBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FacultyActivity.this, AssignSectionFaculty.class);
                startActivity(intent);
            }
        });
    }
}