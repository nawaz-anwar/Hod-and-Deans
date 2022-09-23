package com.coetusstudio.hodanddeans.Activity.Faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.coetusstudio.hodanddeans.Adapter.Faculty.FacultyAdapter;
import com.coetusstudio.hodanddeans.Adapter.Student.StudentAdapter;
import com.coetusstudio.hodanddeans.Models.Faculty.AddFaculty;
import com.coetusstudio.hodanddeans.Models.Students.StudentDetails;
import com.coetusstudio.hodanddeans.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpdateanddeletefacultyActivity extends AppCompatActivity {

    RecyclerView recviewFaculty;
    FacultyAdapter facultyAdapter;
    ArrayList<AddFaculty> list;
    String section;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateanddeletefaculty);
        setTitle("Enter ID");

        Intent intent = getIntent();
        section = intent.getStringExtra("section");

        recviewFaculty=(RecyclerView)findViewById(R.id.rcFactulty);
        recviewFaculty.setHasFixedSize(true);
        recviewFaculty.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<AddFaculty> options =
                new FirebaseRecyclerOptions.Builder<AddFaculty>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Faculty Data").child(section), AddFaculty.class)
                        .build();
        facultyAdapter=new FacultyAdapter(options);
        recviewFaculty.setAdapter(facultyAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        facultyAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        facultyAdapter.stopListening();
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
        FirebaseRecyclerOptions<AddFaculty> options =
                new FirebaseRecyclerOptions.Builder<AddFaculty>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("IIMTU").child("Faculty").orderByChild("facultyId").startAt(s).endAt(s+"\uf8ff"), AddFaculty.class)
                        .build();

        facultyAdapter=new FacultyAdapter(options);
        facultyAdapter.startListening();
        recviewFaculty.setAdapter(facultyAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}