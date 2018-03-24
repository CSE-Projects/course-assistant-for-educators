package com.example.omkar.android.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.omkar.android.fragments.AddAttendanceFragment;
import com.example.omkar.android.fragments.AddMarksFragment;
import com.example.omkar.android.models.Course;

import java.util.ArrayList;

/**
 * Created by omkar on 13-Mar-18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // instance of database helper
    private static DatabaseHelper sInstance;

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

    public static final String STUDENT_COLUMN_COURSE_CODE = "courseCode";
    public static final String COURSE_COLUMN_ID = "id";
    public static final String COURSE_COLUMN_INSEMESTER = "insem";
    public static final String COURSE_COLUMN_ENDSEMESTER = "endsem";
    public static final String COURSE_COLUMN_DATES = "dates";


    public static synchronized DatabaseHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }


    /**
     * Constructor should be private to prevent direct instantiation.
     * make call to static method "getInstance()" instead.
     */
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
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
     *
     * @param course course object
     * @return true
     */
    public void insertCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("courseCode", course.getCourseCode());
        contentValues.put("courseName", course.getCourseName());
        contentValues.put("studentCount", course.getStudentCount());
        contentValues.put("dayCount", course.getDayCount());
        contentValues.put("emailCr", course.getEmailCr());
        contentValues.put("emailTa", course.getEmailTa());
        db.insert("courses", null, contentValues);

        insertStudentForCourse(course.getCourseCode(), course.getStudentCount());
    }


    /**
     * Insert Students for a course in students table
     *
     * @param courseCode   course code for that course
     * @param studentCount student count for that course
     */
    private void insertStudentForCourse(String courseCode, int studentCount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < studentCount; ++i) {
            contentValues.put("courseCode", courseCode);
            contentValues.put("id", i + 1);
            contentValues.put("dates", "");
            contentValues.put("insem", 0);
            contentValues.put("endsem", 0);
            db.insert("students", null, contentValues);
        }

    }


    /**
     * Get student count for a course
     *
     * @param courseCode course code for that course
     * @return student count for the course
     */
    public int getStudentCount(String courseCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select courseCode,studentCount from courses", null);
        int studentCount = 0;
        // iterate through column elements
        if (c.moveToFirst()) {
            do {
                // check for the course code passed
                if (c.getString(0).equals(courseCode)) {
                    // found course entry
                    studentCount = c.getInt(1);
                    break;
                }
            } while (c.moveToNext());
        }
        c.close();
        return studentCount;
    }


    /**
     * Increment day count for a course
     * @param courseCode of the course
     */
    private void incrementDayCount(String courseCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select courseCode,dayCount from courses", null);

        if (c.moveToFirst()) {
            do {
                if (c.getString(0).equals(courseCode)) {
                    int newDayCount = c.getInt(1);
                    // add new day count to dayCount in table
                    ContentValues values = new ContentValues();
                    values.put("dayCount", newDayCount + 1);
                    db.update("courses", values, "courseCode= '" + courseCode +"'", null);
                }
            } while(c.moveToNext());
        }
//        c = db.rawQuery("select courseCode,dayCount from courses", null);
//        // move though rows in tables selected above
//        if (c.moveToFirst()) {
//            do {
//                Log.d("CODE", c.getString(0));
//                Log.d("DAYCOUNT", String.valueOf(c.getInt(1)));
//            } while (c.moveToNext());
//        }
//        c.close();

    }


    /**
     * Update attendance field in dates
     * @param courseCode            course for the attendance
     * @param studentAttendanceList attendance list
     * @param date                  date of taking attendance
     */
    public void updateAttendance(String courseCode, ArrayList<AddAttendanceFragment.StudentAttendance> studentAttendanceList, String date) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("select courseCode,id,dates from students", null);
        // iterator for student attendance list
        int it = 0;

        // move though rows in tables selected above
        if (c.moveToFirst()) {
            do {
                if (it == studentAttendanceList.size()) {
                    break;
                }
                // check for the course code
                if (c.getString(0).equals(courseCode)) {
                    // check if student is present or absent
                    if (studentAttendanceList.get(it).isChecked()) {
                        String dates = c.getString(2);
                        dates += "," + date;
                        // add date to dates in table
                        ContentValues values = new ContentValues();
                        values.put("dates", dates);
                        db.update("students", values, "courseCode= '" + courseCode + "' AND id= " + (it + 1) + "", null);
                    }
                    ++it;
                }
            } while (c.moveToNext());
        }
        c.close();

//        c = db.rawQuery("select courseCode,id,dates from students", null);
//        // move though rows in tables selected above
//        if (c.moveToFirst()) {
//            do {
//                Log.d("CODE", c.getString(0));
//                Log.d("ID", c.getString(1));
//                Log.d("DATES", c.getString(2));
//
//            } while (c.moveToNext());
//        }
//        c.close();

        incrementDayCount(courseCode);
    }


    /**
     * Get course code, insem, endsem, dates column from students table
     * @return Cursor for the above
     */
    public Cursor getStudentInfo() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery( "select courseCode,insem,ensem,dates from students", null );
    }


    /**
     * Get course code column from course table
     * @return course code column
     */
    public Cursor getCourseInfo() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery( "select courseCode,courseName,emailCr,emailTa from courses", null );
    }


    /**
     * Get course code and dates from students table
     * @return cursor to both columns
     */
    public Cursor getStudentAttendance() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery( "select courseCode,dates from students", null );
    }


    /**
     * Update insem, endsem marks for students in a course
     * @param courseCode of the course
     * @param studentMarksList student marks list
     */
    public void addMarks(String courseCode, ArrayList<AddMarksFragment.StudentMarks> studentMarksList) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("select courseCode,id,insem,endsem from students", null);
        // iterator for student marks list
        int it = 0;

        // move though rows in tables selected above
        if (c.moveToFirst()) {
            do {
                if (it == studentMarksList.size()) {
                    break;
                }
                // check for the course code
                if (c.getString(0).equals(courseCode)) {
                    ContentValues values = new ContentValues();
//                    Log.d("Check insem",String.valueOf(studentMarksList.get(it).getInsem()));
                    // add fields
                    values.put("insem", studentMarksList.get(it).getInsem());
                    values.put("endsem", studentMarksList.get(it).getEndsem());
                    db.update("students", values, "courseCode= '" + courseCode + "' AND id= " + (it + 1) + "", null);
                    ++it;
                }
            } while (c.moveToNext());
        }
        c.close();

//        c = db.rawQuery("select courseCode,insem,endsem from students", null);
//        // move though rows in tables selected above
//        if (c.moveToFirst()) {
//            do {
//                Log.d("CODE", c.getString(0));
//                Log.d("INSEM", c.getString(1));
//                Log.d("ENDSEM", c.getString(2));
//
//            } while (c.moveToNext());
//        }
//        c.close();
    }
}