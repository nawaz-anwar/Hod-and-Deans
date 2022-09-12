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
import com.coetusstudio.hodanddeans.Models.Administrator.Semester;
import com.coetusstudio.hodanddeans.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class SectionAdapter extends FirebaseRecyclerAdapter<Section,SectionAdapter.myviewholder> {

    public SectionAdapter(@NonNull FirebaseRecyclerOptions<Section> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final SectionAdapter.myviewholder holder, @SuppressLint("RecyclerView") final int position, @NonNull final Section Section)
    {


        holder.name.setText(Section.getSection());


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Warning");
                builder.setMessage("Are you sure want to delete Student Data...?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Section").child(getRef(position).getKey())
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
    public SectionAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowsection,parent,false);
        return new SectionAdapter.myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView delete;
        TextView name;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            name=itemView.findViewById(R.id.sectionName);

            delete=(ImageView)itemView.findViewById(R.id.sectionDelete);
        }
    }
}
