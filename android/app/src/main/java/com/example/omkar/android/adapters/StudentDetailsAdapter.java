package com.example.omkar.android.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omkar.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omkar on 24-Mar-18.
 */

public class StudentDetailsAdapter extends RecyclerView.Adapter<StudentDetailsAdapter.StudentDetailsViewHolder> {
    private List<String[]> studentDetailsList;
    private Context context;

    public StudentDetailsAdapter(Context context, ArrayList<String[]> studentDetailsList) {
        this.context = context;
        this.studentDetailsList = studentDetailsList;
    }

    @Override
    public StudentDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student_details, parent, false);
        return new StudentDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentDetailsViewHolder holder, int position) {
        // get item
        String[] studentDetails = studentDetailsList.get(position);
        // set view item values
        try {
            holder.studentId.setText(studentDetails[0]);
            holder.insem.setText(studentDetails[1]);
            holder.endsem.setText(studentDetails[2]);
            holder.attendance.setText(studentDetails[3]);
        } catch (Exception e) {
            Toast.makeText(context, "No entry found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return studentDetailsList.size();
    }

    static class StudentDetailsViewHolder extends RecyclerView.ViewHolder {

        TextView studentId;
        TextView insem;
        TextView endsem;
        TextView attendance;

        public StudentDetailsViewHolder(View itemView) {
            super(itemView);

            // get view elements
            studentId = itemView.findViewById(R.id.studentId);
            insem = itemView.findViewById(R.id.insem);
            endsem = itemView.findViewById(R.id.endsem);
            attendance = itemView.findViewById(R.id.attendance);
        }
    }
}
