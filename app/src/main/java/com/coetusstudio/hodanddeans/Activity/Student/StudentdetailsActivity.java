package com.coetusstudio.hodanddeans.Activity.Student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.coetusstudio.hodanddeans.Adapter.Student.StudentAdapter;
import com.coetusstudio.hodanddeans.Models.Students.StudentDetails;
import com.coetusstudio.hodanddeans.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class StudentdetailsActivity extends AppCompatActivity {

    RecyclerView recviewStudent;
    StudentAdapter studentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentdetails);

        setTitle("Enter Admission Number");

        recviewStudent=(RecyclerView)findViewById(R.id.rcstudents);
        recviewStudent.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<StudentDetails> options =
                new FirebaseRecyclerOptions.Builder<StudentDetails>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("IIMTU").child("Student"), StudentDetails.class)
                        .build();

        studentAdapter=new StudentAdapter(options);
        recviewStudent.setAdapter(studentAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        studentAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        studentAdapter.stopListening();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.searchmenu,menu);

        MenuItem item=menu.findItem(R.id.search);

        SearchView searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s) {

                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void processsearch(String s)
    {
        FirebaseRecyclerOptions<StudentDetails> options =
                new FirebaseRecyclerOptions.Builder<StudentDetails>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("IIMTU").child("Student").orderByChild("studentAdmissionNumber").startAt(s).endAt(s+"\uf8ff"), StudentDetails.class)
                        .build();

        studentAdapter=new StudentAdapter(options);
        studentAdapter.startListening();
        recviewStudent.setAdapter(studentAdapter);

    }



}