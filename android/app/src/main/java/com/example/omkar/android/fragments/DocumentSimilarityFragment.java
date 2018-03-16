package com.example.omkar.android.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.omkar.android.CoursesActivity;
import com.example.omkar.android.R;

import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by omkar on 14-Mar-18.
 */

public class DocumentSimilarityFragment extends Fragment {

    // view of fragment
    View view;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // configure Toolbar in Courses Activity
        ((CoursesActivity)getActivity()).setFabHidden(true);
        ((CoursesActivity)getActivity()).initToolbar("Document Similarity", R.drawable.ic_menu);
        ((CoursesActivity)getActivity()).setViewHidden(true, R.color.white);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_document_similarity, container, false);

        Button button = view.findViewById(R.id.similarity_button);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                // Fetching the two strings
                EditText et1 = view.findViewById(R.id.doc1);
                EditText et2 = view.findViewById(R.id.doc2);

                String s1 = et1.getText().toString();
                String s2 = et2.getText().toString();

                // Calculating the result
                double result = cosine_algorithm(s1, s2);

                // Displaying the result
                TextView mytxt=(TextView) view.findViewById(R.id.display_similarity);
                mytxt.setText("Result: "+String.valueOf(result)+"%");

            }
        });

        return view;

    }


    public double cosine_algorithm(String s1, String s2) {

        // Two hash maps created for cosine similarity algorithm
        HashMap<String, Integer> map1 = new HashMap<String, Integer>();
        HashMap<String, Integer> map2 = new HashMap<String, Integer>();
        String[] splitS1 = s1.trim().split("\\s+");
        String[] splitS2 = s2.trim().split("\\s+");

        // Splitting the strings into constituents
        for( int i = 0 ; i < splitS1.length ; ++i){
            if(map1.get(splitS1[i])==null){
                map1.put(splitS1[i], 1);
            }
            else{
                map1.put(splitS1[i], map1.get(splitS1[i])+1);
            }
        }

        for( int i = 0 ; i < splitS2.length ; ++i){
            if(map2.get(splitS2[i])==null){
                map2.put(splitS2[i], 1);
            }
            else{
                map2.put(splitS2[i], map2.get(splitS2[i])+1);
            }
        }

        int numerator=0,mod1=0,mod2=0;

        // Calculating the sum of roots for all the keys
        // Iterating on one of the hash maps and finding numerator
        Iterator it = map1.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            mod1=mod1+(int)pair.getValue()*(int)pair.getValue();
            if(map2.get(pair.getKey())!=null){
                numerator=numerator+(int)pair.getValue()*map2.get(pair.getKey());
            }
            it.remove();
        }


        it = map2.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            mod2=mod2+(int)pair.getValue()*(int)pair.getValue();
        }

        // Calculating the final result
        double result=(double)numerator/(Math.sqrt(mod1)*Math.sqrt(mod2));
        result = result*(double)100;

        int upper = (int) Math.ceil(result);
        int lower = (int) Math.floor(result);

        // Ceiling or flooring in case of integral results
        double precision=1e-6;
        if(upper-result<precision){
            return upper;
        }
        if(result-lower<precision){
            return lower;
        }

        return result;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
