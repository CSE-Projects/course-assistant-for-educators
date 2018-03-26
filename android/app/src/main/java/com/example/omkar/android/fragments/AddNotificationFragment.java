package com.example.omkar.android.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.omkar.android.CourseActivity;
import com.example.omkar.android.R;

import java.util.Calendar;

/**
 * Created by dibyadarshan on 26/3/18.
 */

public class AddNotificationFragment extends Fragment {

    View view;
    private String mCourseCode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // configure Toolbar in Course Activity
        setHasOptionsMenu(true);
        ((CourseActivity)getActivity()).initToolbar("Add Notification");
        ((CourseActivity)getActivity()).setViewHidden(true, R.color.white);

        getCourseCode();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_add_notification, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    /**
     * Get course code
     */
    private void getCourseCode() {
        Bundle courseInfo = getActivity().getIntent().getExtras();
        mCourseCode = courseInfo.getString("courseCode");

    }


    /**
     * Save notifications 
     */
    private void saveNotification() {

        TimePicker timePicker = getView().findViewById(R.id.timePicker);

        DatePicker datePicker = getView().findViewById(R.id.datePicker);

        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
        long startTime = calendar.getTimeInMillis();
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", startTime);
        intent.putExtra("endTime", startTime+60*60*1000);
        intent.putExtra("title", mCourseCode+": Project");
        startActivity(intent);

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
            case R.id.save:
                saveNotification();
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

