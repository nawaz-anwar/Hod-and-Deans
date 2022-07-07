package com.coetusstudio.hodanddeans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.coetusstudio.hodanddeans.Adapter.NoticeAdapter;
import com.coetusstudio.hodanddeans.Models.NoticeData;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class RecentnoticeActivity extends AppCompatActivity {

    RecyclerView recviewNotice;
    NoticeAdapter noticeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recentnotice);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RecentnoticeActivity.this);
        recviewNotice=(RecyclerView)findViewById(R.id.rcNotice);
        recviewNotice.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        FirebaseRecyclerOptions<NoticeData> options =
                new FirebaseRecyclerOptions.Builder<NoticeData>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Notice"), NoticeData.class)
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