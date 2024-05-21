package com.example.project_app_book;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class FragmentHome extends Fragment {
    private ImageView imageViewAnh;
    private ArrayList<Book> listBook;
    private ArrayList<CategoryBook> listCategoryBook;
    private RecyclerView recycler_view, recycler_view_Moi_XB, recycler_view2, recycler_view3;

    private ListView lvCategoryBook;

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

        lvCategoryBook = view.findViewById(R.id.lvCategoryBook);

        listBook = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        // Lấy dữ liệu sách từ Firebase
        databaseReference.child("books").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listBook.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Book book = snapshot.getValue(Book.class);
                    listBook.add(book);
                }

                // Cập nhật adapter sau khi dữ liệu thay đổi
                BookRecyclerAdapter bookRecyclerAdapter = new BookRecyclerAdapter(getActivity(), listBook, R.layout.layout_item_colum_book);
                BookRecyclerAdapter bookRecyclerAdapterTopBook = new BookRecyclerAdapter(getActivity(), listBook, R.layout.layout_item_colum_book_popular);
                BookRecyclerAdapter bookRecyclerAdapterNewBook = new BookRecyclerAdapter(getActivity(), listBook, R.layout.layout_item_colum_book_3_row);
                recycler_view.setAdapter(bookRecyclerAdapterTopBook);
                recycler_view_Moi_XB.setAdapter(bookRecyclerAdapterNewBook);
                recycler_view2.setAdapter(bookRecyclerAdapter);
                recycler_view3.setAdapter(bookRecyclerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi
                Toast.makeText(getActivity(), "Failed to load books.", Toast.LENGTH_SHORT).show();
            }
        });

//        // danh sách Book
//        listBook = new ArrayList<>();
//        listBook.add(new Book("sach"));
//        listBook.add(new Book("sach1"));
//        listBook.add(new Book("sach2"));
//        listBook.add(new Book("sach1"));
//        listBook.add(new Book("sach2"));
//
//        // Set the adapter
////        recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        BookRecyclerAdapter bookRecyclerAdapter = new BookRecyclerAdapter(getActivity(), listBook, R.layout.layout_item_colum_book);
//        BookRecyclerAdapter bookRecyclerAdapterTopBook = new BookRecyclerAdapter(getActivity(), listBook, R.layout.layout_item_colum_book_popular);
//        BookRecyclerAdapter bookRecyclerAdapterNewBook = new BookRecyclerAdapter(getActivity(), listBook, R.layout.layout_item_colum_book_3_row);
//        recycler_view.setAdapter(bookRecyclerAdapterTopBook);
//        recycler_view_Moi_XB.setAdapter(bookRecyclerAdapterNewBook);
//        recycler_view2.setAdapter(bookRecyclerAdapter);
//        recycler_view3.setAdapter(bookRecyclerAdapter);
//
        // dach sách danh mục
        listCategoryBook = new ArrayList<>();
        listCategoryBook.add(new CategoryBook("Kinh doanh", "ic_kinhdoanh"));
        listCategoryBook.add(new CategoryBook("Văn học", "ic_vanhoc"));
        listCategoryBook.add(new CategoryBook("Tâm linh - Tôn giáo", "ic_tamlin_tongiao"));
        listCategoryBook.add(new CategoryBook("Tư duy", "ic_tuduy"));
        listCategoryBook.add(new CategoryBook("Kỹ năng", "ic_kynang"));
        listCategoryBook.add(new CategoryBook("Tâm lý hoc", "ic_tamlyhoc"));


        CategoryBookAdapter categoryBookAdapter = new CategoryBookAdapter(getActivity(), R.layout.layout_row_category_book, listCategoryBook);
        lvCategoryBook.setAdapter(categoryBookAdapter);
        return view;
    }
}