package com.coetusstudio.hodanddeans;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.coetusstudio.hodanddeans.Models.Lecture;
import com.coetusstudio.hodanddeans.databinding.ActivityLivemeetingBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LivemeetingActivity extends AppCompatActivity {

    ActivityLivemeetingBinding binding;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLivemeetingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        reference = FirebaseDatabase.getInstance().getReference().child("Lecture");

        binding.btnRecentLecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LivemeetingActivity.this, RecentmeetingActivity.class);
                startActivity(intent);
            }
        });


        binding.btnSendLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.lectureLink.getEditText().getText().toString().isEmpty()) {
                    binding.lectureLink.setError("Empty");
                    binding.lectureLink.requestFocus();
                } else {
                    sendlink();
                }
            }
        });

        binding.btnMeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse("https://meet.google.com/");
                Intent webMeet = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webMeet);
            }
        });
        binding.btnZoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage1 = Uri.parse("https://zoom.us/signin");
                Intent webZoom = new Intent(Intent.ACTION_VIEW, webpage1);
                startActivity(webZoom);
            }
        });
        binding.btnMsTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage3 = Uri.parse("https://teams.live.com/");
                Intent webMsTeam = new Intent(Intent.ACTION_VIEW, webpage3);
                startActivity(webMsTeam);
            }
        });
        binding.btnWebex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage3 = Uri.parse("https://www.webex.com/");
                Intent webWebex = new Intent(Intent.ACTION_VIEW, webpage3);
                startActivity(webWebex);
            }
        });

    }

    private void sendlink() {

        String lectureName = binding.lectureName.getEditText().getText().toString();
        String lectureLink = binding.lectureLink.getEditText().getText().toString();
        String lectureTiming = binding.lectureTiming.getEditText().getText().toString();
        Lecture lecture = new Lecture(lectureName,lectureLink,lectureTiming);

        reference.push().setValue(lecture).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(LivemeetingActivity.this, "Link sent to the student", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LivemeetingActivity.this, "Please, try again later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}