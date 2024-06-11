package com.example.project_app_book.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.project_app_book.R;
import com.example.project_app_book.model.Author;
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

public class FragmentSachTheoTacGia extends Fragment {

    private User user;
    private RecyclerView recycler_view_author;
    BookThreeRowRecyclerAdapter bookThreeRowRecyclerAdapter;
    private ArrayList<Book> listBook = new ArrayList<>();
    private HashMap<String, String> authorMap;
    private Author author;
    Toolbar toolbar;
    public FragmentSachTheoTacGia() {
        // Required empty public constructor
    }

    public FragmentSachTheoTacGia(Author author)
    {
        this.author = author;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sach_theo_tac_gia,container,false);

        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        authorMap = sharedViewModel.getAuthorMap().getValue();

        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("loggedInUser");
        }
        Intent intent = getActivity().getIntent(); // Get intent from hosting activity
        user = (User) intent.getSerializableExtra("loggedInUser");


        addControls(view);

        getBooksByCategory(author.getAuthorID());
        addEvents(view);
        return view;
    }


    public void addControls(View view)
    {
        recycler_view_author = view.findViewById(R.id.recycler_view_author);
        bookThreeRowRecyclerAdapter = new BookThreeRowRecyclerAdapter(getContext(), listBook, R.layout.layout_item_colum_book_favourite ,authorMap);
        recycler_view_author.setAdapter(bookThreeRowRecyclerAdapter);
    }


    public void getBooksByCategory(String authorID) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("books");
        Query query = databaseReference.orderByChild("authorId").equalTo(authorID);
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
    public void addEvents(View view)
    {
        bookThreeRowRecyclerAdapter.setOnItemClickListener(new BookThreeRowRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                loadFragment(new FragmentDetailBook(book));
            }
        });

        toolbar = view.findViewById(R.id.toolbar_fragment_author);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_states);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
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