package com.example.project_app_book.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_app_book.R;
import com.example.project_app_book.model.AnimationUtil;
import com.example.project_app_book.model.Book;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentDetailBook extends Fragment {

    private Book book;
    private boolean isHeartSelected = false;
    private ImageView imgAvatarBook, ivHeart;
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
        ImageView imageView = view.findViewById(R.id.imgAvatarBook);
        @SuppressLint("DiscouragedApi")
        int resourceId = container.getResources().getIdentifier(book.getImage(), "drawable", getContext().getPackageName());
        imageView.setImageResource(resourceId);

        addControls(view);
        addEvents(container, view);
        checkFavoriteStatus();
        return view;
    }

    private void addControls(View view) {
        ivHeart = view.findViewById(R.id.imgYeuThich);
        tvReadBook = view.findViewById(R.id.tvReadBook);
        imgAvatarBook = view.findViewById(R.id.imgAvatarBook);
        tvNameBook = view.findViewById(R.id.tvNameBook);
    }

    private void addEvents(ViewGroup container, View view) {
        @SuppressLint("DiscouragedApi")
        int resourceId = container.getResources().getIdentifier(book.getImage(), "drawable", getContext().getPackageName());
        imgAvatarBook.setImageResource(resourceId);
        tvNameBook.setText(book.getTitle());

        ivHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavoriteStatus();
            }
        });

        tvReadBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationUtil.applyScaleAnimation(getContext(), tvReadBook, new AnimationUtil.AnimationListener() {
                    @Override
                    public void onAnimationEnd() {
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
        if (book.getTitle().length() > 15) {
            toolbar.setTitle(book.getTitle().substring(0, 15) + "...");
        } else {
            toolbar.setTitle(book.getTitle());
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });
    }

    private void checkFavoriteStatus() {
        // Ensure bookID is not null
        if (book == null) {
            Toast.makeText(getContext(), "Invalid book data in check", Toast.LENGTH_SHORT).show();
            return;
        }

//        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String userId = "user1";
        DatabaseReference favoritesRef = FirebaseDatabase.getInstance().getReference("user").child(userId).child("favourite");

        favoritesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String key = childSnapshot.getKey();
                    if (key != null && key.equals(book.getBookID())) {
                        isHeartSelected = true;
                        break;
                    }
                }
                ivHeart.setImageResource(isHeartSelected ? R.drawable.ic_heart_states : R.drawable.ic_heart);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    private void toggleFavoriteStatus() {
        // Ensure bookID is not null
        if (book == null || book.getBookID() == null) {
            Toast.makeText(getContext(), "Invalid book data in toggle", Toast.LENGTH_SHORT).show();
            return;
        }
//        FirebaseAuth.getInstance().getCurrentUser().getUid();
//        String userId =
        String userId = "user1";
        DatabaseReference favoritesRef = FirebaseDatabase.getInstance().getReference("user").child(userId).child("favourite");

        if (isHeartSelected) {
            favoritesRef.child(book.getBookID()).removeValue().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    isHeartSelected = false;
                    ivHeart.setImageResource(R.drawable.ic_heart);
                    Toast.makeText(getContext(), "Đã xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            favoritesRef.child(book.getBookID()).setValue(true).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    isHeartSelected = true;
                    ivHeart.setImageResource(R.drawable.ic_heart_states);
                    Toast.makeText(getContext(), "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
