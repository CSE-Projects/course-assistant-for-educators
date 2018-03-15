package com.example.omkar.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class CourseActivity extends AppCompatActivity {

    String mCourseCode;
    String mCRemail;
    String mTAemail;
    String mCourseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        // Intent bundle for getting extra info
        Bundle courseInfo = getIntent().getExtras();
        mCourseCode = courseInfo.getString("courseCode");
//        mCRemail = courseInfo.getString("emailCR");
//        mTAemail = courseInfo.getString("emailTA");
//        mCourseName = courseInfo.getString("courseName");
//        Log.d("Course Code", mCourseCode);

    }


    public void contactMailCr(View v) {
        Intent email = new Intent(android.content.Intent.ACTION_SEND);

        email.setType("plain/text");
        email.putExtra(android.content.Intent.EXTRA_EMAIL, "one");
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, mCourseCode + ": " + mCourseName);
        email.putExtra(android.content.Intent.EXTRA_TEXT,"Sent from: CourseAssistant");

        startActivity(Intent.createChooser(email, "Send mail"));
    }


    public void contactMailTa(View v) {
        Intent email = new Intent(android.content.Intent.ACTION_SEND);
        email.setType("plain/text");
        email.putExtra(android.content.Intent.EXTRA_EMAIL, "two");
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, mCourseCode + ": " + mCourseName);
        email.putExtra(android.content.Intent.EXTRA_TEXT,"Sent from: CourseAssistant");

        startActivity(Intent.createChooser(email, "Send mail"));
    }
}
