package com.example.omkar.android.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

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
    private ArrayList<Integer> mStudentIdList;
    private DatabaseHelper mDatabaseHelper;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // configure Toolbar in Courses Activity
//        ((CoursesActivity)getActivity()).initToolbar("Document Similarity", R.drawable.ic_menu);
//        ((CoursesActivity)getActivity()).setViewHidden(true, R.color.white);

        fillStudentList();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_add_attendance, container, false);
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
        // context of activity
        mAddAttendanceAdapter = new AddAttendanceAdapter(getActivity(), mStudentIdList);
        // set listener
        ListView addAttendanceList = view.findViewById(R.id.add_attendance_list_view);
        addAttendanceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int item, long id) {
                // check or un-check checkbox
                CheckBox checkBox = view.findViewById(R.id.check);
                if (checkBox.isChecked()) {
                    checkBox.setChecked(true);
                }
                else{
                    checkBox.setChecked(false);
                }
            }
        });
    }


    /**
     * Helper to fill student id list array from database
     */
    private void fillStudentList() {
//        mDatabaseHelper = DatabaseHelper.getInstance(getActivity());
//        mDatabaseHelper
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // Reset Courses Activity Toolbar
//        ((CoursesActivity)getActivity()).initToolbar("Courses", R.drawable.ic_menu);
//        ((CoursesActivity)getActivity()).setFabHidden(false);
//        ((CoursesActivity)getActivity()).setViewHidden(false, R.color.background);
    }
}
