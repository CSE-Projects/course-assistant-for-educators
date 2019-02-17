package com.example.omkar.android.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.omkar.android.R;
import com.example.omkar.android.fragments.AddAttendanceFragment;
import com.example.omkar.android.fragments.AddAttendanceFragment.StudentAttendance;

import java.util.List;

/**
 * Created by omkar on 15-Mar-18.
 */

public class AddAttendanceAdapter extends RecyclerView.Adapter<AddAttendanceAdapter.StudentAttendanceViewHolder> {

    private List<StudentAttendance> studentAttendancelist;

    public AddAttendanceAdapter(List<StudentAttendance> studentAttendancelist) {
        this.studentAttendancelist = studentAttendancelist;
    }

    @Override
    public StudentAttendanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_attendance, parent, false);
        return new StudentAttendanceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StudentAttendanceViewHolder holder, int position) {
        // getting item from array list
        AddAttendanceFragment.StudentAttendance studentAttendance = studentAttendancelist.get(position);

        // set attributes
        holder.textView.setText(String.valueOf(studentAttendance.getId()));
        holder.checkBox.setChecked(studentAttendance.isChecked());
        holder.checkBox.setTag(studentAttendance);
    }

    @Override
    public int getItemCount() {
        return studentAttendancelist.size();
    }

    /**
     * Holds child views for one row.
     */
    static class StudentAttendanceViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private TextView textView;

        StudentAttendanceViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.check);
            textView = itemView.findViewById(R.id.studentIdText);

            // If CheckBox is toggled, update the studentAttendance obj it is tagged with.
            checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {   // get the checkbox
                    CheckBox cb = (CheckBox) v;
                    // get the student Attendance obj associated with that checkbox
                    AddAttendanceFragment.StudentAttendance studentAttendance = (AddAttendanceFragment.StudentAttendance) cb.getTag();
                    // set the student selection if check box is selected
                    studentAttendance.setChecked(cb.isChecked());
                }
            });
        }
    }

}
