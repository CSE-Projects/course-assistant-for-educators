package com.example.omkar.android;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    public static final int RC_SIGN_IN = 123;
    private boolean ERROR_PAGE_DISPLAYED = false;

    // Firebase Auth Object
    private FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Auth objects initialized
        mFirebaseAuth = FirebaseAuth.getInstance();

        // check for authentication
        checkAuth();
    }


    /**
     * Authenticate user
     */
    private void checkAuth() {

        // check if user is Authenticated
        if (mFirebaseAuth.getCurrentUser() != null) {
            // already signed in
            onSignedIn();
        }
        else {
            // not signed in

            // check internet connectivity
            if (!isInternetAvailable()) {

                Toast.makeText(this, "Not Connected to Internet", Toast.LENGTH_SHORT).show();

                // load Error page once
                if (!ERROR_PAGE_DISPLAYED) {
                    // check internet connection layout
                    setContentView(R.layout.activity_main);
                    //getSupportActionBar().hide();
                    // set listener on button to call checkAuth
                    final Button checkStatus = findViewById(R.id.checkStatus);
                    checkStatus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            checkAuth();
                        }
                    });
                    ERROR_PAGE_DISPLAYED = true;
                }
                return;
            }

            // start FirebaseUI Auth sign-in activity
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setTheme(R.style.LoginTheme)
                            .build(),
                    RC_SIGN_IN);
        }
    }


    /**
     * Handle cancelled sign in and displaying UI after signing in
     * @param requestCode passed from FirebaseUI sign in activity
     * @param resultCode passed from FirebaseUI sign in activity
     * @param data passed from FirebaseUI sign in activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            if (resultCode == RESULT_OK){
                // sign up successful
                Toast.makeText(MainActivity.this, "Signed in!", Toast.LENGTH_SHORT).show();
                onSignedIn();
            }
            else if (resultCode == RESULT_CANCELED){
                Toast.makeText(this, "Sign in cancelled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

    }


    /**
     * Check network connectivity
     * @return true if network connectivity, else false
     */
    private boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    /**
     * Start Courses Activity on Sign In
     */
    private void onSignedIn() {
        Toast.makeText(MainActivity.this, "Signed in!", Toast.LENGTH_SHORT).show();
        Intent launchCoursesActivity = new Intent(MainActivity.this, CoursesActivity.class);
        startActivity(launchCoursesActivity);
        Log.d("Activity", "Finished");
        finish();
    }
}
