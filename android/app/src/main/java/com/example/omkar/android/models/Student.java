package com.example.omkar.android.models;

/**
 * Created by omkar on 11-Mar-18.
 */

public class Student {

    private int mStudentId = 0;
    private float mInsemester = 0;
    private float mEndsemester = 0;
//    private ArrayList<Date> mAttendanceList;
    private String mAttendanceDates = "";

    public Student() {}

    public Student(int studentId, float insemester, float endsemester, String attendanceDates) {
        mStudentId = studentId;
        mInsemester = insemester;
        mEndsemester = endsemester;
        mAttendanceDates = attendanceDates;
    }

    public int getmStudentId() {
        return mStudentId;
    }

    public void setmStudentId(int mStudentId) {
        this.mStudentId = mStudentId;
    }

    public float getmInsemester() {
        return mInsemester;
    }

    public void setmInsemester(float insemester) {
        mInsemester = insemester;
    }

    public float getmEndsemester() {
        return mEndsemester;
    }

    public void setmEndsemester(float endsemester) {
        this.mEndsemester = endsemester;
    }

//    public ArrayList<Date> getAttendanceList() {
//        return mAttendanceList;
//    }
//
//    public void setAttendanceList(ArrayList<Date> attendanceList) {
//        mAttendanceList = attendanceList;
//    }

    public String getmAttendanceDates() {
        return mAttendanceDates;
    }

    public void setmAttendanceDates(String mAttendanceDates) {
        this.mAttendanceDates = mAttendanceDates;
    }
}
