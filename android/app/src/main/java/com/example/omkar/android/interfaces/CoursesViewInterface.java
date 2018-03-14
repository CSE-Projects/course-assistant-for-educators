package com.example.omkar.android.interfaces;

import com.example.omkar.android.models.Course;

/**
 * Created by omkar on 13-Mar-18.
 */

// Contains methods implemented in Courses Activity and used by Add Course Fragment
public interface CoursesViewInterface {

    public void initToolbar(String title, int ic_home);
    public void setDrawerLocked(boolean shouldLock);
    public void setFabHidden(boolean enabled);
    public void insertNewCourse(Course course);
    public void setViewHidden(boolean enabled, int color);
}
