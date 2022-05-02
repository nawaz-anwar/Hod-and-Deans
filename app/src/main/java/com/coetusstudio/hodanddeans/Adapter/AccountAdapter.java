package com.coetusstudio.hodanddeans.Adapter;

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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.coetusstudio.hodanddeans.Models.Accountdata;
import com.coetusstudio.hodanddeans.Models.AddFaculty;
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

public class AccountAdapter extends FirebaseRecyclerAdapter<Accountdata,AccountAdapter.myviewholder>{


    public AccountAdapter(@NonNull FirebaseRecyclerOptions<Accountdata> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final AccountAdapter.myviewholder holder, @SuppressLint("RecyclerView") final int position, @NonNull final Accountdata Accountdata)
    {

        holder.name.setText(Accountdata.getNameAccount());
        holder.email.setText(Accountdata.getEmailAccount());
        holder.id.setText(Accountdata.getIdAccount());
        holder.position.setText(Accountdata.getPositionAccount());
        Glide.with(holder.img.getContext()).load(Accountdata.getImageAccount())
            .placeholder(R.drawable.manimg)
            .circleCrop()
            .error(R.drawable.manimg)
            .into(holder.img);

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus= DialogPlus.newDialog(holder.img.getContext())
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

                imageUrl.setText(Accountdata.getImageAccount());
                name.setText(Accountdata.getNameAccount());
                email.setText(Accountdata.getEmailAccount());
                id.setText(Accountdata.getIdAccount());
                subject.setText(Accountdata.getPositionAccount());
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

                        FirebaseDatabase.getInstance().getReference().child("IIMTU").child("Account Data")
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


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.img.getContext());
                builder.setTitle("Warning");
                builder.setMessage("Are you sure want to delete Account Faculty...?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("IIMTU").child("Account Data")
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
    public AccountAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowaccount,parent,false);
        return new AccountAdapter.myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        CircleImageView img;
        ImageView edit,delete;
        TextView name,course,email,id,position;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            img=itemView.findViewById(R.id.imgRcAccount);
            name=itemView.findViewById(R.id.nameRcAccount);
            email=itemView.findViewById(R.id.emailRcAccount);
            id=itemView.findViewById(R.id.idRcAccount);
            position=itemView.findViewById(R.id.positionRcAccount);

            edit=(ImageView)itemView.findViewById(R.id.editRcAccount);
            delete=(ImageView)itemView.findViewById(R.id.deleteRcAccount);
        }
    }
}
