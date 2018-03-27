package com.example.omkar.android.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.omkar.android.CoursesActivity;
import com.example.omkar.android.R;
import com.example.omkar.android.helpers.DatabaseHelper;
import com.example.omkar.android.models.Course;
import com.example.omkar.android.models.Student;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


/**
 * Created by omkar on 26-Mar-18.
 */

public class BackupFragment extends Fragment {

    // view of fragment
    View view;

    private static DatabaseHelper mDatabaseHelper;
    private static LinearLayout mLayoutProgress;
    private static ImageView doneImage;

    // Firebase instances
    private static FirebaseAuth mFirebaseAuth;
    private static FirebaseFirestore mFirestoreInstance;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // configure Toolbar in Courses Activity
        setHasOptionsMenu(true);
        ((CoursesActivity)getActivity()).setFabHidden(true);
        ((CoursesActivity)getActivity()).initToolbar("Backup", R.drawable.ic_menu);
        ((CoursesActivity)getActivity()).setViewHidden(true, R.color.white);

        // get required instances
        mFirebaseAuth = FirebaseAuth.getInstance();
//        Log.d("FIREBASE USER", mFirebaseAuth.getCurrentUser().getUid());
        mFirestoreInstance = FirebaseFirestore.getInstance();
        mDatabaseHelper = DatabaseHelper.getInstance(getActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_backup, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // progress loader
        mLayoutProgress = view.findViewById(R.id.layoutProgress);
        // success indicator
        doneImage = view.findViewById(R.id.done);
        // listener on button
        Button backupButton = view.findViewById(R.id.backupButton);
        backupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doneImage.setVisibility(View.GONE);
                mLayoutProgress.setVisibility(View.VISIBLE);
                collectDataAndBackup();
            }
        });
    }


    /**
     * Collect Data from local database and Backup data to Firestore
     */
    private void collectDataAndBackup() {
        // run task on separate thread
        new CollectDataAndBackupTask(getActivity()).execute();
    }


    /**
     * Async Task class
     */
    private static class CollectDataAndBackupTask extends AsyncTask<Void, Void, String> {

        private WeakReference<Activity> activityReference;

        // only retain a weak reference to the activity
        CollectDataAndBackupTask(Activity context) {
            activityReference = new WeakReference<>(context);
        }

        // task
        @Override
        protected String doInBackground(Void... params) {
            if (Looper.myLooper() == null) {
                Looper.prepare();
            }
            try {
                // get cursor for tables
                Cursor coursesCursor = mDatabaseHelper.getCourses();
                Log.d("LENGTH OF TABLE", String.valueOf(coursesCursor.getCount()));
                Cursor studentsCursor = mDatabaseHelper.getStudentInfo();

                // check if course table is present
                if (coursesCursor.moveToFirst()) {
                    int id = 0;
                    // move through the course table
                    do {
                        // get row attributes for a course and instantiate a object for these
                        int studentCount = coursesCursor.getInt(2);
                        String courseCode = coursesCursor.getString(0);
                        Course course = new Course(coursesCursor.getString(0), coursesCursor.getString(1), studentCount, coursesCursor.getString(3), coursesCursor.getString(4));
                        course.setmDayCount(coursesCursor.getInt(5));

                        // fill array list of Students for a course
                        ArrayList<Student> studentList = new ArrayList<>();
                        // check if student table exists
                        if (studentsCursor.moveToFirst()) {
                            // move through the students table
                            do {
                                // check for the course code as the current course
                                if (studentsCursor.getString(0).equals(courseCode)) {
                                    // get all students for the course
                                    for (int i = 0; i < studentCount; ++i) {
                                        Log.d("CHECK COURSE DATES", studentsCursor.getString(3));
                                        // instantiate Student obj from row attributes and add in the list
                                        studentList.add(new Student(i + 1, studentsCursor.getFloat(1), studentsCursor.getFloat(2), studentsCursor.getString(3)));
                                        studentsCursor.moveToNext();
                                    }
                                    // add list to course
                                    course.setmStudentList(studentList);
                                    // store in Firestore under the email id of the user and doc id as per course
                                    mFirestoreInstance.collection(mFirebaseAuth.getCurrentUser().getEmail()).document(String.valueOf(id)).set(course);
                                    id += 1;
                                    Log.d("DONE", "IN");
                                    break;
                                }
                            } while (studentsCursor.moveToNext());
                        }
                    } while (coursesCursor.moveToNext());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "task finished";
        }

        // after success
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(activityReference.get(), "Successful!", Toast.LENGTH_SHORT).show();
            mLayoutProgress.setVisibility(View.GONE);
            doneImage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Reset Courses Activity Toolbar
        ((CoursesActivity)getActivity()).initToolbar("Courses", R.drawable.ic_menu);
        ((CoursesActivity)getActivity()).setFabHidden(false);
        ((CoursesActivity)getActivity()).setViewHidden(false, R.color.background);
    }

}
