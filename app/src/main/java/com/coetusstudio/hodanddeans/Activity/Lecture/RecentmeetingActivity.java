package com.coetusstudio.hodanddeans.Activity.Lecture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.coetusstudio.hodanddeans.Adapter.Lecture.MeetingAdapter;
import com.coetusstudio.hodanddeans.Models.Lecture.Lecture;
import com.coetusstudio.hodanddeans.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class RecentmeetingActivity extends AppCompatActivity {

    RecyclerView recviewLecture;
    MeetingAdapter lectureAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recentmeeting);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RecentmeetingActivity.this);
        recviewLecture=(RecyclerView)findViewById(R.id.rcLecture);
        recviewLecture.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        FirebaseRecyclerOptions<Lecture> options =
                new FirebaseRecyclerOptions.Builder<Lecture>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Lecture"), Lecture.class)
                        .build();

        lectureAdapter=new MeetingAdapter(options);
        recviewLecture.setAdapter(lectureAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        lectureAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        lectureAdapter.stopListening();
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
        FirebaseRecyclerOptions<Lecture> options =
                new FirebaseRecyclerOptions.Builder<Lecture>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Lecture").orderByChild("lectureName").startAt(s).endAt(s+"\uf8ff"), Lecture.class)
                        .build();

        lectureAdapter=new MeetingAdapter(options);
        lectureAdapter.startListening();
        recviewLecture.setAdapter(lectureAdapter);

    }
}