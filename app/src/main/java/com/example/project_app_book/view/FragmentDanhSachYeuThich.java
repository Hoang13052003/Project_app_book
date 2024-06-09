package com.example.project_app_book.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_app_book.R;
import com.example.project_app_book.model.Book;
import com.example.project_app_book.model.User;
import com.example.project_app_book.view.adapter.BookThreeRowRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentDanhSachYeuThich extends Fragment {

    private RecyclerView recycler_view_favourite;
    private ArrayList<Book> listBook = new ArrayList<>();
    BookThreeRowRecyclerAdapter bookRecyclerAdapterNewBook;
    private HashMap<String, String> authorMap;
    private DatabaseReference userFavoritesRef;
    private DatabaseReference booksRef;

    public FragmentDanhSachYeuThich() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danh_sach_yeu_thich, container, false);
        //lấy thông tin tác giá từ database realtime: ID và Tên Tác Giả
        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        authorMap = sharedViewModel.getAuthorMap().getValue();

        addControls(view);

        User user = (User)getArguments().getSerializable("loggedInUser");

//        String userId = "user1";
        userFavoritesRef = FirebaseDatabase.getInstance().getReference("user").child(user.getUserID()).child("favourite");
        booksRef = FirebaseDatabase.getInstance().getReference("books");

        addEvents(view);

        loadFavoriteBooks();

        return view;
    }

    private void addControls(View view){
        recycler_view_favourite = view.findViewById(R.id.recycler_view_favourite);

        bookRecyclerAdapterNewBook = new BookThreeRowRecyclerAdapter(getContext(), listBook, R.layout.layout_item_colum_book_favourite, authorMap);


        recycler_view_favourite.setAdapter(bookRecyclerAdapterNewBook);

    }
    private void addEvents(View view){

        bookRecyclerAdapterNewBook.setOnItemClickListener(new BookThreeRowRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                loadFragment(new FragmentDetailBook(book));
            }
        });

        androidx.appcompat.widget.Toolbar toolbar = view.findViewById(R.id.toolbar_fragment_favourite);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_states);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });
    }

    private void loadFavoriteBooks() {
        userFavoritesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listBook.clear();
                for (DataSnapshot favSnapshot : snapshot.getChildren()) {
                    String bookId = favSnapshot.getKey();
                    loadBookDetails(bookId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }

    private void loadBookDetails(String bookId) {
        booksRef.child(bookId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Book book = snapshot.getValue(Book.class);
                if (book != null) {
                    listBook.add(book);
                    bookRecyclerAdapterNewBook.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }

    private void loadFragment(Fragment fragment){
        // Thực hiện việc chuyển đổi fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragLayoutLoad, fragment); // fragment_container là id của FrameLayout trong activity_main.xml
        transaction.addToBackStack(null); // thêm transaction vào back stack để có thể quay lại fragment trước đó
        transaction.commit();
    }
}
