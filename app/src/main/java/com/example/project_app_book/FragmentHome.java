package com.example.project_app_book;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;
import java.util.ArrayList;


public class FragmentHome extends Fragment {
    private ImageView imageViewAnh;
    private ArrayList<Book> listBook;
    private RecyclerView recycler_view;
    private RecyclerView recycler_view1;

    public FragmentHome() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        imageViewAnh = (ImageView) view.findViewById(R.id.imgBook);
        imageViewAnh.setImageResource(R.drawable.sach);
        recycler_view = view.findViewById(R.id.recycler_view);
        recycler_view1 = view.findViewById(R.id.recycler_view1);
        // Initialize the listBook ArrayList
        listBook = new ArrayList<>();
        listBook.add(new Book("sach"));
        listBook.add(new Book("sach1"));
        listBook.add(new Book("sach2"));
        listBook.add(new Book("sach1"));
        listBook.add(new Book("sach2"));

        // Set the adapter
        BookRecyclerAdapter bookRecyclerAdapter = new BookRecyclerAdapter(getContext(), listBook);
        recycler_view.setAdapter(bookRecyclerAdapter);
        recycler_view1.setAdapter(bookRecyclerAdapter);


        return view;
    }
}