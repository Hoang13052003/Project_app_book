package com.example.project_app_book.view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.project_app_book.R;

public class FragmentDetailBook extends Fragment {

    public FragmentDetailBook() {
        // Required empty public constructor
    }
    private static final String ARG_BOOK_ID = "book_id";
    private String bookId;
    private boolean isHeartSelected = false;
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

        View view = inflater.inflate(R.layout.fragment_detail_book, container, false);
//        @SuppressLint({"LocalSuppress", "MissingInflatedId"}) TextView tvdemo = view.findViewById(R.id.tvdemo);
        ImageView imageView = view.findViewById(R.id.imgAvatarBook);

        @SuppressLint("DiscouragedApi") int resourceId = container.getResources().getIdentifier(bookId, "drawable", getContext().getPackageName());
        imageView.setImageResource(resourceId);

//        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                // Custom back action if needed
//                getParentFragmentManager().popBackStack(); // Pop the back stack to go to the previous fragment
//            }
//        });




        final ImageView ivHeart = view.findViewById(R.id.imgYeuThich);
        ivHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isHeartSelected = !isHeartSelected;
                if (isHeartSelected) {
                    ivHeart.setImageResource(R.drawable.ic_heart_states);
                } else {
                    ivHeart.setImageResource(R.drawable.ic_heart);
                }
            }
        });

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