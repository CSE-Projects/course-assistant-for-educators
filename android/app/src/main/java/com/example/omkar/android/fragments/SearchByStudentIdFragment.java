package com.example.omkar.android.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omkar.android.CourseActivity;
import com.example.omkar.android.R;
import com.example.omkar.android.helpers.DatabaseHelper;

import java.util.ArrayList;


/**
 * Created by dibyadarshan on 28/3/18.
 */

public class SearchByStudentIdFragment extends Fragment{
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
        ((CourseActivity)getActivity()).initToolbar("Search");
        ((CourseActivity)getActivity()).setViewHidden(true, R.color.white);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_search_student, container, false);

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


        Button searchButton = view.findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                // TODO Auto-generated method stub
                EditText searchForId = view.findViewById(R.id.searchForId);
                String studentIdObtained = searchForId.getText().toString();

                int i, flag=0;

                for( i = 0; i < mStudentDetailsList.size(); ++i){

                    if(mStudentDetailsList.get(i)[0].equals(studentIdObtained)){

                        flag=1;

                        TextView studentId = view.findViewById(R.id.studentId);
                        TextView insemMarks = view.findViewById(R.id.insemMarks);
                        TextView endsemMarks = view.findViewById(R.id.endsemMarks);
                        TextView attendancePercent = view.findViewById(R.id.attendancePercent);

                        studentId.setText(mStudentDetailsList.get(i)[0]);
                        insemMarks.setText(mStudentDetailsList.get(i)[1]);
                        endsemMarks.setText(mStudentDetailsList.get(i)[2]);
                        attendancePercent.setText(mStudentDetailsList.get(i)[3]);

                        break;
                    }
                }

                if(flag==0){
                    Toast.makeText(getActivity(),"Invalid Input!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        // return inflated view
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
