package com.coetusstudio.hodanddeans.Activity.Notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.coetusstudio.hodanddeans.Adapter.Notification.NoticeAdapter;
import com.coetusstudio.hodanddeans.Models.Notification.NoticeData;
import com.coetusstudio.hodanddeans.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class RecentnoticeActivity extends AppCompatActivity {

    RecyclerView recviewNotice;
    NoticeAdapter noticeAdapter;
    String semester, section;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recentnotice);

        Intent intent = getIntent();
        section = intent.getStringExtra("section");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RecentnoticeActivity.this);
        recviewNotice=(RecyclerView)findViewById(R.id.rcNotice);
        recviewNotice.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        FirebaseRecyclerOptions<NoticeData> options =
                new FirebaseRecyclerOptions.Builder<NoticeData>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Notice").child(section).child("All Faculty And Students"), NoticeData.class)
                        .build();

        noticeAdapter=new NoticeAdapter(options);
        recviewNotice.setAdapter(noticeAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        noticeAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        noticeAdapter.stopListening();
    }
}