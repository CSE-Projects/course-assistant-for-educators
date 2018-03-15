package com.example.omkar.android;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.omkar.android.fragments.AddAttendanceFragment;
import com.example.omkar.android.interfaces.CourseViewInterface;

public class CourseActivity extends AppCompatActivity implements CourseViewInterface {

    private String mCourseCode;
    private String mCrEmail;
    private String mTaEmail;
    private String mCourseName;

    private static final String EMAIL_BODY = "Sent from: Course Assistant";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        // Intent bundle for getting extra info
        Bundle courseInfo = getIntent().getExtras();
        mCourseCode = courseInfo.getString("courseCode");
        mCrEmail = courseInfo.getString("emailCr");
        mTaEmail = courseInfo.getString("emailTa");
        mCourseName = courseInfo.getString("courseName");

        // init methods
        initToolbar(mCourseCode);
        initButtons();

    }


    /**
     * Set toolbar details
     * @param title title for toolbar
     */
    @Override
    public void initToolbar(String title) {
        // Add toolbar support
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        // add home button in toolbar
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        // set title
        actionbar.setTitle(title);
    }


    /**
     * Hide activity view
     * @param enabled true: hide, false: show
     */
    @Override
    public void setViewHidden(boolean enabled) {
        LinearLayout courseView = findViewById(R.id.course_view);
        if (enabled) {
            courseView.setVisibility(View.GONE);
        }
        else {
            courseView.setVisibility(View.VISIBLE);
            initToolbar(mCourseCode);
        }
    }


    /**
     * initialize buttons in the cards of view
     */
    private void initButtons() {
        Button addAttendance = findViewById(R.id.addAttendance);
        addAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new fragment
                AddAttendanceFragment addAttendanceFragment = new AddAttendanceFragment();
                // get transaction manager
                FragmentManager manager = getFragmentManager();
                // start transaction
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.fragContainer, addAttendanceFragment, "Add Attendance Fragment");
                // add this fragment to stack
                transaction.addToBackStack("Add Attendance Fragment");
                // commit this transaction
                transaction.commit();
            }
        });
    }


    // methods to contact CR and TA
    public void contactMailCr(View v) {
        Intent email = new Intent(android.content.Intent.ACTION_SEND);

        email.setType("plain/text");
        email.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{mCrEmail});
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, mCourseCode + ": " + mCourseName);
        email.putExtra(android.content.Intent.EXTRA_TEXT, EMAIL_BODY);

        startActivity(Intent.createChooser(email, "Send mail"));
    }
    public void contactMailTa(View v) {
        Intent email = new Intent(android.content.Intent.ACTION_SEND);
        email.setType("plain/text");
        email.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{mTaEmail});
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, mCourseCode + ": " + mCourseName);
        email.putExtra(android.content.Intent.EXTRA_TEXT, EMAIL_BODY);

        startActivity(Intent.createChooser(email, "Send mail"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if(getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
            finish();
        }
    }
}
