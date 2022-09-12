package com.coetusstudio.hodanddeans.Adapter.Adminstrator;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coetusstudio.hodanddeans.Models.Administrator.Section;
import com.coetusstudio.hodanddeans.Models.Administrator.Subject;
import com.coetusstudio.hodanddeans.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class SubjectAdapter extends FirebaseRecyclerAdapter<Subject,SubjectAdapter.myviewholder> {


    public SubjectAdapter(@NonNull FirebaseRecyclerOptions<Subject> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final SubjectAdapter.myviewholder holder, @SuppressLint("RecyclerView") final int position, @NonNull final Subject Subject)
    {


        holder.subjectName.setText(Subject.getSubject());
        holder.subjectCode.setText(Subject.getSubjectCode());


        holder.subjectDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.subjectName.getContext());
                builder.setTitle("Warning");
                builder.setMessage("Are you sure want to delete Student Data...?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Subject").child(getRef(position).getKey())
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
    public SubjectAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowsubject,parent,false);
        return new SubjectAdapter.myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView subjectDelete;
        TextView subjectName, subjectCode;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            subjectName=itemView.findViewById(R.id.subjectName);
            subjectCode=itemView.findViewById(R.id.subjectCode);

            subjectDelete=(ImageView)itemView.findViewById(R.id.subjectDelete);
        }
    }
}
