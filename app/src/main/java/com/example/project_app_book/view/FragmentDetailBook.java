package com.example.project_app_book.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_app_book.R;

public class FragmentDetailBook extends Fragment {

    public FragmentDetailBook() {
        // Required empty public constructor
    }


//    public static FragmentDetailBook newInstance(String param1, String param2) {
//        FragmentDetailBook fragment = new FragmentDetailBook();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_book, container, false);
    }
}