package com.coetusstudio.hodanddeans.Activity.Notification;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.coetusstudio.hodanddeans.Models.Notification.NoticeData;
import com.coetusstudio.hodanddeans.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

public class SentnotificationActivity extends AppCompatActivity {

    private CardView sendNotification;
    private Bitmap bitmap;
    private ImageView addImg, noticeImageView;
    Uri filepath;
    private EditText noticeTitle;
    private Button btnSendNotification, btnRecentNotification, btnRecentFacultyNotification;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentnotification);


        pd = new ProgressDialog(this);

        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference();

        sendNotification = findViewById(R.id.sendNotification);
        noticeImageView = findViewById(R.id.noticeImageView);
        addImg = findViewById(R.id.addImg);
        noticeTitle = findViewById(R.id.noticeTitle);
        btnSendNotification = findViewById(R.id.btnSendNotification);
        btnRecentNotification = findViewById(R.id.btnRecentStudentNotification);
        btnRecentFacultyNotification = findViewById(R.id.btnRecentFacultyNotification);

        btnRecentNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecentnoticeActivity.class);
                startActivity(intent);
            }
        });

        btnRecentFacultyNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecentFacultyNotification.class);
                startActivity(intent);
            }
        });


        addImg.setOnClickListener(new View.OnClickListener() {
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

        btnSendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processupload(filepath);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==101 && resultCode==RESULT_OK)
        {
            filepath=data.getData();
            noticeImageView.setImageURI(data.getData());
        }
    }
    public void processupload(Uri filepath)
    {
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setTitle("Notice Uploading....!!!");
        pd.show();

        final StorageReference reference=storageReference.child("Notice Data/"+System.currentTimeMillis()+".pdf");
        reference.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                NoticeData noticeData=new NoticeData(noticeTitle.getText().toString(),uri.toString());
                                databaseReference.child("Notice").child(databaseReference.push().getKey()).setValue(noticeData);

                                pd.dismiss();
                                Toast.makeText(getApplicationContext(),"Notice Uploaded",Toast.LENGTH_LONG).show();


                                noticeTitle.setText("");
                                noticeImageView.setVisibility(View.INVISIBLE);
                            }
                        });

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        float percent=(100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                        pd.setMessage("Uploaded :"+(int)percent+"%");
                    }
                });

    }
}