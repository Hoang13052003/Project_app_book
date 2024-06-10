package com.example.project_app_book.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentDanhSachTheLoai extends Fragment {

    private RecyclerView recycle_view_categories;
    private ArrayList<Book> listBook = new ArrayList<>();
    private HashMap<String, String> authorMap;
    BookThreeRowRecyclerAdapter bookThreeRowRecyclerAdapter;

    Toolbar toolbar;

    User user;
    public FragmentDanhSachTheLoai() {
        // Required empty public constructorA
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_danh_sach_the_loai, container, false);
        //lấy thông tin tác giá từ database realtime: ID và Tên Tác Giả
        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        authorMap = sharedViewModel.getAuthorMap().getValue();
        addControls(view);

        String category = getArguments().getString("category");
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("loggedInUser");
        }
        Intent intent = getActivity().getIntent(); // Get intent from hosting activity
        user = (User) intent.getSerializableExtra("loggedInUser");
        Toast.makeText(getContext(), category, Toast.LENGTH_SHORT);


        // Lấy danh sách sách theo thể loại đã chọn
        getBooksByCategory(category);
        addEvents(view);
        return view;
    }

    public void addControls(View view)
    {
        recycle_view_categories = view.findViewById(R.id.recycler_view_category);
//        bookThreeRowRecyclerAdapter = new BookThreeRowRecyclerAdapter(getContext(), listBook, R.layout.layout_item_colum_book_favourite);
        bookThreeRowRecyclerAdapter = new BookThreeRowRecyclerAdapter(getContext(), listBook, R.layout.layout_item_colum_book_favourite,authorMap);
        recycle_view_categories.setAdapter(bookThreeRowRecyclerAdapter);
    }

    public void addEvents(View view)
    {
        bookThreeRowRecyclerAdapter.setOnItemClickListener(new BookThreeRowRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                loadFragment(new FragmentDetailBook(book));
            }
        });

        toolbar = view.findViewById(R.id.toolbar_fragment_category);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_states);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });

    }




    public void getBooksByCategory(String category) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("books");
        Query query = databaseReference.orderByChild("categoryId").equalTo(category);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listBook.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Book book = snapshot.getValue(Book.class);
                    listBook.add(book);
                }
                // Cập nhật adapter sau khi dữ liệu thay đổi
                bookThreeRowRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi
            }
        });
    }

    private void loadFragment(Fragment fragment){
        // Thực hiện việc chuyển đổi fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("loggedInUser", user);
        fragment.setArguments(bundle);
        transaction.replace(R.id.fragLayoutLoad, fragment); // fragment_container là id của FrameLayout trong activity_main.xml
        transaction.addToBackStack(null); // thêm transaction vào back stack để có thể quay lại fragment trước đó
        transaction.commit();
    }
}