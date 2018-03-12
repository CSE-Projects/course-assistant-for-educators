package com.example.omkar.android.models;

import java.sql.Time;
import java.util.Date;

/**
 * Created by omkar on 11-Mar-18.
 */

public class ProjectDeadline {

    private String mName;
    private Time mTime;
    private Date mDate;

    public ProjectDeadline(String name, Time time, Date date) {
        this.mName = name;
        this.mTime = time;
        this.mDate = date;
    }

    public String getName() {
        return mName;
    }

    public Time getTime() {
        return mTime;
    }

    public Date getDate() {
        return mDate;
    }
}
