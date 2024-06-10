package com.example.project_app_book.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project_app_book.R;
import com.example.project_app_book.model.Author;
import com.example.project_app_book.model.Book;
import com.example.project_app_book.model.CategoryBook;
import com.example.project_app_book.model.User;
import com.example.project_app_book.view.adapter.AuthorRecyclerAdapter;
import com.example.project_app_book.view.adapter.BookRecyclerAdapter;
import com.example.project_app_book.view.adapter.BookRecyclerAdapterHorizental;
import com.example.project_app_book.view.adapter.BookThreeRowRecyclerAdapter;
import com.example.project_app_book.view.adapter.CategoryBookAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;


public class FragmentSearch extends Fragment {
    private RecyclerView recycler_view_search;
    private EditText edtSearch;
    private ArrayList<Book> listBook = new ArrayList<>();
    private ArrayList<Book> filteredListBook = new ArrayList<>();
    BookRecyclerAdapterHorizental bookRecyclerAdapterNewBook;

    private User user;
    public FragmentSearch() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        addControls(view);
        Intent intent = getActivity().getIntent();
        user = (User) intent.getSerializableExtra("loggedInUser");
        addEvents(view);
        return view;
    }
    private void addControls(View view){
        recycler_view_search = view.findViewById(R.id.recycler_view_search);
        edtSearch = view.findViewById(R.id.edtSearch);
        recycler_view_search.setLayoutManager(new LinearLayoutManager(getContext()));
        bookRecyclerAdapterNewBook = new BookRecyclerAdapterHorizental(getContext(), filteredListBook, R.layout.layout_item_colum_book_search);
        recycler_view_search.setAdapter(bookRecyclerAdapterNewBook);
    }
    private void addEvents(View view){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        // Tạo các task lấy dữ liệu từ Firebase
        Task<DataSnapshot> booksTask = databaseReference.child("books").get();

        Tasks.whenAll(booksTask).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Lấy dữ liệu sách
                    DataSnapshot booksSnapshot = booksTask.getResult();
                    listBook.clear();
                    for (DataSnapshot snapshot : booksSnapshot.getChildren()) {

                        Book book = snapshot.getValue(Book.class);
                        if (book != null) {
                            book.setBookID(snapshot.getKey());
                            listBook.add(book);
                        }
                    }
                    filteredListBook.addAll(listBook);
                    bookRecyclerAdapterNewBook.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "Failed to load data.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        bookRecyclerAdapterNewBook.setOnItemClickListener(new BookRecyclerAdapterHorizental.OnItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                loadFragment(new FragmentDetailBook(book));
            }
        });
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterBooks(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Do nothing
            }
        });
    }

    private void filterBooks(String query) {
        filteredListBook.clear();
        if (query.isEmpty()) {
            filteredListBook.addAll(listBook);
        } else {
            for (Book book : listBook) {
                if (book.getTitle() != null && book.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredListBook.add(book);
                }
            }
        }
        bookRecyclerAdapterNewBook.notifyDataSetChanged();
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