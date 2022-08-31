package com.coetusstudio.hodanddeans.Activity.Attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.coetusstudio.hodanddeans.databinding.ActivityViewSubjectAttendanceBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class View_Subject_Attendance extends AppCompatActivity {

    ActivityViewSubjectAttendanceBinding binding;
    String facultySubject, facultySection, attendanceDate;
    DatabaseReference dbAttendanceRecordRef;
    ArrayList attendance= new ArrayList<>();
    int count=0;
    ProgressDialog mDialog;
    int tpresent = 0;
    int tattencount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewSubjectAttendanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mDialog=new ProgressDialog(this);
        mDialog.setTitle("Loading...");
        mDialog.setMessage("Please wait...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

        attendance.clear();

        Intent intent=getIntent();
        facultySubject=intent.getStringExtra("facultySubject");
        facultySection=intent.getStringExtra("facultySection");
        //attendanceDate = intent.getStringExtra("attendanceDate");

        dbAttendanceRecordRef= FirebaseDatabase.getInstance().getReference().child("AttendenRecordSheet").child(facultySection).child(facultySubject);
        dbAttendanceRecordRef.keepSynced(true);

        binding.facultySubjectAttendance.setText(facultySubject);
        binding.facultySectionAttendance.setText(facultySection);
        //binding.attendanceDate.setText(attendanceDate);

        attendance.add("Student Roll Number"+"                   "+"Attendance");
        dbAttendanceRecordRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dspp :dataSnapshot.getChildren()){
                    String date,avalue,rollnumber;
                    date=dspp.getKey();
                    //rollnumber=dspp.child(date).getKey();
                    for (DataSnapshot dsp : dspp.child(date).getChildren()) {
                        rollnumber=dsp.getKey();
                        for (DataSnapshot dssp : dsp.child(rollnumber).getChildren()) {
                            String aval;
                            aval = dssp.child("atvalue").getValue(String.class);

                            try {
                                tpresent= tpresent+Integer.valueOf(aval.substring(0, 1));
                                tattencount += Integer.valueOf(aval.substring(2, 3));
                            }
                            catch (Exception e){}

                        }
                        int abs = tattencount - tpresent;
                        float percent;
                        percent = (((float) tpresent) / ((float) tattencount)) * 100;


                        String clslvt=String.valueOf(tpresent-(abs*3));
                        String attendence=(String.valueOf(tpresent)+"/"+String.valueOf(tattencount)) ;
                        int mpercentint=(int)Math.round(percent) ;
                        String mmper=String.valueOf(mpercentint)+"%";

                        attendance.add(rollnumber+"                                           "+mmper);

                        count=count+1;
                    }
                }
                attendance.add("Total Number Of Student=  "+count);
                listshow(attendance);//this is a function created by me
                mDialog.dismiss();
                count=0;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void listshow(ArrayList attendancelist) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, attendancelist);
        binding.listviewbydate.setAdapter(adapter);

        binding.listviewbydate.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String dataupatevar = parent.getItemAtPosition(position).toString();
                //updateattendence(dataupatevar);
                return false;
            }
        });
    }
}