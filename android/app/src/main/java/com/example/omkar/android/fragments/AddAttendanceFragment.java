package com.example.omkar.android.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.omkar.android.CourseActivity;
import com.example.omkar.android.R;
import com.example.omkar.android.adapters.AddAttendanceAdapter;
import com.example.omkar.android.helpers.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by omkar on 15-Mar-18.
 */

public class AddAttendanceFragment extends Fragment {

    // view of fragment
    View view;
    private AddAttendanceAdapter mAddAttendanceAdapter;
    private ArrayList<StudentAttendance> mStudentAttendanceList;
    private DatabaseHelper mDatabaseHelper;
    private String mCourseCode;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // configure Toolbar in Course Activity
        setHasOptionsMenu(true);
        ((CourseActivity) getActivity()).initToolbar("Add Attendance");
        ((CourseActivity) getActivity()).setViewHidden(true, R.color.white);

        fillStudentList();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_attendance, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initList();
    }


    /**
     * Initialize add attendance list view
     */
    private void initList() {
        // Create an adapter
        mAddAttendanceAdapter = new AddAttendanceAdapter(mStudentAttendanceList);
        // Get a reference to the recycler view
        RecyclerView addAttendanceList = view.findViewById(R.id.add_attendance_recycler_view);
        // Set layout manager
        addAttendanceList.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        // Set
        addAttendanceList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        // set adapter
        addAttendanceList.setAdapter(mAddAttendanceAdapter);
    }

    /**
     * Helper to fill student id list array from database
     */
    private void fillStudentList() {
        // instantiate student list
        mStudentAttendanceList = new ArrayList<>();

        // get the course code
        Bundle courseInfo = getActivity().getIntent().getExtras();
        mCourseCode = courseInfo.getString("courseCode");
//        Log.d("Bundle Check", courseInfo.getString("courseCode"));

        // get student count
        mDatabaseHelper = DatabaseHelper.getInstance(getActivity());
        int studentCount = mDatabaseHelper.getStudentCount(mCourseCode);
//        Log.d("DB STUDNET COUNT", String.valueOf(mDatabaseHelper.getStudentCount(courseInfo.getString("courseCode"))));

        // fill student list
        for (int i = 1; i <= studentCount; ++i) {
            mStudentAttendanceList.add(new StudentAttendance(String.valueOf(i), false));
        }
    }


    /**
     * Save attendance in database
     */
    private void saveAttendance() {
        // get db instance
        mDatabaseHelper = DatabaseHelper.getInstance(getActivity());
        // get current date
        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        // update attendance
        mDatabaseHelper.updateAttendance(mCourseCode, mStudentAttendanceList, date);
        Toast.makeText(getActivity(), "Saved!", Toast.LENGTH_SHORT).show();
    }


    /**
     * Inflate menu items into views
     *
     * @param menu     menu xml
     * @param inflater inflater obj
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    /**
     * Toolbar Item selection Handler
     *
     * @param item in toolbar
     * @return true: to hold and exit, false: to fall through
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            case R.id.save:
                saveAttendance();
                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // Reset Course Activity Toolbar
        ((CourseActivity) getActivity()).initToolbar("TITLE");
        ((CourseActivity) getActivity()).setViewHidden(false, R.color.background);
    }


    /**
     * Class template for Attendance of Student
     */
    public class StudentAttendance {

        private String id = "";
        private boolean checked = false;

        public StudentAttendance(String name, boolean checked) {
            this.id = name;
            this.checked = checked;
        }

        public String getId() {
            return id;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }
    }


}
