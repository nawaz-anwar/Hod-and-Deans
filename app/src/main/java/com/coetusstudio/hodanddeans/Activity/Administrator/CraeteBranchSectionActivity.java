package com.coetusstudio.hodanddeans.Activity.Administrator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.coetusstudio.hodanddeans.Activity.Home.MainActivity;
import com.coetusstudio.hodanddeans.CredentialPassword;
import com.coetusstudio.hodanddeans.databinding.ActivityCraeteBranchSectionBinding;

public class CraeteBranchSectionActivity extends AppCompatActivity {

    ActivityCraeteBranchSectionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCraeteBranchSectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnCreateBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CraeteBranchSectionActivity.this, Create_New_Branch.class);
                startActivity(intent);
            }
        });

        binding.btnCreateSemester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CraeteBranchSectionActivity.this, Create_New_Semester.class);
                startActivity(intent);
            }
        });

        binding.btnCreateSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CraeteBranchSectionActivity.this, Create_New_Section.class);
                startActivity(intent);
            }
        });

        binding.btnCreateSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CraeteBranchSectionActivity.this, Create_New_Subject.class);
                startActivity(intent);
            }
        });

        binding.btnCreateSessional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CraeteBranchSectionActivity.this, Create_Sessional_Title.class);
                startActivity(intent);
            }
        });

        binding.btnCredentials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CraeteBranchSectionActivity.this, CredentialPassword.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}