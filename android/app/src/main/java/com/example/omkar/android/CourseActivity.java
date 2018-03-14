package com.example.omkar.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class CourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        // Intent bundle for getting extra info
        Bundle play = getIntent().getExtras();
        String courseCode = play.getString("courseCode");
        Log.d("Course Code", courseCode);

    }
}
