package com.example.omkar.android;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.omkar.android.fragments.AddCourseFragment;
import com.example.omkar.android.helpers.DatabaseHelper;
import com.example.omkar.android.interfaces.CoursesViewInterface;
import com.example.omkar.android.models.Course;

import java.util.ArrayList;

public class CoursesActivity extends AppCompatActivity implements CoursesViewInterface {


    private DrawerLayout mDrawerLayout;
    private FloatingActionButton mAddCourseFab;
    private AddCourseFragment mAddCourseFragment;
    private DatabaseHelper mDbHelper;
    private RecyclerView mRecyclerView;

    private ArrayList<String> mCourseCodeList;
    private ArrayAdapter<String> mAdapter;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        // Drawer Layout instantiated
        mDrawerLayout = findViewById(R.id.drawer_layout);

        // display courses
        displayCourses();

        // initialize views
        initToolbar("Courses", R.drawable.ic_menu);
        initSideNav();
        initAddCourseFab();
    }


    /**
     * Display courses from db
     */
    private void displayCourses() {
        // cource code list
        mCourseCodeList = new ArrayList<>();

        // get contents
        mDbHelper = new DatabaseHelper(this);
        Cursor c = mDbHelper.getCourseCodes();

        if (c.moveToFirst()){
            do {
                mCourseCodeList.add(c.getString(0));
                // Do something Here with values
            } while(c.moveToNext());
        }
        c.close();

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mCourseCodeList);
        mListView = findViewById(R.id.courses_list_view);
        mListView.setAdapter(mAdapter);
    }

    /**
     * Insert new course into database and list
     * @param course passed from fragment
     */
    public void insertNewCourse(Course course) {
        mCourseCodeList.add(course.getCourseCode());
//        for (String member : mCourseCodeList){
//            Log.i("Member name: ", member);
//        }
        mAdapter.notifyDataSetChanged();
        mDbHelper.insertCourse(course);

        Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
    }


    /**
     * Toolbar Item selection Handler
     * @param item in toolbar
     * @return true: to hold and exit, false: to fall through
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // get current fragment in activity
        Fragment currentFragment = getFragmentManager().findFragmentById(R.id.addCourseFrag);
        // if yes then call onOptionsItemSelected of fragment first
        if (currentFragment != null && currentFragment.onOptionsItemSelected(item)) {
            // onOptionsItemSelected of current fragment will return true is item is home
            return true;
        }

        // check for item selection in this Activity
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Set listener to Add course Floating Action Button
     * and display Add New Course Fragment
     */
    private void initAddCourseFab() {

        mAddCourseFab = findViewById(R.id.addCourseFab);
        mAddCourseFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if there are no previous fragments in back stack
                if (getFragmentManager().getBackStackEntryCount() == 0) {
                    // Create a new fragment
                    mAddCourseFragment = new AddCourseFragment();
                    // get transaction manager
                    FragmentManager manager = getFragmentManager();
                    // start transaction
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.add(R.id.addCourseFrag, mAddCourseFragment, "Add New Course Fragment");
                    // add this fragment to stack
                    transaction.addToBackStack("Add New Course Fragment");
                    // commit this transaction
                    transaction.commit();

                    // hide add new course fab
                    setFabHidden(true);
                }
            }
        });
    }


    /**
     * Configure Toolbar params
     * @param title for toolbar
     * @param ic_home icon to be displayed for home
     */
    public void initToolbar(String title, int ic_home) {
        // Add toolbar support
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        // add home button in toolbar
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setHomeAsUpIndicator(ic_home);
        // set title
        actionbar.setTitle(title);
        Log.d("Check init toolba", title);
    }


    /**
     * Set listener for Side Nav item selection
     */
    private void initSideNav() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // On selection, highlight it and quit the drawer
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }


    /**
     * Lock Side Nav
     * @param enabled true: lock, false: unlock
     */
    @Override
    public void setDrawerLocked(boolean enabled){
        // check if Drawer exists
        if (mDrawerLayout != null) {
            if(enabled){
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }else{
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
        }
    }


    /**
     * Hide and show Add Course FAB
     * @param enable true: hide, false: show
     */
    @Override
    public void setFabHidden(boolean enable){
        if (enable) {
            mAddCourseFab.hide();
        }
        else {
            mAddCourseFab.show();
        }
    }

    @Override
    public void setViewHidden(boolean enabled) {
        ListView l = findViewById(R.id.courses_list_view);

        if (enabled) {
            l.setVisibility(View.GONE);
        }
        else {
            l.setVisibility(View.VISIBLE);
        }
    }
}
