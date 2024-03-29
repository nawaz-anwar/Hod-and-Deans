package com.coetusstudio.hodanddeans;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.coetusstudio.hodanddeans.Activity.Notification.RecentFacultyNotification;
import com.coetusstudio.hodanddeans.Activity.Notification.RecentnoticeActivity;
import com.coetusstudio.hodanddeans.Models.Notification.NoticeData;
import com.coetusstudio.hodanddeans.databinding.ActivitySendnotificationBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SendnotificationActivity extends AppCompatActivity {

    ActivitySendnotificationBinding binding;
    Uri filepath;
    Boolean isImageUploaded = false;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    private ProgressDialog pd;
    FirebaseAuth auth;
    FirebaseUser currentUser;
    String adminImage, notificationType, semester, section;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySendnotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        pd = new ProgressDialog(this);

        Intent intent = getIntent();
        section = intent.getStringExtra("section");

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference();

        // Spinner Notification Type
        final List<String> lstfacsub=new ArrayList<String>();
        lstfacsub.add("Select Notification Type*");
        lstfacsub.add("All Faculty And Students");
        lstfacsub.add("Only Faculty");

        ArrayAdapter<String> AttendanceTypeSubArrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,lstfacsub);
        AttendanceTypeSubArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerNotificationType.setAdapter(AttendanceTypeSubArrayAdapter);

        binding.spinnerNotificationType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                notificationType=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("IIMTU").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dsp : snapshot.getChildren()) {
                    try {
                        adminImage = dsp.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userImage").getValue(String.class).toString();

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.btnRecentStudentNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SendnotificationActivity.this, RecentnoticeActivity.class);
                intent.putExtra("section", section);
                startActivity(intent);
            }
        });

        binding.btnRecentFacultyNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SendnotificationActivity.this, RecentFacultyNotification.class);
                intent.putExtra("section", section);
                startActivity(intent);
            }
        });

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        String date = currentDate.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        String time = currentTime.format(calForTime.getTime());


        binding.addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent=new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent,"Select Image Files"),101);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        binding.btnSendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isImageUploaded.equals(false)){

                    uploadNotice(date,time);
                }else {
                    processupload(filepath, date, time);
                }
            }
        });

    }

    private void uploadNotice(String date, String time) {
        if (binding.noticeTitle.getEditableText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Please! Enter Notice Description", Toast.LENGTH_SHORT).show();
        }else {
            NoticeData noticeData = new NoticeData(binding.noticeTitle.getEditableText().toString(), date, time, adminImage);
            databaseReference.child("Notice").child(section).child(notificationType).child(databaseReference.push().getKey()).setValue(noticeData).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getApplicationContext(), "Notice Uploaded", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Please, try again later!", Toast.LENGTH_SHORT).show();
                }
            });

            //pd.dismiss();

            binding.noticeTitle.setText("");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==101 && resultCode==RESULT_OK)
        {
            filepath=data.getData();
            binding.noticeImageView.setImageURI(data.getData());
            isImageUploaded = true;
        }else {
            //processupload(filepath);
        }
    }
    public void processupload(Uri filepath, String date, String time)
    {
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setTitle("Notice Uploading....!!!");
        pd.show();


        if (notificationType.equals("Select Notification Type*")){
            Toast.makeText(getApplicationContext(), "Please! Select Notification Type", Toast.LENGTH_SHORT).show();
        }else {
            final StorageReference reference = storageReference.child("Notice Data/" + System.currentTimeMillis() + ".pdf");
            reference.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    NoticeData noticeData = new NoticeData(binding.noticeTitle.getEditableText().toString(), uri.toString(), date, time, adminImage);
                                    databaseReference.child("Notice").child(section).child(notificationType).child(databaseReference.push().getKey()).setValue(noticeData);

                                    pd.dismiss();
                                    Toast.makeText(getApplicationContext(), "Notice Uploaded", Toast.LENGTH_LONG).show();


                                    binding.noticeTitle.setText("");
                                    binding.noticeImageView.setVisibility(View.INVISIBLE);
                                }
                            });

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            float percent = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            pd.setMessage("Uploaded :" + (int) percent + "%");
                        }
                    });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}