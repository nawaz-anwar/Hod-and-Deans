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
import com.coetusstudio.hodanddeans.Models.Accountdata;
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

    FirebaseAuth auth;
    Context context;
    ArrayList<AddFaculty> facultyLists;

    public FacultyAdapter(Context context, ArrayList<AddFaculty> addFaculty){
        this.context = context;
        this.facultyLists = addFaculty;
    }

    @NonNull
    @Override
    public FacultyViewHoilder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.singlerowfaculty,parent,false);
        return new FacultyViewHoilder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacultyViewHoilder holder, int position) {
        AddFaculty addFaculty = facultyLists.get(position);
        auth = FirebaseAuth.getInstance();

        holder.binding.nameText.setText(addFaculty.getFacultyName());
        holder.binding.emailText.setText(addFaculty.getFacultyEmail());
        holder.binding.idText.setText(addFaculty.getFacultyId());
        holder.binding.subjectText.setText(addFaculty.getFacultySubject());
        holder.binding.subjectCodeText.setText(addFaculty.getFacultySubjectCode());
        Glide.with(holder.binding.imgText.getContext()).load(AddFaculty.getFacultyImage())
                .placeholder(R.drawable.manimg)
                .circleCrop()
                .error(R.drawable.manimg)
                .into(holder.binding.imgText);


        holder.binding.editicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus= DialogPlus.newDialog(holder.binding.editicon.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogfaculty))
                        .setExpanded(true,1400)
                        .create();

                View myview=dialogPlus.getHolderView();
                final EditText imageUrl=myview.findViewById(R.id.updImageFaculty);
                final EditText name=myview.findViewById(R.id.updNameFaculty);
                final EditText email=myview.findViewById(R.id.updEmailFaculty);
                final EditText id=myview.findViewById(R.id.updIdFaculty);
                final EditText subject=myview.findViewById(R.id.updSubjectFaculty);
                final EditText subjectCode=myview.findViewById(R.id.updSubjectCodeFaculty);
                Button submit=myview.findViewById(R.id.updBtnFaculty);

                imageUrl.setText(AddFaculty.getFacultyImage());

                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("FacultyImage",imageUrl.getText().toString());
                        map.put("FacultyName",name.getText().toString());
                        map.put("FacultyEmail",email.getText().toString());
                        map.put("FacultyId",id.getText().toString());
                        map.put("FacultySubject",subject.getText().toString());
                        map.put("FacultySubjectCode",subjectCode.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("IIMTU").child("Faculty").child(auth.getUid())
                                .updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });


            }
        });

        holder.binding.deleteicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.binding.imgText.getContext());
                builder.setTitle("Warning");
                builder.setMessage("Are you sure want to delete Faculty...?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("IIMTU").child("Faculty").child(auth.getUid())
                                .removeValue();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return facultyLists.size();
    }

    public class FacultyViewHoilder extends RecyclerView.ViewHolder{

        SinglerowfacultyBinding binding;

        public FacultyViewHoilder(@NonNull View itemView) {
            super(itemView);
            binding = SinglerowfacultyBinding.bind(itemView);
        }
    }
}
