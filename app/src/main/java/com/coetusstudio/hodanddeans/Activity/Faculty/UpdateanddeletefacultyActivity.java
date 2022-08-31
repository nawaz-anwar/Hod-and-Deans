package com.coetusstudio.hodanddeans.Activity.Faculty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.coetusstudio.hodanddeans.Adapter.Faculty.FacultyAdapter;
import com.coetusstudio.hodanddeans.Models.Faculty.AddFaculty;
import com.coetusstudio.hodanddeans.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateanddeletefacultyActivity extends AppCompatActivity {



    RecyclerView recviewFaculty;
    FacultyAdapter facultyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateanddeletefaculty);
        setTitle("Enter ID");



        recviewFaculty=(RecyclerView)findViewById(R.id.rcFactulty);
        recviewFaculty.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<AddFaculty> options =
                new FirebaseRecyclerOptions.Builder<AddFaculty>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("IIMTU").child("Faculty"), AddFaculty.class)
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


    
}