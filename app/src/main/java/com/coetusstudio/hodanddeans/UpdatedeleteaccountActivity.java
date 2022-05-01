package com.coetusstudio.hodanddeans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.coetusstudio.hodanddeans.Adapter.AccountAdapter;
import com.coetusstudio.hodanddeans.Adapter.FacultyAdapter;
import com.coetusstudio.hodanddeans.Models.Accountdata;
import com.coetusstudio.hodanddeans.Models.AddFaculty;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class UpdatedeleteaccountActivity extends AppCompatActivity {


    RecyclerView recviewAccount;
    AccountAdapter adapterAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatedeleteaccount);

        setTitle("Search here..");

        recviewAccount=(RecyclerView)findViewById(R.id.recviewAccount);
        recviewAccount.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Accountdata> options =
                new FirebaseRecyclerOptions.Builder<Accountdata>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Account Data"), Accountdata.class)
                        .build();

        adapterAccount=new AccountAdapter(options);
        recviewAccount.setAdapter(adapterAccount);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterAccount.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterAccount.stopListening();
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
        FirebaseRecyclerOptions<Accountdata> options =
                new FirebaseRecyclerOptions.Builder<Accountdata>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("IIMTU").orderByChild("Account Data").startAt(s).endAt(s+"\uf8ff"), Accountdata.class)
                        .build();

        adapterAccount=new AccountAdapter(options);
        adapterAccount.startListening();
        recviewAccount.setAdapter(adapterAccount);

    }
}