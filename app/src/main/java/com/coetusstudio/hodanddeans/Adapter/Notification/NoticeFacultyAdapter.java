package com.coetusstudio.hodanddeans.Adapter.Notification;

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

import com.bumptech.glide.Glide;
import com.coetusstudio.hodanddeans.Models.Notification.NoticeData;
import com.coetusstudio.hodanddeans.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class NoticeFacultyAdapter extends FirebaseRecyclerAdapter<NoticeData,NoticeFacultyAdapter.myviewholder> {

    public NoticeFacultyAdapter(@NonNull FirebaseRecyclerOptions<NoticeData> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final NoticeFacultyAdapter.myviewholder holder, @SuppressLint("RecyclerView") final int position, @NonNull final NoticeData NoticeData)
    {


        holder.notificationTitle.setText(NoticeData.getTitle());
        holder.notificationDate.setText(NoticeData.getDate());
        holder.notificationTime.setText(NoticeData.getTime());
        Glide.with(holder.notificationImage.getContext()).load(NoticeData.getImage())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.noimage)
                .into(holder.notificationImage);

        holder.notificationDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder=new AlertDialog.Builder(holder.notificationImage.getContext());
                builder.setTitle("Warning");
                builder.setMessage("Are you sure want to delete...?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Notice").child("Only Faculty").child(getRef(position).getKey())
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
    public NoticeFacultyAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlenotice,parent,false);
        return new NoticeFacultyAdapter.myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView notificationTitle, notificationDate, notificationTime;
        ImageView notificationImage, notificationDelete;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            notificationTitle=itemView.findViewById(R.id.notificationTitle);
            notificationImage=itemView.findViewById(R.id.notificationImage);
            notificationDate=itemView.findViewById(R.id.notificationDate);
            notificationTime=itemView.findViewById(R.id.notificationTime);
            notificationDelete=itemView.findViewById(R.id.notificationDelete);

        }
    }
}
