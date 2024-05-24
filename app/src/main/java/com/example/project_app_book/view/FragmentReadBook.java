package com.example.project_app_book.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project_app_book.R;


public class FragmentReadBook extends Fragment {


    public FragmentReadBook() {
        // Required empty public constructor
    }
    private static final String ARG_BOOK_ID = "book_id";
    private String bookId;
    private boolean isHeartSelected = false;
    private TextView tvReadBook;

    public static FragmentReadBook newInstance(String bookId) {
        FragmentReadBook fragment = new FragmentReadBook();
        Bundle args = new Bundle();
        args.putString(ARG_BOOK_ID, bookId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bookId = getArguments().getString(ARG_BOOK_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_read_book, container, false);

        androidx.appcompat.widget.Toolbar toolbar = view.findViewById(R.id.toolbar_fragment_detail);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_states);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the back navigation
                getParentFragmentManager().popBackStack();
            }
        });

        return view;
    }
}