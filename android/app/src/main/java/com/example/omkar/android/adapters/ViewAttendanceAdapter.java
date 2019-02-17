package com.example.omkar.android.adapters;

import android.app.Activity;
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
 * Created by omkar on 23-Mar-18.
 */

public class ViewAttendanceAdapter extends RecyclerView.Adapter<ViewAttendanceAdapter.ViewAttendanceViewHolder> {
    private List<String[]> attendancesList;
    private Context context;

    public ViewAttendanceAdapter(Context context, ArrayList<String[]> attendancesList) {
        this.attendancesList = attendancesList;
        this.context = context;
    }

    @Override
    public ViewAttendanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_attendance, parent, false);
        return new ViewAttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewAttendanceViewHolder holder, int position) {
        // get item
        String[] attendanceList = attendancesList.get(position);

        // set view item values
        try {
            // set text and attendance status
            holder.studentId.setText(attendanceList[0]);
            holder.attendanceStatus.setText(attendanceList[1]);
            if (attendanceList[1].equals("P")) {
                holder.attendanceStatus.setTextColor(context.getResources().getColor(R.color.green));
            } else {
                holder.attendanceStatus.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            }
        } catch (Exception e) {
            Toast.makeText(context, "No entry found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return attendancesList.size();
    }

    static class ViewAttendanceViewHolder extends RecyclerView.ViewHolder {
        TextView studentId;
        TextView attendanceStatus;

        ViewAttendanceViewHolder(View itemView) {
            super(itemView);
            // get view elements
            studentId = itemView.findViewById(R.id.studentId);
            attendanceStatus = itemView.findViewById(R.id.attendanceStatus);
        }
    }
}
