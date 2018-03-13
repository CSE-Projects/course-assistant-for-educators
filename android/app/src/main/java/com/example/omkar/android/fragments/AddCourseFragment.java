package com.example.omkar.android.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.omkar.android.CoursesActivity;
import com.example.omkar.android.R;

/**
 * Created by omkar on 12-Mar-18.
 */

public class AddCourseFragment extends Fragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // configure Toolbar in Courses Activity
        setHasOptionsMenu(true);
        ((CoursesActivity)getActivity()).setDrawerLocked(true);
        ((CoursesActivity)getActivity()).initToolbar("Course Details", R.drawable.ic_arrow_back);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_course, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


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
        // Reset Courses Activity Toolbar
        ((CoursesActivity)getActivity()).setDrawerLocked(false);
        ((CoursesActivity)getActivity()).initToolbar("Courses", R.drawable.ic_menu);
        ((CoursesActivity)getActivity()).setFabHidden(false);
    }
}




