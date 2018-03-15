package com.example.omkar.android.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.omkar.android.R;

import java.util.ArrayList;

/**
 * Created by omkar on 15-Mar-18.
 */

public class AddAttendanceAdapter extends ArrayAdapter<Integer> {

    public AddAttendanceAdapter(Activity context, ArrayList<Integer> studentIdList) {
        super(context, 0, studentIdList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            // inflate (create an object) a item from item_course and store in view
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_course, parent, false);
        }

        // getting item from array list
        Integer studentId = getItem(position);

        // get view from item_course to display course code and name
        TextView studentIdText = view.findViewById(R.id.studentIdText);
        // parse student id and display
        studentIdText.setText(String.valueOf(studentId));

        // return item
        return view;
    }
}
