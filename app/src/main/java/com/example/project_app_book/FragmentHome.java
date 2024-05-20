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
    private RecyclerView recycler_view_Moi_XB, recycler_view2, recycler_view3;

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
        recycler_view_Moi_XB = view.findViewById(R.id.recycler_view_Moi_XB);
        recycler_view2 = view.findViewById(R.id.recycler_view2);
        recycler_view3 = view.findViewById(R.id.recycler_view3);
        // Initialize the listBook ArrayList
        listBook = new ArrayList<>();
        listBook.add(new Book("sach"));
        listBook.add(new Book("sach1"));
        listBook.add(new Book("sach2"));
        listBook.add(new Book("sach1"));
        listBook.add(new Book("sach2"));

        // Set the adapter
//        recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        BookRecyclerAdapter bookRecyclerAdapter = new BookRecyclerAdapter(getActivity(), listBook, R.layout.layout_item_colum_book);
        BookRecyclerAdapter bookRecyclerAdapterTopBook = new BookRecyclerAdapter(getActivity(), listBook, R.layout.layout_item_colum_book_popular);
        BookRecyclerAdapter bookRecyclerAdapterNewBook = new BookRecyclerAdapter(getActivity(), listBook, R.layout.layout_item_colum_book_3_row);
        recycler_view.setAdapter(bookRecyclerAdapterTopBook);
        recycler_view_Moi_XB.setAdapter(bookRecyclerAdapterNewBook);
        recycler_view2.setAdapter(bookRecyclerAdapter);
        recycler_view3.setAdapter(bookRecyclerAdapter);


        return view;
    }
}