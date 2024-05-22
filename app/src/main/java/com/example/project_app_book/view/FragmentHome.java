package com.example.project_app_book.view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.project_app_book.model.Author;
import com.example.project_app_book.view.adapter.AuthorRecyclerAdapter;
import com.example.project_app_book.view.adapter.BookRecyclerAdapter;
import com.example.project_app_book.view.adapter.CategoryBookAdapter;
import com.example.project_app_book.R;
import com.example.project_app_book.model.Book;
import com.example.project_app_book.model.CategoryBook;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class FragmentHome extends Fragment {
    private ImageView imageViewAnh;
    private ArrayList<Book> listBook = new ArrayList<>();
    private ArrayList<CategoryBook> listCategoryBook = new ArrayList<>();
    private ArrayList<Author> listAuthor = new ArrayList<>();
    private RecyclerView recycler_view_top_book, recycler_view_Moi_XB, recycler_view_author, recycler_view_kham_pha_english_book;

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

        addControls(view);
        addEvents();
        return view;
    }

    private void addControls(View view){
        imageViewAnh = (ImageView) view.findViewById(R.id.imgBook);

        recycler_view_top_book = view.findViewById(R.id.recycler_view_top_book);
        recycler_view_Moi_XB = view.findViewById(R.id.recycler_view_Moi_XB);
        recycler_view_author = view.findViewById(R.id.recycler_view_author);
        recycler_view_kham_pha_english_book = view.findViewById(R.id.recycler_view_kham_pha_english_book);

        lvCategoryBook = view.findViewById(R.id.lvCategoryBook);

//        // Thiết lập LayoutManager cho RecyclerViews
//        recycler_view_top_book.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        recycler_view_Moi_XB.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        recycler_view_author.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        recycler_view_kham_pha_english_book.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void addEvents(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        // Tạo các task lấy dữ liệu từ Firebase
        Task<DataSnapshot> booksTask = databaseReference.child("books").get();
        Task<DataSnapshot> authorsTask = databaseReference.child("authors").get();

        // Kết hợp các task
        Tasks.whenAll(booksTask, authorsTask).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Lấy dữ liệu sách
                    DataSnapshot booksSnapshot = booksTask.getResult();
                    listBook.clear();
                    for (DataSnapshot snapshot : booksSnapshot.getChildren()) {
                        Book book = snapshot.getValue(Book.class);
                        listBook.add(book);
                    }

                    // Lấy dữ liệu tác giả
                    DataSnapshot authorsSnapshot = authorsTask.getResult();
                    listAuthor.clear();
                    for (DataSnapshot snapshot : authorsSnapshot.getChildren()) {
                        Author author = snapshot.getValue(Author.class);
                        listAuthor.add(author);
                    }

                    // Cập nhật adapter sau khi dữ liệu thay đổi
                    BookRecyclerAdapter bookRecyclerAdapter = new BookRecyclerAdapter(getActivity(), listBook, R.layout.layout_item_colum_book);
                    BookRecyclerAdapter bookRecyclerAdapterTopBook = new BookRecyclerAdapter(getActivity(), listBook, R.layout.layout_item_colum_book_popular);
                    BookRecyclerAdapter bookRecyclerAdapterNewBook = new BookRecyclerAdapter(getActivity(), listBook, R.layout.layout_item_colum_book_3_row);
                    recycler_view_top_book.setAdapter(bookRecyclerAdapterTopBook);
                    recycler_view_Moi_XB.setAdapter(bookRecyclerAdapterNewBook);
                    recycler_view_kham_pha_english_book.setAdapter(bookRecyclerAdapter);

                    AuthorRecyclerAdapter authorRecyclerAdapter = new AuthorRecyclerAdapter(getActivity(), listAuthor, R.layout.layout_item_author);
                    recycler_view_author.setAdapter(authorRecyclerAdapter);
                } else {
                    // Xử lý lỗi
                    Toast.makeText(getActivity(), "Failed to load data.", Toast.LENGTH_SHORT).show();
                }
            }
        });





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

    }

}