package com.example.quiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class QuestionFragment extends Fragment {
    TextView  qFragment;
    static String qDescription;
    static int qColor;


//    public  QuestionFragment(){
//        super(R.layout.fragment_question);
//
//    }

    public static QuestionFragment newInstance(String d, int c){
        QuestionFragment qf = new QuestionFragment();
        qDescription = d;
        qColor = c;
        return qf;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        // Find your TextView
        qFragment = view.findViewById(R.id.tvFragmentQ);
        qFragment.setText(qDescription);

        view.setBackgroundColor(ContextCompat.getColor(requireContext(), qColor));

        return view;

    }
}
