package com.coetusstudio.hodanddeans.Adapter.Faculty;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.coetusstudio.hodanddeans.Models.Faculty.AddFaculty;
import com.coetusstudio.hodanddeans.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class FacultyAdapter extends FirebaseRecyclerAdapter<AddFaculty,FacultyAdapter.myviewholder>{

    public FacultyAdapter(@NonNull FirebaseRecyclerOptions<AddFaculty> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final FacultyAdapter.myviewholder holder, @SuppressLint("RecyclerView") final int position, @NonNull final AddFaculty AddFaculty)
    {


        holder.name.setText(AddFaculty.getFacultyName());
        holder.email.setText(AddFaculty.getFacultyEmail());
        holder.id.setText(AddFaculty.getFacultyId());
        holder.subject.setText(AddFaculty.getFacultySubject());
        holder.subjectCode.setText(AddFaculty.getFacultySubjectCode());
        Glide.with(holder.img.getContext()).load(AddFaculty.getFacultyImage())
                .placeholder(R.drawable.manimg)
                .circleCrop()
                .error(R.drawable.manimg)
                .into(holder.img);


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus= DialogPlus.newDialog(holder.name.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogfaculty))
                        .setExpanded(true,1500)
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
                name.setText(AddFaculty.getFacultyName());
                email.setText(AddFaculty.getFacultyEmail());
                id.setText(AddFaculty.getFacultyId());
                subject.setText(AddFaculty.getFacultySubject());
                subject.setText(AddFaculty.getFacultySubjectCode());
                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("facultyImage",imageUrl.getText().toString());
                        map.put("facultyName",name.getText().toString());
                        map.put("facultyEmail",email.getText().toString());
                        map.put("facultyId",id.getText().toString());
                        map.put("facultySubject",subject.getText().toString());
                        map.put("facultySubjectCode",subjectCode.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("IIMTU").child("Faculty").child(getRef(position).getKey())
                                .updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(holder.img.getContext(), "Success", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.img.getContext(), "Error", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });


            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Warning");
                builder.setMessage("Are you sure want to delete Faculty Data...?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("IIMTU").child("Faculty").child(getRef(position).getKey())
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

    } // End of OnBindViewMethod

    @NonNull
    @Override
    public FacultyAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowfaculty,parent,false);
        return new FacultyAdapter.myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        CircleImageView img;
        ImageView edit,delete;
        TextView name,email,id,subject,subjectCode,admission,enrollment,roll,fees,semester,grade,attendance;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            img=itemView.findViewById(R.id.facultyRcImage);
            name=itemView.findViewById(R.id.facultyRcName);
            email=itemView.findViewById(R.id.facultyRcEmail);
            id=itemView.findViewById(R.id.facultyRcId);
            subject=itemView.findViewById(R.id.facultyRcSubjectName);
            subjectCode=itemView.findViewById(R.id.facultyRcSubjectCode);

            edit=(ImageView)itemView.findViewById(R.id.facultyRcEdit);
            delete=(ImageView)itemView.findViewById(R.id.facultyRcDelete);

        }
    }

}
