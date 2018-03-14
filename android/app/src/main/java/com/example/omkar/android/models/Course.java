package com.example.omkar.android.models;

import java.util.ArrayList;

/**
 * Created by omkar on 10-Mar-18.
 */

public class Course {

    // members variables
    private String mCourseName;
    private String mCourseCode;
    private int mStudentCount;
    private String mEmailCr = "";
    private String mEmailTa = "";
    private int mDayCount;

    // member object variables
    private ArrayList<Student> mStudentList;
    private ArrayList<ProjectDeadline> mProjectDeadlineList;


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

    public String getCourseName() {
        return mCourseName;
    }

    public String getCourseCode() {
        return mCourseCode;
    }

    public int getStudentCount() {
        return mStudentCount;
    }

    public String getEmailCr() {
        return mEmailCr;
    }

    public String getEmailTa() {
        return mEmailTa;
    }

    public int getDayCount() {
        return mDayCount;
    }

    public void setDayCount(int dayCount) {
        this.mDayCount = dayCount;
    }

    public ArrayList<Student> getStudentList() {
        return mStudentList;
    }

    public void setmStudentList(ArrayList<Student> studentList) {
        mStudentList = studentList;
    }

    public ArrayList<ProjectDeadline> getProjectDeadlineList() {
        return mProjectDeadlineList;
    }

    public void setProjectDeadlineList(ArrayList<ProjectDeadline> projectDeadlineList) {
        mProjectDeadlineList = projectDeadlineList;
    }
}
