package com.example.omkar.android.fragments;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omkar.android.CourseActivity;
import com.example.omkar.android.R;
import com.example.omkar.android.helpers.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by dibyadarshan on 23/3/18.
 */

public class AddMarksFragment extends Fragment{

    // view of fragment
    View view;
    private ArrayList<AddMarksFragment.StudentMarks> mStudentMarksList;
    private DatabaseHelper mDatabaseHelper;
    private String mCourseCode;
    private int mStudentCount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // configure Toolbar in Course Activity
        setHasOptionsMenu(true);
        ((CourseActivity)getActivity()).initToolbar("Add Marks");
        ((CourseActivity)getActivity()).setViewHidden(true, R.color.white);

        fillStudentList();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_add_marks, container, false);

        // get table layout
        TableLayout marksTable = view.findViewById(R.id.marksTable);
        for (int i = 0; i < mStudentCount; i++) {
            // create new row
            TableRow row = new TableRow(getActivity());
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 8, 0);
            row.setLayoutParams(layoutParams);
            // edit text for insem marks
            EditText insemEditText = new EditText(getActivity());
            insemEditText.setTag("insem" + i);
            insemEditText.setWidth(16);
            // edit text for endsem marks
            EditText endsemEditText = new EditText(getActivity());
            endsemEditText.setTag("endsem" + i);
            endsemEditText.setWidth(16);
            // id
            TextView idText = new TextView(getActivity());
            idText.setText(String.valueOf(i+1));
            idText.setGravity(Gravity.CENTER_HORIZONTAL);
            idText.setTypeface(null, Typeface.BOLD);
            idText.setTextSize(18);

            row.addView(idText);
            row.addView(insemEditText);
            row.addView(endsemEditText);

            marksTable.addView(row,i);
        }
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    /**
     * Helper to fill student list array from database
     */
    private void fillStudentList() {
        // instantiate student list
        mStudentMarksList = new ArrayList<>();

        // get the course code
        Bundle courseInfo = getActivity().getIntent().getExtras();
        mCourseCode = courseInfo.getString("courseCode");
//        Log.d("Bundle Check", courseInfo.getString("courseCode"));

        // get student count
        mDatabaseHelper = DatabaseHelper.getInstance(getActivity());
        mStudentCount = mDatabaseHelper.getStudentCount(mCourseCode);
//        Log.d("DB STUDNET COUNT", String.valueOf(mDatabaseHelper.getStudentCount(courseInfo.getString("courseCode"))));

        // fill student list
        for (int i = 0; i < mStudentCount; ++i) {
            mStudentMarksList.add(new AddMarksFragment.StudentMarks(String.valueOf(i+1)));
        }
    }


    /**
     * Save marks in database
     */
    private void saveMarks() {
        // get db instance
        mDatabaseHelper = DatabaseHelper.getInstance(getActivity());
        // update attendance

        // accumulate entered marks
        for(int i = 0; i < mStudentCount; i++){
            // get marks fields
            EditText insemEditText = view.findViewWithTag("insem"+i);
            EditText endsemEditText = view.findViewWithTag("endsem"+i);
            // add to list
            mStudentMarksList.get(i).setInsem(Integer.valueOf(insemEditText.getText().toString()));
            mStudentMarksList.get(i).setEndsem(Integer.valueOf(endsemEditText.getText().toString()));

        }
        // save in database
        mDatabaseHelper.addMarks(mCourseCode, mStudentMarksList);
        Toast.makeText(getActivity(), "Saved!", Toast.LENGTH_SHORT).show();
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
                saveMarks();
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


    /**
     * Class template for Attendance of Student
     */
    public class StudentMarks {

        private String id = "";
        private int insem;
        private int endsem;

        public StudentMarks(String name) {
            this.id = name;
            this.insem = 0;
            this.endsem = 0;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getInsem() {
            return insem;
        }

        public void setInsem(int insem) {
            this.insem = insem;
        }

        public int getEndsem() {
            return endsem;
        }

        public void setEndsem(int endsem) {
            this.endsem = endsem;
        }
    }
}
