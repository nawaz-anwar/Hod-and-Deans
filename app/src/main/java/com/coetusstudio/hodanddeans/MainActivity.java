package com.coetusstudio.hodanddeans;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.coetusstudio.hodanddeans.Models.AddFaculty;
import com.coetusstudio.hodanddeans.Models.StudentDetails;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.coetusstudio.hodanddeans.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    CardView sendNotification, addNewFaculty, updateDeleteFaculty, studentDetails, liveMeeting, UpdateDeleteAccount, createForm, chat, addAccountFaculty;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActivityMainBinding binding;
    FirebaseAuth auth;
    FirebaseUser currentUser ;
    DatabaseReference database;
    String facultyImage, facultyName, facultyEmail, facultyId;
    CircleImageView headerImage;
    TextView headerName, headerEmail, headerID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setTitle("HOD and DEANS");

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        sendNotification = findViewById(R.id.sendNotification);
        addNewFaculty = findViewById(R.id.addNewFaculty);
        updateDeleteFaculty = findViewById(R.id.updateDeleteFaculty);
        liveMeeting = findViewById(R.id.liveMeeting);
        studentDetails = findViewById(R.id.studentDetails);
        UpdateDeleteAccount = findViewById(R.id.updateDeleteAccount);
        createForm = findViewById(R.id.createForm);
        addAccountFaculty = findViewById(R.id.addAccountFaculty);

        sendNotification.setOnClickListener(this);
        addNewFaculty.setOnClickListener(this);
        updateDeleteFaculty.setOnClickListener(this);
        liveMeeting.setOnClickListener(this);
        studentDetails.setOnClickListener(this);
        UpdateDeleteAccount.setOnClickListener(this);
        createForm.setOnClickListener(this);
        addAccountFaculty.setOnClickListener(this);





        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.CloseDrawer);



        toggle.syncState();

        updateNavHeader();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.profile) {
                    Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();

                } else if (id == R.id.profileSendNotification) {
                    Intent intent = new Intent(MainActivity.this, SentnotificationActivity.class);
                    startActivity(intent);

                } else if (id == R.id.privacyPolicy) {
                    Uri webpage = Uri.parse("https://www.iimtu.com/privacy-policy");
                    Intent webMeet = new Intent(Intent.ACTION_VIEW, webpage);
                    startActivity(webMeet);
                } else if (id == R.id.aboutUs) {
                    Uri webpage = Uri.parse("https://www.iimtu.com/about-iimt/our-founder");
                    Intent webMeet = new Intent(Intent.ACTION_VIEW, webpage);
                    startActivity(webMeet);
                } else if (id == R.id.logout) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                }

                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });


    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sendNotification:
                Intent intent = new Intent(MainActivity.this, SentnotificationActivity.class);
                startActivity(intent);
                break;
            case R.id.addNewFaculty:
                Intent intent1 = new Intent(MainActivity.this, AddfacultyActivity.class);
                startActivity(intent1);
                break;
            case R.id.addAccountFaculty:
                Intent intent2 = new Intent(MainActivity.this, AddaccountfacultyActivity.class);
                startActivity(intent2);
                break;
            case R.id.liveMeeting:
                Intent intent3 = new Intent(MainActivity.this, LivemeetingActivity.class);
                startActivity(intent3);
            case R.id.updateDeleteFaculty:
                Intent intent4 = new Intent(MainActivity.this, UpdateanddeletefacultyActivity.class);
                startActivity(intent4);
                break;
            case R.id.updateDeleteAccount:
                Intent intent5 = new Intent(MainActivity.this, CraeteBranchSectionActivity.class);
                startActivity(intent5);
                break;
            case R.id.createForm:
                Intent intent6 = new Intent(MainActivity.this, CreateformActivity.class);
                startActivity(intent6);
                break;
            case R.id.studentDetails:
                Intent intent7 = new Intent(MainActivity.this, StudentdetailsActivity.class);
                startActivity(intent7);
                break;
        }
    }
    public void updateNavHeader() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.headerName);
        TextView navUserMail = headerView.findViewById(R.id.headerEmail);
        ImageView navUserPhot = headerView.findViewById(R.id.headerImage);

        // now we will use Glide to load user image
        // first we need to import the library

        FirebaseDatabase.getInstance().getReference().child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    //StudentDetails studentDetails = snapshot.getValue(StudentDetails.class);
                    //acultyImage = snapshot.child("imageAccount").getValue().toString();
                    //facultyName  = snapshot.child("studentName").getValue().toString();
                    //facultyEmail = snapshot.child("emailAccount").getValue().toString();
                    //facultyId = snapshot.child("facultyId").getValue().toString();
                    navUsername.setText(snapshot.child("userName").getValue().toString());
                    navUserMail.setText(snapshot.child("userEmail").getValue().toString());
                    Glide.with(MainActivity.this).load("https://firebasestorage.googleapis.com/v0/b/hod-and-deans.appspot.com/o/Faculty%20Profiles%2FNofPKGzvLefR7N229EPI7WiNdI93?alt=media&token=04a9ba67-655a-4f3c-a779-ace3daa2b9d5").into(navUserPhot);
                    //navUserMail.setText(StudentDetails.getStudentName());
                    //headerEmail.setText(facultyEmail);
                    //headerID.setText(facultyId);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });



    }


}