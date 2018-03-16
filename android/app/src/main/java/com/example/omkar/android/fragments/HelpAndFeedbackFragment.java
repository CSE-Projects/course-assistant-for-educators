package com.example.omkar.android.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.omkar.android.CoursesActivity;
import com.example.omkar.android.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by omkar on 16-Mar-18.
 */

public class HelpAndFeedbackFragment extends Fragment{

    // view of fragment
    View view;
    private int mViewCheck;

    private FirebaseFirestore mFirestoreInstance;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // configure Toolbar in Courses Activity
        setHasOptionsMenu(true);
        ((CoursesActivity)getActivity()).setFabHidden(true);
        ((CoursesActivity)getActivity()).initToolbar("Help and Feedback", R.drawable.ic_menu);
        ((CoursesActivity)getActivity()).setViewHidden(true, R.color.white);

        mFirestoreInstance = FirebaseFirestore.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // TODO: Add connection check to internet
        // INTERNET CONNECTION CHECK NOT REQUIRED
        view =  inflater.inflate(R.layout.fragment_help_and_feedback, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final RatingBar ratingBar = view.findViewById(R.id.rating);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Log.d("RATING CHECK", "In");
            }
        });
    }


    /**
     * Send feedback gathering values from input fields
     */
    private void sendFeedback() {

        // get input values
        RatingBar ratingBar = view.findViewById(R.id.rating);
        EditText feedbackEdit = view.findViewById(R.id.feedbackEdit);
        EditText otherFeatureEdit = view.findViewById(R.id.otherFeatEdit);

        // store in map
        Map<String, Object> user = new HashMap<>();
        user.put("rating", String.valueOf(ratingBar.getRating()));
        user.put("feedback", feedbackEdit.getText().toString());
        user.put("suggestions",otherFeatureEdit.getText().toString());

        // Add a new document with a generated ID to Firebase Firestore
        mFirestoreInstance.collection("feedbacks")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("FEEDBACK CHECK", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("FEEDBACK CHECK", "Error adding document", e);
                    }
                });
    }


    /**
     * Inflate menu items into views
     * @param menu menu xml
     * @param inflater inflater obj
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_send, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    /**
     * Toolbar Item selection Handler
     * @param item in toolbar
     * @return true: to hold and exit, false: to fall through
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.send:
                sendFeedback();
                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // Reset Courses Activity Toolbar
        ((CoursesActivity)getActivity()).initToolbar("Courses", R.drawable.ic_menu);
        ((CoursesActivity)getActivity()).setFabHidden(false);
        ((CoursesActivity)getActivity()).setViewHidden(false, R.color.background);
    }
}
