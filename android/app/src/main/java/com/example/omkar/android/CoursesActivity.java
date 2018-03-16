package com.example.omkar.android;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.omkar.android.adapters.CoursesAdapter;
import com.example.omkar.android.fragments.AddCourseFragment;
import com.example.omkar.android.fragments.DocumentSimilarityFragment;
import com.example.omkar.android.fragments.HelpAndFeedbackFragment;
import com.example.omkar.android.helpers.DatabaseHelper;
import com.example.omkar.android.interfaces.CoursesViewInterface;
import com.example.omkar.android.models.Course;

import java.util.ArrayList;

public class CoursesActivity extends AppCompatActivity implements CoursesViewInterface {

    private DrawerLayout mDrawerLayout;
    private FloatingActionButton mAddCourseFab;
    private Class mCurrentFragmentClass;
    private DatabaseHelper mDbHelper;
//    private RecyclerView mRecyclerView;

    private ArrayList<String[]> mCourseCodeList;
    private CoursesAdapter mCoursesAdapter;
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
        // course code list
        mCourseCodeList = new ArrayList<>();

        // get course column from db
        mDbHelper = DatabaseHelper.getInstance(this);
        Cursor c = mDbHelper.getCourseInfo();

        // iterate through column elements
        if (c.moveToFirst()){
            do {
                // add to list
                mCourseCodeList.add(new String[]{c.getString(0), c.getString(1), c.getString(2), c.getString(3)});
            } while(c.moveToNext());
        }
        c.close();

        // set custom adapter and add to list view
        mCoursesAdapter = new CoursesAdapter(this, mCourseCodeList);
        mListView = findViewById(R.id.courses_list_view);
        mListView.setAdapter(mCoursesAdapter);

        // listener for click events on list view
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // set intent to Course Activity
                Intent courseIntent = new Intent(CoursesActivity.this, CourseActivity.class);
                // get course code of item clicked
                String[] courseInfo = mCourseCodeList.get(position);

                // send course with intent
                courseIntent.putExtra("courseCode", courseInfo[0]);
                courseIntent.putExtra("courseName", courseInfo[1]);
                courseIntent.putExtra("emailCr", courseInfo[2]);
                courseIntent.putExtra("emailTa", courseInfo[3]);
                startActivity(courseIntent);
            }
        });
    }


    /**
     * Insert new course into database and list
     * @param course passed from fragment
     */
    public void insertNewCourse(Course course) {
        // add course code and name to the list
        mCourseCodeList.add(new String[]{course.getCourseCode(), course.getCourseName()});
//        for (String member : mCourseCodeList){
//            Log.i("Member name: ", member);
//        }
        // notify adapter that list has changed
        mCoursesAdapter.notifyDataSetChanged();
        // insert course in database
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
        Fragment currentFragment = getFragmentManager().findFragmentByTag("Add New Course Fragment");
        // if yes then call onOptionsItemSelected of fragment first
        if (currentFragment != null && currentFragment.onOptionsItemSelected(item)) {
            // onOptionsItemSelected of current fragment will return true is item is home
            return true;
        }

        // check for item selection in Toolbar
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
                    AddCourseFragment mAddCourseFragment = new AddCourseFragment();
                    // get transaction manager
                    FragmentManager manager = getFragmentManager();
                    // start transaction
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.add(R.id.fragContainer, mAddCourseFragment, "Add New Course Fragment");
                    // add this fragment to stack
                    transaction.addToBackStack("Add New Course Fragment");
                    // commit this transaction
                    transaction.commit();

                    // set current fragment class as Add Course Fragment
                    mCurrentFragmentClass = AddCourseFragment.class;
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
        Log.d("Check init toolbar", title);
    }


    /**
     * Set listener for Side Nav item selection
     */
    private void initSideNav() {
        // set listener
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;

                    }
                });
    }


    /**
     * Handler for nav drawer selection
     * @param menuItem item clicked in nav drawer
     */
    public void selectDrawerItem(MenuItem menuItem) {
        removeAllFragments();

        mCurrentFragmentClass = null;
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;

        // select
        switch(menuItem.getItemId()) {
            case R.id.home:
                // On selection, highlight it and quit the drawer
                menuItem.setChecked(true);
                setFabHidden(false);
                mDrawerLayout.closeDrawers();
                return;
            case R.id.similarity_check:
                mCurrentFragmentClass = DocumentSimilarityFragment.class;
                break;
            case R.id.help_feedback:
                mCurrentFragmentClass = HelpAndFeedbackFragment.class;
                break;
            default:
                return;
        }

        // make fragment object from class
        try {
            fragment = (Fragment) mCurrentFragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawerLayout.closeDrawers();
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


    /**
     * Hide view of Courses Activity and set background color
     * @param enabled true: hide, false: show
     * @param color set background color
     */
    @Override
    public void setViewHidden(boolean enabled, int color) {
        ListView l = findViewById(R.id.courses_list_view);
        mDrawerLayout.setBackgroundColor(getResources().getColor(color));
        if (enabled) {
            l.setVisibility(View.GONE);
        }
        else {
            l.setVisibility(View.VISIBLE);
        }
    }


    /**
     * Remove all fragments from back stack
     */
    private void removeAllFragments () {
        // remove all fragments
        FragmentManager fragmentManager = getFragmentManager();
        for(int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
            fragmentManager.popBackStack();
        }
    }


    /**
     * Decide navigation on press of back button
     */
    @Override
    public void onBackPressed() {

        // if fragments are present
        if (mCurrentFragmentClass != null) {
            Log.d("Check back", "fRAG");
            // remove all fragments in stack
            removeAllFragments();
            // no current fragment
            mCurrentFragmentClass = null;
            // select home item in nav drawer and check it
            NavigationView navigation = findViewById(R.id.nav_view);
            Menu drawer_menu = navigation.getMenu();
            MenuItem menuItem;
            menuItem = drawer_menu.findItem(R.id.home);
            if(!menuItem.isChecked())
            {
                menuItem.setChecked(true);
            }
        }
        else {
            Log.d("Check back", "SUPER");
            super.onBackPressed();
        }
    }
}
