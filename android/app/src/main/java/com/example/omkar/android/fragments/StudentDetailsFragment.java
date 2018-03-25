package com.example.omkar.android.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.omkar.android.CourseActivity;
import com.example.omkar.android.R;
import com.example.omkar.android.adapters.StudentDetailsAdapter;
import com.example.omkar.android.helpers.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by omkar on 23-Mar-18.
 */

public class StudentDetailsFragment extends Fragment{


    // view of fragment
    View view;
    private DatabaseHelper mDatabaseHelper;
    private String mCourseCode;
    private ArrayList<String[]> mStudentDetailsList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // configure Toolbar in Course Activity
        setHasOptionsMenu(true);
        ((CourseActivity)getActivity()).initToolbar("Student Details");
        ((CourseActivity)getActivity()).setViewHidden(true, R.color.white);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_student_details, container, false);

        mStudentDetailsList = new ArrayList<>();
        // get course code
        Bundle courseInfo = getActivity().getIntent().getExtras();
        mCourseCode = courseInfo.getString("courseCode");
        Log.d("COURSE CODE", mCourseCode);

        // get student count for this course
        mDatabaseHelper = DatabaseHelper.getInstance(getActivity());
        int studentCount = mDatabaseHelper.getStudentCount(mCourseCode);
        // get no of days attendance has been taken for this course
        int dayCount = mDatabaseHelper.getDayCount(mCourseCode);

        // get student information
        Cursor c = mDatabaseHelper.getStudentInfo();
        if (c.moveToFirst()) {
            do {
                // check for the course code
                if (c.getString(0).equals(mCourseCode)) {
                    // found course entry
                    break;
                }
            } while (c.moveToNext());
        }

        // fill mStudentsList with the information
        for (int i = 0; i < studentCount; i++) {
            // no of days attended
            String dates = c.getString(3);
            int dateCount = dates.length() - dates.replace(",", "").length();;
            Log.d("DATE COUNT", String.valueOf(dateCount));
            Log.d("DAY COUNT", String.valueOf(dayCount));
            // add info
            mStudentDetailsList.add(new String[]{String.valueOf(i + 1), String.valueOf(c.getInt(1)), String.valueOf(c.getInt(2)), dayCount != 0?String.valueOf((float)((dateCount * 100) / dayCount)):"0"});
            if(!c.moveToNext()){break;}
        }
        c.close();

        // return inflated view
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        StudentDetailsAdapter studentDetailsAdapter = new StudentDetailsAdapter(getActivity(), mStudentDetailsList);
        ListView studentDetailsList = view.findViewById(R.id.list_student_details);
        studentDetailsList.setAdapter(studentDetailsAdapter);

    }


    /**
     * Inflate menu items into views
     * @param menu menu xml
     * @param inflater inflater obj
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_overflow, menu);
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
            case R.id.sort_marks:
//                sortByMarks();
                getActivity().onBackPressed();
                return true;
            case R.id.sort_attendance:
//                sortByAttendance();
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
        ((CourseActivity)getActivity()).setViewHidden(false, R.color.background);
    }
}
