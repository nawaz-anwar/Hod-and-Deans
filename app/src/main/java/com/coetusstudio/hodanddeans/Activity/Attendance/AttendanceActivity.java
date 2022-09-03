package com.coetusstudio.hodanddeans.Activity.Attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.coetusstudio.hodanddeans.databinding.ActivityAttendanceBinding;

public class AttendanceActivity extends AppCompatActivity {

    ActivityAttendanceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAttendanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnViewAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttendanceActivity.this, Select_Subject_Attendance.class);
                startActivity(intent);
            }
        });

        binding.btnDeleteAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttendanceActivity.this, Delete_Attendance.class);
                startActivity(intent);
            }
        });
    }
}