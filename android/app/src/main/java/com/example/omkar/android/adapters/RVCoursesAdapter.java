package com.example.omkar.android.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.omkar.android.CourseActivity;
import com.example.omkar.android.R;

import java.util.ArrayList;

/**
 * Created by ruderbytes on 10/02/18.
 */

public class RVCoursesAdapter extends RecyclerView.Adapter<RVCoursesAdapter.ViewHolder> {

    private ArrayList<String[]> courses;
    private Context context;

    public RVCoursesAdapter(ArrayList<String[]> courses, Context context) {
        this.courses = courses;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()) .inflate(R.layout.item_course, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        // get course code of item clicked
        final String[] courseInfo = courses.get(position);

        // set text of view
        viewHolder.courseCodeText.setText(courseInfo[0]);
        viewHolder.courseNameText.setText(courseInfo[1]);

        // listener for click events on list view
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set intent to Course Activity
                Intent courseIntent = new Intent(context, CourseActivity.class);

                // add flag activity new task
                courseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                // send course with intent
                courseIntent.putExtra("courseCode", courseInfo[0]);
                courseIntent.putExtra("courseName", courseInfo[1]);
                courseIntent.putExtra("emailCr", courseInfo[2]);
                courseIntent.putExtra("emailTa", courseInfo[3]);
                context.startActivity(courseIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView courseCodeText, courseNameText;

        public ViewHolder(View view) {
            super(view);

            // get view from item_course to display course code and name
            courseCodeText = view.findViewById(R.id.courseCodeText);
            courseNameText = view.findViewById(R.id.courseNameText);

        }
    }

}
