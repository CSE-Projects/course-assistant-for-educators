package com.example.omkar.android.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.omkar.android.R;
import com.example.omkar.android.fragments.AddAttendanceFragment;

import java.util.ArrayList;

/**
 * Created by omkar on 15-Mar-18.
 */

public class AddAttendanceAdapter extends ArrayAdapter<AddAttendanceFragment.StudentAttendance> {

    /** Holds child views for one row. */
    private static class StudentAttendanceViewHolder {
        private CheckBox checkBox;
        private TextView textView;
    }

    public AddAttendanceAdapter(Activity context, ArrayList<AddAttendanceFragment.StudentAttendance> studentAttendancesList) {
        super(context, 0, studentAttendancesList);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // get view obj
        View view = convertView;
        // holder for the view
        StudentAttendanceViewHolder holder = null;

        // view doesn't exist
        if (view == null) {
            // inflate (create an object) a item from item_course and store in view
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_add_attendance, parent, false);

            // create new holder for the view
            holder = new StudentAttendanceViewHolder();

            // holder view elements
            holder.checkBox = view.findViewById(R.id.check);
            holder.textView = view.findViewById(R.id.studentIdText);
            // tag view with the holder
            view.setTag(holder);

            // If CheckBox is toggled, update the studentAttendance obj it is tagged with.
            holder.checkBox.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {   // get the checkbox
                    CheckBox cb = (CheckBox) v;
                    // get the student Attendance obj associated with that checkbox
                    AddAttendanceFragment.StudentAttendance studentAttendance = (AddAttendanceFragment.StudentAttendance) cb.getTag();
                    // set the student selection if check box is selected
                    studentAttendance.setChecked(cb.isChecked());
                }
            });
        }
        // view exists
        else {
            // get holder from the view through the tag
            holder = (StudentAttendanceViewHolder) view.getTag();
        }

        // getting item from array list
        AddAttendanceFragment.StudentAttendance studentAttendance = getItem(position);

        // set attributes
        holder.textView.setText(String.valueOf(studentAttendance.getId()));
        holder.checkBox.setChecked(studentAttendance.isChecked());
        holder.checkBox.setTag(studentAttendance);

        // return item
        return view;
    }
}
