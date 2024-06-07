package com.example.project_app_book.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.project_app_book.R;
import com.example.project_app_book.model.AnimationUtil;
import com.example.project_app_book.model.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FragmentDetailBook extends Fragment {

    private Book book;
    private boolean isHeartSelected = false;
    private HashMap<String, String> authorMap;
    private ImageView imgAvatarBook, ivHeart;
    private TextView tvNameBook, tvReadBook, tvAuthorName;
    private ScrollView scrollView;
    private LinearLayout linear;
    private OkHttpClient client;

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

        // Khởi tạo client ở đây
        client = new OkHttpClient();

        @SuppressLint("DiscouragedApi")
        int resourceId = container.getResources().getIdentifier(book.getImage(), "drawable", getContext().getPackageName());
        imageView.setImageResource(resourceId);

        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        authorMap = sharedViewModel.getAuthorMap().getValue();

        addControls(view);
        addEvents(container, view);
        checkFavoriteStatus();
        fetchBookContent("https://www.gutenberg.org/files/2701/2701-0.txt");

        return view;
    }


    private void addControls(View view) {
        ivHeart = view.findViewById(R.id.imgYeuThich);
        tvReadBook = view.findViewById(R.id.tvReadBook);
        imgAvatarBook = view.findViewById(R.id.imgAvatarBook);
        tvNameBook = view.findViewById(R.id.tvNameBook);
        tvAuthorName = view.findViewById(R.id.tvNameAuthor);
        scrollView = view.findViewById(R.id.scrollview_detail);
        linear = view.findViewById(R.id.linear);
    }

    public String[] splitString(String originalString, int maxLength) {
        int arrayLength = (int) Math.ceil((double) originalString.length() / maxLength);
        String[] result = new String[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            int start = i * maxLength;
            int end = Math.min((i + 1) * maxLength, originalString.length());
            result[i] = originalString.substring(start, end);
        }

        return result;
    }

    private void fetchBookContent(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String content = response.body().string();
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String[] newa = splitString(content, 5000);
                            Toast.makeText(getContext(), String.valueOf(newa.length), Toast.LENGTH_SHORT).show();

                            Button[] btnWord = new Button[newa.length];
                            for (int i = 0; i < newa.length; i++) {
                                btnWord[i] = new Button(getContext());
                                btnWord[i].setHeight(50);
                                btnWord[i].setWidth(50);
                                btnWord[i].setTag(i);
                                btnWord[i].setText("Part " + (i + 1));
                                linear.addView(btnWord[i]);

                                int finalI = i;
                                btnWord[i].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        openContentFragment(newa[finalI]);
                                    }
                                });
                            }
                        }
                    });
                } else {
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView tvNoiDung = getView().findViewById(R.id.tvNoiDung);
                            tvNoiDung.setText("Failed to fetch content. Response code: " + response.code());
                        }
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void addEvents(ViewGroup container, View view) {
        @SuppressLint("DiscouragedApi")
        int resourceId = container.getResources().getIdentifier(book.getImage(), "drawable", getContext().getPackageName());
        imgAvatarBook.setImageResource(resourceId);
        tvNameBook.setText(book.getTitle());
        tvAuthorName.setText(authorMap.get(book.getAuthorId()));

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
//                        FragmentReadBook fragmentReadBook = new FragmentReadBook(book, 1);
//                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                        transaction.replace(R.id.fragLayoutLoad, fragmentReadBook);
//                        transaction.addToBackStack(null);
//                        transaction.commit();
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

    private void openContentFragment(String content) {
        FragmentReadBook fragmentContent = FragmentReadBook.newInstance(content);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragLayoutLoad, fragmentContent);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
