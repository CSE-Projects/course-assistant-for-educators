package com.example.omkar.android.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.omkar.android.CourseActivity;
import com.example.omkar.android.R;
import com.example.omkar.android.adapters.AddAttendanceAdapter;
import com.example.omkar.android.helpers.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by omkar on 15-Mar-18.
 */

public class AddAttendanceFragment extends Fragment{

    // view of fragment
    View view;
    private AddAttendanceAdapter mAddAttendanceAdapter;
    private ArrayList<Integer[]> mStudentList;
    private DatabaseHelper mDatabaseHelper;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // configure Toolbar in Course Activity
        setHasOptionsMenu(true);
        ((CourseActivity)getActivity()).initToolbar("Add Attendance");
        ((CourseActivity)getActivity()).setViewHidden(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_add_attendance, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fillStudentList();
        initList();
    }


    /**
     * Initialize add attendance list view
     */
    private void initList() {
        // context of activity
        mAddAttendanceAdapter = new AddAttendanceAdapter(getActivity(), mStudentList);
        // set listener
        ListView addAttendanceList = view.findViewById(R.id.add_attendance_list_view);
        addAttendanceList.setAdapter(mAddAttendanceAdapter);

        addAttendanceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // check or un-check checkbox
                Integer[] student = mStudentList.get(position);
                if (student[1] == 0) {
                    student[1] = 1;
                }
                else {
                    student[1] = 0;
                }
                mAddAttendanceAdapter.notifyDataSetChanged();
            }
        });
        // set adapter

    }


    /**
     * Helper to fill student id list array from database
     */
    private void fillStudentList() {

        mStudentList = new ArrayList<>();

        Bundle courseInfo = getActivity().getIntent().getExtras();
        Log.d("Bundle Check", courseInfo.getString("courseCode"));

        mDatabaseHelper = DatabaseHelper.getInstance(getActivity());
        Log.d("DB STUDNET COUNT", String.valueOf(mDatabaseHelper.getStudentCount(courseInfo.getString("courseCode"))));

        for (int i= 1; i <= mDatabaseHelper.getStudentCount(courseInfo.getString("courseCode")); ++i) {
            mStudentList.add(new Integer[]{ new Integer(i), 0});
        }
    }


    /**
     * Inflate menu items into views
     * @param menu menu xml
     * @param inflater inflater obj
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    /**
     * Toolbar Item selection Handler
     * @param item in toolbar
     * @return true: to hold and exit, false: to fall through
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // Reset Course Activity Toolbar
        ((CourseActivity)getActivity()).initToolbar("TITLE");
        ((CourseActivity)getActivity()).setViewHidden(false);
    }
}
