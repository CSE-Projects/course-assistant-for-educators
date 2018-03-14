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
 * Created by omkar on 14-Mar-18.
 */

public class CoursesAdapter extends ArrayAdapter<String[]> {

    public CoursesAdapter(Activity context, ArrayList<String[]> courseCodeList) {
        super(context, 0, courseCodeList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        if(view == null)
        {
            // inflate (create an object) a item from item_course and store in view
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_course, parent, false);
        }

        // getting item from array list
        String[] courseInfo = getItem(position);

        // get view from item_course to display course code and name
        TextView courseCodeText = view.findViewById(R.id.courseCodeText);
        TextView courseNameText = view.findViewById(R.id.courseNameText);
        // set text of view
        courseCodeText.setText(courseInfo[0]);
        courseNameText.setText(courseInfo[1]);
        // return item
        return view;
    }
}
