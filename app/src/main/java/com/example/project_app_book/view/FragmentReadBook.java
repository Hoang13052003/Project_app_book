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
import com.example.project_app_book.model.Book;


public class FragmentReadBook extends Fragment {

    private Book book;
    private int idChapter;

    private ImageView imgAvatarBook ;
    private TextView tvNameBook ;

    public FragmentReadBook() {
        // Required empty public constructor
    }
    public FragmentReadBook(Book book, int idChapter) {
        this.book = book;
        this.idChapter = idChapter;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_read_book, container, false);

        addControls(view);
        addEvents(container, view);

        return view;
    }
    private void addControls(View view){
        imgAvatarBook = view.findViewById(R.id.imgAvatarBook);
        tvNameBook = view.findViewById(R.id.tvNameBook);

    }
    private void addEvents(ViewGroup container,View view){
        @SuppressLint("DiscouragedApi") int resourceId = container.getResources().getIdentifier(book.getImage(), "drawable", getContext().getPackageName());
        imgAvatarBook.setImageResource(resourceId);
        tvNameBook.setText(book.getTitle());

        androidx.appcompat.widget.Toolbar toolbar = view.findViewById(R.id.toolbar_fragment_read_book);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_states);
        toolbar.setTitle(book.getTitle());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the back navigation
                getParentFragmentManager().popBackStack();
            }
        });
    }

}