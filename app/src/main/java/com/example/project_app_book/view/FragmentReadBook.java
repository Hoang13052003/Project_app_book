package com.example.project_app_book.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.project_app_book.R;

public class FragmentReadBook extends Fragment {

    private static final String ARG_CONTENT = "content";
    private TextView tvContent;

    public FragmentReadBook() {
        // Required empty public constructor
    }

    public static FragmentReadBook newInstance(String content) {
        FragmentReadBook fragment = new FragmentReadBook();
        Bundle args = new Bundle();
        args.putString(ARG_CONTENT, content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read_book, container, false);
        tvContent = view.findViewById(R.id.tvContent);

        // Lấy nội dung từ Bundle
        String content = getArguments().getString(ARG_CONTENT);

        // Hiển thị nội dung trong tvContent
        tvContent.setText(content);

        return view;
    }
}
