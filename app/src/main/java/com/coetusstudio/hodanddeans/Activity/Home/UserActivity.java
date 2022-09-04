package com.coetusstudio.hodanddeans.Activity.Home;

import static android.view.View.INVISIBLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.coetusstudio.hodanddeans.Models.Admin.User;
import com.coetusstudio.hodanddeans.R;
import com.coetusstudio.hodanddeans.databinding.ActivityUserBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends AppCompatActivity {

    ActivityUserBinding binding;
    FirebaseAuth auth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnFacultyBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserActivity.this.finish();
            }
        });

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("IIMTU").child("User").child(auth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User user = snapshot.getValue(User.class);

                binding.userName.setText(user.getUserName());
                binding.userEmail.setText(user.getUserEmail());
                binding.userID.setText(user.getUserID());
                binding.userPosition.setText(user.getUserPosition());
                binding.userSchool.setText(user.getUserSchool());
                String password = user.getUserPassword();

                String url = snapshot.child("userImage").getValue().toString();
                Glide.with(getApplicationContext()).load(url).error(R.drawable.manimg).into(binding.userImage);

                binding.userPasswordVisibility.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        binding.userPassword.setText(password);
                        binding.userPasswordVisibility.setVisibility(INVISIBLE);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}