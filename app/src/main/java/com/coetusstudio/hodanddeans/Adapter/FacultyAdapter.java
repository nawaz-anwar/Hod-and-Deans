package com.coetusstudio.hodanddeans.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.coetusstudio.hodanddeans.Models.AddFaculty;
import com.coetusstudio.hodanddeans.R;
import com.coetusstudio.hodanddeans.databinding.SinglerowfacultyBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class FacultyAdapter extends RecyclerView.Adapter<FacultyAdapter.FacultyViewHoilder>{


    Context context;
    ArrayList<AddFaculty> addFacultys;

    public FacultyAdapter(Context context, ArrayList<AddFaculty> addFaculty){
        this.context = context;
        this.addFacultys = addFacultys;
    }




    @NonNull
    @Override
    public FacultyViewHoilder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.singlerowfaculty,parent,false);
        return new FacultyViewHoilder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacultyViewHoilder holder, int position) {
        AddFaculty addFaculty = addFacultys.get(position);

        holder.binding.nameText.setText(addFaculty.getFacultyName());
    }

    @Override
    public int getItemCount() {
        return this.addFacultys.size();
    }

    public class FacultyViewHoilder extends RecyclerView.ViewHolder{

        SinglerowfacultyBinding binding;

        public FacultyViewHoilder(@NonNull View itemView) {
            super(itemView);
            binding = SinglerowfacultyBinding.bind(itemView);
        }
    }
}
