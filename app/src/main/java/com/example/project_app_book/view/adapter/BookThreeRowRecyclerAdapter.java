package com.example.project_app_book.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_app_book.R;
import com.example.project_app_book.model.AnimationUtil;
import com.example.project_app_book.model.Book;

import java.util.ArrayList;
import java.util.HashMap;

public class BookThreeRowRecyclerAdapter extends RecyclerView.Adapter<BookThreeRowRecyclerAdapter.ViewHolder> {

    private ArrayList<Book> bookList;
    private Context context;
    private int layoutResource;
    private HashMap<String, String> authorMap;
    private OnItemClickListener listener;

     //Constructor
//    public BookThreeRowRecyclerAdapter(Context context, ArrayList<Book> bookList, int layoutResource) {
//        this.context = context;
//        this.bookList = bookList;
//        this.layoutResource = layoutResource;
//    }

    public BookThreeRowRecyclerAdapter(Context context, ArrayList<Book> bookList, int layoutResource, HashMap<String, String> authorMap) {
        this.context = context;
        this.bookList = bookList;
        this.layoutResource = layoutResource;
        this.authorMap = authorMap;
    }

    public interface OnItemClickListener {
        void onItemClick(Book book);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgAvatarBook;
        public TextView tvNameBook, tvNameAuthor;

        public ViewHolder(View itemView) {
            super(itemView);
            imgAvatarBook = itemView.findViewById(R.id.imgAvatarBook);
            tvNameBook = itemView.findViewById(R.id.tvNameBook);
            tvNameAuthor = itemView.findViewById(R.id.tvNameAuthor);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            AnimationUtil.applyScaleAnimation(context, v, new AnimationUtil.AnimationListener() {
                                @Override
                                public void onAnimationEnd() {
                                    listener.onItemClick(bookList.get(position));
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutResource, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = bookList.get(position);

        int resourceId = context.getResources().getIdentifier(book.getImage(), "drawable", context.getPackageName());
        holder.imgAvatarBook.setImageResource(resourceId);

        holder.tvNameBook.setText(book.getTitle());


        String authorId = book.getAuthorId();
        String authorName = authorMap.get(authorId);
        if (authorName != null) {
            holder.tvNameAuthor.setText(authorName);
        } else {
            holder.tvNameAuthor.setText("Unknown Author");
        }

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}
