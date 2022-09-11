package com.coetusstudio.hodanddeans.Activity.Home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.coetusstudio.hodanddeans.Activity.Administrator.CraeteBranchSectionActivity;
import com.coetusstudio.hodanddeans.Activity.Faculty.AddfacultyActivity;
import com.coetusstudio.hodanddeans.Notification_Type_Activity;
import com.coetusstudio.hodanddeans.SelectSectionStudent;
import com.coetusstudio.hodanddeans.Activity.Attendance.AttendanceActivity;
import com.coetusstudio.hodanddeans.Activity.Form.CreateformActivity;
import com.coetusstudio.hodanddeans.Activity.Lecture.LivemeetingActivity;
import com.coetusstudio.hodanddeans.R;
import com.coetusstudio.hodanddeans.SelectSectionActivity;
import com.coetusstudio.hodanddeans.SendnotificationActivity;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    CardView sendNotification, addNewFaculty, updateDeleteFaculty, studentDetails, liveMeeting, administrator, createForm, chat, attendance;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActivityMainBinding binding;
    FirebaseAuth auth;
    FirebaseUser currentUser;
    DatabaseReference database;
    String facultyImage, facultyName, facultyEmail, facultyId;
    CircleImageView headerImage;
    TextView headerName, headerEmail, headerID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        sendNotification = findViewById(R.id.sendNotification);
        addNewFaculty = findViewById(R.id.addNewFaculty);
        updateDeleteFaculty = findViewById(R.id.updateDeleteFaculty);
        liveMeeting = findViewById(R.id.liveMeeting);
        studentDetails = findViewById(R.id.studentDetails);
        administrator = findViewById(R.id.administrator);
        createForm = findViewById(R.id.createForm);
        attendance = findViewById(R.id.attendance);

        sendNotification.setOnClickListener(this);
        addNewFaculty.setOnClickListener(this);
        updateDeleteFaculty.setOnClickListener(this);
        liveMeeting.setOnClickListener(this);
        studentDetails.setOnClickListener(this);
        administrator.setOnClickListener(this);
        createForm.setOnClickListener(this);
        attendance.setOnClickListener(this);





        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.CloseDrawer);
        toggle.syncState();

        toolbar.setTitle("IIMT HoDs And Deans");

        updateNavHeader();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.profile) {
                    startActivity(new Intent(getApplicationContext(), UserActivity.class));

                } else if (id == R.id.profileSendNotification) {
                    Intent intent = new Intent(MainActivity.this, SendnotificationActivity.class);
                    startActivity(intent);

                } else if (id == R.id.privacyPolicy) {
                    Uri webpage = Uri.parse("https://www.iimtu.com/privacy-policy");
                    Intent webMeet = new Intent(Intent.ACTION_VIEW, webpage);
                    startActivity(webMeet);

                } else if (id == R.id.aboutUs) {
                    Uri webpage = Uri.parse("https://www.iimtu.com/about-iimt/our-founder");
                    Intent webMeet = new Intent(Intent.ACTION_VIEW, webpage);
                    startActivity(webMeet);

                } else if (id == R.id.resetPassword) {
                    startActivity(new Intent(getApplicationContext(), ForgetPasswordActivity.class));

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
                Intent intent = new Intent(MainActivity.this, Notification_Type_Activity.class);
                startActivity(intent);
                break;
            case R.id.addNewFaculty:
                Intent intent1 = new Intent(MainActivity.this, AddfacultyActivity.class);
                startActivity(intent1);
                break;
            case R.id.attendance:
                Intent intent2 = new Intent(MainActivity.this, AttendanceActivity.class);
                startActivity(intent2);
                break;
            case R.id.liveMeeting:
                Intent intent3 = new Intent(MainActivity.this, LivemeetingActivity.class);
                startActivity(intent3);
                break;
            case R.id.updateDeleteFaculty:
                Intent intent4 = new Intent(MainActivity.this, SelectSectionActivity.class);
                startActivity(intent4);
                break;
            case R.id.administrator:
                Intent intent5 = new Intent(MainActivity.this, CraeteBranchSectionActivity.class);
                startActivity(intent5);
                break;
            case R.id.createForm:
                Intent intent6 = new Intent(MainActivity.this, CreateformActivity.class);
                startActivity(intent6);
                break;
            case R.id.studentDetails:
                Intent intent7 = new Intent(MainActivity.this, SelectSectionStudent.class);
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


        FirebaseDatabase.getInstance().getReference().child("IIMTU").child("User").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    navUsername.setText(snapshot.child("userName").getValue().toString());
                    navUserMail.setText(snapshot.child("userEmail").getValue().toString());
                    String url = snapshot.child("userImage").getValue().toString();
                    Glide.with(getApplicationContext()).load(url).into(navUserPhot);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });



    }


}