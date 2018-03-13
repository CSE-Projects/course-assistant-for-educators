package com.example.omkar.android;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.omkar.android.fragments.AddCourseFragment;
import com.example.omkar.android.interfaces.CoursesViewInterface;

public class CoursesActivity extends AppCompatActivity implements CoursesViewInterface {


    private DrawerLayout mDrawerLayout;
    private FloatingActionButton mAddCourseFab;
    private AddCourseFragment addCourseFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        // Drawer Layout instantiated
        mDrawerLayout = findViewById(R.id.drawer_layout);

        // initialize views
        initToolbar("Courses", R.drawable.ic_menu);
        initSideNav();
        initAddCourseFab();
    }


    /**
     * Toolbar Item selection Handler
     * @param item in toolbar
     * @return true: to hold and exit, false: to fall through
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        // check if Add Course fragment is present
//        Fragment currentFragment = CoursesActivity.this.getSupportFragmentManager().findFragmentById(R.id.addCourseFrag);
//
//        // call onOptionsItemSelected of fragment first
        // TODO: Call the fragment onOptionsItemSelect first

//        if(addCourseFragment != null && addCourseFragment.onOptionsItemSelected(item)){
//            return true;
//        }

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

                // TODO: Add condition for checking whether a fragment already exits

                // Create a new fragment
                addCourseFragment = new AddCourseFragment();
                // get transaction manager
                FragmentManager manager = getFragmentManager();
                // start transaction
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.addCourseFrag, addCourseFragment, "Add New Course Fragment");
                // add this fragment to stack
                transaction.addToBackStack("Add New Course Fragment");
                // commit this transaction
                transaction.commit();

                // hide add new course fab
                setFabHidden(true);
            }
        });
    }


    /**
     * Configure Toolbar params
     * @param title for toolbar
     * @param ic_home icon to be displayed for home
     */
    public void initToolbar(String title, int ic_home) {
        // set title
        setTitle(title);
        // Add toolbar support
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        // add home button in toolbar
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setHomeAsUpIndicator(ic_home);
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
        if(enable) {
            mAddCourseFab.hide();
        }
        else {
            mAddCourseFab.show();
        }
    }
}
