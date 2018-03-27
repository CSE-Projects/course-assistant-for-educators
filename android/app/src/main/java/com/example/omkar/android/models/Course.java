package com.example.omkar.android.models;

import java.util.ArrayList;

/**
 * Created by omkar on 10-Mar-18.
 */

public class Course {

    // members variables
    private String mCourseName = "";
    private String mCourseCode = "";
    private int mStudentCount = 0;
    private String mEmailCr = "";
    private String mEmailTa = "";
    private int mDayCount = 0;

    // member object variables
    private ArrayList<Student> mStudentList;
//    private ArrayList<ProjectDeadline> mProjectDeadlineList;

    public Course() {}

    /**
     * Constructor to Initialize params
     * @param courseCode course code
     * @param studentCount number of students for the course
     */
    public Course(String courseName, String courseCode, int studentCount, String emailCr, String emailTa) {
        mCourseName = courseName;
        mCourseCode = courseCode;
        mStudentCount = studentCount;
        mEmailCr = emailCr;
        mEmailTa = emailTa;
        mDayCount = 0;
    }

    // getters for variables required on object instantiation
    public String getmCourseName() {
        return mCourseName;
    }

    public String getmCourseCode() {
        return mCourseCode;
    }

    public int getmStudentCount() {
        return mStudentCount;
    }

    public String getmEmailCr() {
        return mEmailCr;
    }

    public String getmEmailTa() {
        return mEmailTa;
    }

    public int getmDayCount() {
        return mDayCount;
    }

    public void setmDayCount(int dayCount) {
        this.mDayCount = dayCount;
    }

    public ArrayList<Student> getmStudentList() {
        return mStudentList;
    }

    public void setmStudentList(ArrayList<Student> studentList) {
        mStudentList = studentList;
    }

//    public ArrayList<ProjectDeadline> getProjectDeadlineList() {
//        return mProjectDeadlineList;
//    }
//
//    public void setProjectDeadlineList(ArrayList<ProjectDeadline> projectDeadlineList) {
//        mProjectDeadlineList = projectDeadlineList;
//    }

    public void setmCourseName(String mCourseName) {
        this.mCourseName = mCourseName;
    }

    public void setmCourseCode(String mCourseCode) {
        this.mCourseCode = mCourseCode;
    }

    public void setmStudentCount(int mStudentCount) {
        this.mStudentCount = mStudentCount;
    }

    public void setmEmailCr(String mEmailCr) {
        this.mEmailCr = mEmailCr;
    }

    public void setmEmailTa(String mEmailTa) {
        this.mEmailTa = mEmailTa;
    }
}
