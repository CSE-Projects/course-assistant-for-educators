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

import java.util.List;

/**
 * Created by omkar on 14-Mar-18.
 */

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CoursesViewHolder> {

    // TODO: Add colors to course codes
//    private int[] colors ;
//    private int positionColors;

    private List<String[]> courseCodeList;
    private Context mContext;

    public CoursesAdapter(Context context, List<String[]> courseCodeList) {
        mContext = context;
        this.courseCodeList = courseCodeList;
//      colors = new int[] {R.color.brown, R.color.pink, R.color.blue, R.color.green, R.color.orange, R.color.purple};
//      positionColors = 0;
    }

    @Override
    public CoursesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        return new CoursesViewHolder(view, mContext, courseCodeList);
    }

    @Override
    public void onBindViewHolder(CoursesViewHolder holder, int position) {
        // getting item from array list
        String[] courseInfo = courseCodeList.get(position);

        // set text of view
        holder.courseCodeText.setText(courseInfo[0]);
        holder.courseNameText.setText(courseInfo[1]);

    }

    @Override
    public int getItemCount() {
        return courseCodeList.size();
    }

    static class CoursesViewHolder extends RecyclerView.ViewHolder {

        TextView courseCodeText;
        TextView courseNameText;

        CoursesViewHolder(View itemView, final Context context, final List<String[]> courseCodeList) {
            super(itemView);
            // get view from item_course to display course code and name
            courseCodeText = itemView.findViewById(R.id.courseCodeText);
            courseNameText = itemView.findViewById(R.id.courseNameText);

            // set an OnClickListener on the list item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // set intent to Course Activity
                    Intent courseIntent = new Intent(context, CourseActivity.class);
                    // get course code of item clicked
                    String[] courseInfo = courseCodeList.get(getAdapterPosition());

                    // send course with intent
                    courseIntent.putExtra("courseCode", courseInfo[0]);
                    courseIntent.putExtra("courseName", courseInfo[1]);
                    courseIntent.putExtra("emailCr", courseInfo[2]);
                    courseIntent.putExtra("emailTa", courseInfo[3]);
                    context.startActivity(courseIntent);
                }
            });
        }
    }
}
