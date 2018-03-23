package com.example.omkar.android.fragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ListView;

import com.example.omkar.android.CourseActivity;
import com.example.omkar.android.R;
import com.example.omkar.android.adapters.ViewAttendanceAdapter;
import com.example.omkar.android.helpers.DatabaseHelper;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by omkar on 20-Mar-18.
 */

public class ViewAttendanceFragment extends Fragment{

    // view of fragment
    View view;
    // course code
    private String mCourseCode;
    private DatabaseHelper mDatabaseHelper;
    private ArrayList<String[]> mAttendanceList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // configure Toolbar in Course Activity
        setHasOptionsMenu(true);
        ((CourseActivity)getActivity()).initToolbar("Attendance");
        ((CourseActivity)getActivity()).setViewHidden(true, R.color.white);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_view_attendance, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // get calendar instance
        final Calendar myCalendar = Calendar.getInstance();

        // TODO: Keep the disalog persistent and make a call to backPressed of Parent when cancel is pressed
        // set date picker dialog listener for selection of date
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // set date
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Log.d("DATE PICKER", String.valueOf(year));
                Log.d("DATE PICKER", String.valueOf(monthOfYear));
                Log.d("DATE PICKER", String.valueOf(dayOfMonth));

                String month = "";
                if(monthOfYear < 10) {
                    month="0"+String.valueOf(monthOfYear + 1);
                }

                displayAttendanceForTheDay(String.valueOf(dayOfMonth)+'/'+month+'/'+String.valueOf(year));
            }
        };

        // display the date picker dialog
        new DatePickerDialog(getActivity(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    /**
     * Display Attendance for the day
     * @param date date picked
     */
    private void displayAttendanceForTheDay(String date) {
        Log.d("SELECTED DATE", date);
        // get course code
        Bundle courseInfo = getActivity().getIntent().getExtras();
        mCourseCode = courseInfo.getString("courseCode");
        Log.d("COURSE CODE", mCourseCode);

        // initialize attendance list
        mAttendanceList = new ArrayList<>();

        // get student count
        mDatabaseHelper = DatabaseHelper.getInstance(getActivity());
        int studentCount = mDatabaseHelper.getStudentCount(mCourseCode);
        Log.d("STUDENT COUNT", String.valueOf(studentCount));

        // get attendance dates and course code from database
        Cursor c = mDatabaseHelper.getStudentAttendance();
        if (c.moveToFirst()) {
            do {
                // check for the course code
                if (c.getString(0).equals(mCourseCode)) {
                    // found course entry
                    break;
                }
            } while (c.moveToNext());
        }

        // fil the attendance list
        Log.d("CHECK ATT", "IN");
        for (int i = 0; i < studentCount; i++) {
            Log.d("ATTENDANCE DATES", c.getString(1));
            boolean isFound = c.getString(1).contains(date);
            mAttendanceList.add(new String[]{String.valueOf(i+1), isFound? "P":"A"});
            if(!c.moveToNext()){break;}
        }
        c.close();

        // set adapter and init list view
        ViewAttendanceAdapter viewAttendanceAdapter = new ViewAttendanceAdapter(getActivity(), mAttendanceList);
        ListView attendanceListView = view.findViewById(R.id.view_attendance_list);
        attendanceListView.setAdapter(viewAttendanceAdapter);
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
        ((CourseActivity)getActivity()).setViewHidden(false, R.color.background);
    }
}
