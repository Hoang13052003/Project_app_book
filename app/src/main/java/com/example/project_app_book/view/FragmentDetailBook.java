package com.example.project_app_book.view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_app_book.R;

public class FragmentDetailBook extends Fragment {

    public FragmentDetailBook() {
        // Required empty public constructor
    }
    private static final String ARG_BOOK_ID = "book_id";
    private String bookId;

    public static FragmentDetailBook newInstance(String bookId) {
        FragmentDetailBook fragment = new FragmentDetailBook();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_book, container, false);
        @SuppressLint({"LocalSuppress", "MissingInflatedId"}) TextView tvdemo = view.findViewById(R.id.tvdemo);
        ImageView imageView = view.findViewById(R.id.imageView);

        @SuppressLint("DiscouragedApi") int resourceId = container.getResources().getIdentifier(bookId, "drawable", getContext().getPackageName());
        imageView.setImageResource(resourceId);
        tvdemo.setText(bookId);
        // Here, you can initialize your UI elements and load book details using bookId

        return view;
    }
}