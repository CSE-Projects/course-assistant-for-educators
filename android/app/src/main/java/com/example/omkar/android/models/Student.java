package com.example.omkar.android.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by omkar on 11-Mar-18.
 */

public class Student {

    private int mStudentId;
    private float mInsemester;
    private float mEndsemester;
    private ArrayList<Date> mAttendanceList;

    public Student(int studentId) {
        mStudentId = studentId;
    }

    public int getStudentId() {
        return mStudentId;
    }

    public float getInsemester() {
        return mInsemester;
    }

    public void setInsemester(float insemester) {
        mInsemester = insemester;
    }

    public float getEndsemester() {
        return mEndsemester;
    }

    public void setEndsemester(float endsemester) {
        this.mEndsemester = endsemester;
    }

    public ArrayList<Date> getAttendanceList() {
        return mAttendanceList;
    }

    public void setAttendanceList(ArrayList<Date> attendanceList) {
        mAttendanceList = attendanceList;
    }
}
