package com.example.omkar.android.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.omkar.android.models.Course;

/**
 * Created by omkar on 13-Mar-18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // identifiers
    public static final String DATABASE_NAME = "Course.db";
    public static final String COURSE_TABLE_NAME = "courses";
    public static final String STUDENT_TABLE_NAME = "students";

    public static final String COURSE_COLUMN_COURSE_CODE = "courseCode";
    public static final String COURSE_COLUMN_COURSE_NAME = "courseName";
    public static final String COURSE_COLUMN_STUDENT_COUNT = "studentCount";
    public static final String COURSE_COLUMN_DAY_COUNT = "dayCount";
    public static final String COURSE_COLUMN_CR_EMAIL = "emailCR";
    public static final String COURSE_COLUMN_TA_EMAIL = "emailTa";

    public static final String STUDENT_COLUMN_COURSE_CODE = "studentCourseCode";
    public static final String COURSE_COLUMN_ID = "id";
    public static final String COURSE_COLUMN_INSEMESTER = "insem";
    public static final String COURSE_COLUMN_ENDSEMESTER = "endsem";
    public static final String COURSE_COLUMN_DATES = "dates";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // create two tables
        db.execSQL(
                "create table courses " +
                        "(courseCode STRING primary key, courseName STRING, studentCount INTEGER, dayCount INTEGER, emailCr STRING, emailTa STRING)"
        );

        db.execSQL(
                "create table students " +
                        "(id INTEGER, courseCode STRING, insem FLOAT, endsem FLOAT, dates STRING)"
        );
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    /**
     * Insert a Course attributes in course table
     * @param course course object
     * @return
     */
    public boolean insertCourse (Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("courseCode", course.getCourseCode());
        contentValues.put("courseName", course.getCourseName());
        contentValues.put("studentCount", course.getStudentCount());
        contentValues.put("dayCount", course.getDayCount());
        contentValues.put("emailCr", course.getEmailCr());
        contentValues.put("emailTa", course.getEmailTa());
        db.insert("courses", null, contentValues);
        return true;
    }


    /**
     * Get course code column from course table
     * @return course code column
     */
    public Cursor getCourseInfo() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery( "select courseCode,courseName from courses", null );
    }
}