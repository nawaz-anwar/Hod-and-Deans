package com.coetusstudio.hodanddeans.Activity.Lecture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.coetusstudio.hodanddeans.Models.Lecture.Lecture;
import com.coetusstudio.hodanddeans.databinding.ActivityLivemeetingBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LivemeetingActivity extends AppCompatActivity {

    ActivityLivemeetingBinding binding;
    DatabaseReference reference;
    ProgressDialog progressDialog;
    String section, userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLivemeetingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        section = intent.getStringExtra("section");
        userImage = intent.getStringExtra("image");

        progressDialog = new ProgressDialog(LivemeetingActivity.this);
        progressDialog.setTitle("Creating Meeting");
        progressDialog.setMessage("Link Sending...");

        reference = FirebaseDatabase.getInstance().getReference().child("Lecture");

        binding.btnRecentLecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LivemeetingActivity.this, RecentmeetingActivity.class);
                intent.putExtra("section", section);
                startActivity(intent);
            }
        });


        binding.btnSendLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
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

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        String date = currentDate.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        String time = currentTime.format(calForTime.getTime());

        String lectureName = binding.lectureName.getEditText().getText().toString();
        String lectureLink = binding.lectureLink.getEditText().getText().toString();
        String lectureTiming = binding.lectureTiming.getEditText().getText().toString();
        Lecture lecture = new Lecture(lectureName,lectureTiming,lectureLink,date,time, userImage, section);


        reference.child(section).push().setValue(lecture).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                binding.lectureName.getEditText().setText("");
                binding.lectureLink.getEditText().setText("");
                binding.lectureTiming.getEditText().setText("");
                progressDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LivemeetingActivity.this, "Please, try again later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}