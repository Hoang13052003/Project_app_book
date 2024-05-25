package com.example.project_app_book.view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_app_book.R;
import com.example.project_app_book.model.AnimationUtil;
import com.example.project_app_book.model.Book;

public class FragmentDetailBook extends Fragment {



    private Book book;
    private boolean isHeartSelected = false;
    private ImageView imgAvatarBook, ivHeart ;
    private TextView tvNameBook, tvReadBook;
    public FragmentDetailBook() {
        // Required empty public constructor
    }
    public FragmentDetailBook(Book item) {
        this.book = item;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_book, container, false);
//        @SuppressLint({"LocalSuppress", "MissingInflatedId"}) TextView tvdemo = view.findViewById(R.id.tvdemo);
        ImageView imageView = view.findViewById(R.id.imgAvatarBook);

        @SuppressLint("DiscouragedApi") int resourceId = container.getResources().getIdentifier(book.getImage(), "drawable", getContext().getPackageName());
        imageView.setImageResource(resourceId);










        addControls(view);
        addEvents(container, view);
        return view;
    }
    private void addControls(View view){
        ivHeart = view.findViewById(R.id.imgYeuThich);
        tvReadBook = view.findViewById(R.id.tvReadBook);

        imgAvatarBook = view.findViewById(R.id.imgAvatarBook);
        tvNameBook = view.findViewById(R.id.tvNameBook);

    }
    private void addEvents(ViewGroup container,View view){
        @SuppressLint("DiscouragedApi") int resourceId = container.getResources().getIdentifier(book.getImage(), "drawable", getContext().getPackageName());
        imgAvatarBook.setImageResource(resourceId);
        tvNameBook.setText(book.getTitle());

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

        tvReadBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationUtil.applyScaleAnimation(getContext(), tvReadBook, new AnimationUtil.AnimationListener() {
                    @Override
                    public void onAnimationEnd() {
                        // Sau khi hoàn thành animation, chuyển đổi fragment
                        FragmentReadBook fragmentReadBook = new FragmentReadBook(book, 1);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragLayoutLoad, fragmentReadBook);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
            }
        });

        androidx.appcompat.widget.Toolbar toolbar = view.findViewById(R.id.toolbar_fragment_detail);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_states);
        if(book.getTitle().length()>15){
            toolbar.setTitle(book.getTitle().substring(0, 15)+"...");
        }else {
            toolbar.setTitle(book.getTitle());
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the back navigation
                getParentFragmentManager().popBackStack();
            }
        });
    }

}