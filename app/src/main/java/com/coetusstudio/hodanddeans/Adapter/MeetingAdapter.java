package com.coetusstudio.hodanddeans.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coetusstudio.hodanddeans.Models.Lecture;
import com.coetusstudio.hodanddeans.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MeetingAdapter extends FirebaseRecyclerAdapter<Lecture,MeetingAdapter.myviewholder> {

    public MeetingAdapter(@NonNull FirebaseRecyclerOptions<Lecture> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final MeetingAdapter.myviewholder holder, @SuppressLint("RecyclerView") final int position, @NonNull final Lecture Lecture)
    {


        holder.lectureName.setText(Lecture.getLectureName());
        holder.lectureTiming.setText(Lecture.getLectureTiming());
        holder.lectureLink.setText(Lecture.getLectureLink());
        holder.lectureDate.setText(Lecture.getLectureDate());
        holder.lectureTime.setText(Lecture.getLectureTime());




    } // End of OnBindViewMethod

    @NonNull
    @Override
    public MeetingAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlemeeting,parent,false);
        return new MeetingAdapter.myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView lectureName, lectureTiming, lectureLink, lectureDate, lectureTime;
        Button btnLectureJoin;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            lectureName=itemView.findViewById(R.id.lectureName);
            lectureTiming=itemView.findViewById(R.id.lectureTiming);
            lectureLink=itemView.findViewById(R.id.lectureLink);
            lectureDate=itemView.findViewById(R.id.lectureDate);
            lectureTime=itemView.findViewById(R.id.lectureTime);
            btnLectureJoin=itemView.findViewById(R.id.btnLectureJoin);

        }
    }
}
