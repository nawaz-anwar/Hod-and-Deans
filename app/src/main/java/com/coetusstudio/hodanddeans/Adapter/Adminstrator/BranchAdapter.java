package com.coetusstudio.hodanddeans.Adapter.Adminstrator;

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
import com.coetusstudio.hodanddeans.Adapter.Student.StudentAdapter;
import com.coetusstudio.hodanddeans.Models.Administrator.Branch;
import com.coetusstudio.hodanddeans.Models.Students.StudentDetails;
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

public class BranchAdapter extends FirebaseRecyclerAdapter<Branch,BranchAdapter.myviewholder> {

    public BranchAdapter(@NonNull FirebaseRecyclerOptions<Branch> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final BranchAdapter.myviewholder holder, @SuppressLint("RecyclerView") final int position, @NonNull final Branch Branch)
    {


        holder.name.setText(Branch.getBranch());


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Warning");
                builder.setMessage("Are you sure want to delete Student Data...?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Branch").child(getRef(position).getKey())
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
    public BranchAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowbranch,parent,false);
        return new BranchAdapter.myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView delete;
        TextView name;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            name=itemView.findViewById(R.id.branchName);

            delete=(ImageView)itemView.findViewById(R.id.branchDelete);
        }
    }
}
