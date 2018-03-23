package com.example.omkar.android.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omkar.android.R;

import java.util.ArrayList;

/**
 * Created by omkar on 23-Mar-18.
 */

public class ViewAttendanceAdapter extends ArrayAdapter<String[]>{

    public ViewAttendanceAdapter(Activity context, ArrayList<String[]> attendancesList) {
        super(context, 0, attendancesList);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // get view obj
        View view = convertView;

        if (view == null) {
            // inflate (create an object) a item from item_course and store in view
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_attendance, parent, false);
        }

        // get item
        String[] attendanceList = getItem(position);

        // get view elements
        TextView studentId = view.findViewById(R.id.studentId);
        TextView attendanceStatus = view.findViewById(R.id.attendanceStatus);

        // set view item values
        try {
            // set text and attendance status
            studentId.setText(attendanceList[0]);
            attendanceStatus.setText(attendanceList[1]);
            if (attendanceList[1].equals("P")) {
                attendanceStatus.setTextColor(getContext().getResources().getColor(R.color.green));
            } else {
                attendanceStatus.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
            }
        }
        catch (Exception e){
            Toast.makeText(getContext(), "No entry found", Toast.LENGTH_SHORT).show();
        }

        // return item
        return view;
    }
}
