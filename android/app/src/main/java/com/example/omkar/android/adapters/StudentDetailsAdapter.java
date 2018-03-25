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
 * Created by omkar on 24-Mar-18.
 */

public class StudentDetailsAdapter extends ArrayAdapter<String[]> {

    public StudentDetailsAdapter(Activity context, ArrayList<String[]> studentDetailsList) {
        super(context, 0, studentDetailsList);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // get view obj
        View view = convertView;

        if (view == null) {
            // inflate (create an object) a item from item_student_details and store in view
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_student_details, parent, false);
        }

        // get item
        String[] studentDetails = getItem(position);

        // get view elements
        TextView studentId = view.findViewById(R.id.studentId);
        TextView insem = view.findViewById(R.id.insem);
        TextView endsem = view.findViewById(R.id.endsem);
        TextView attendance = view.findViewById(R.id.attendance);

        // set view item values
        try {
            studentId.setText(studentDetails[0]);
            insem.setText(studentDetails[1]);
            endsem.setText(studentDetails[2]);
            attendance.setText(studentDetails[3]);
        }
        catch (Exception e){
            Toast.makeText(getContext(), "No entry found", Toast.LENGTH_SHORT).show();
        }

        // return item
        return view;
    }
}
