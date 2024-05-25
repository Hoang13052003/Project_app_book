package com.example.project_app_book.view.adapter;

import android.annotation.SuppressLint;
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

public class BookThreeRowRecyclerAdapter extends RecyclerView.Adapter<BookThreeRowRecyclerAdapter.ViewHolder> {

    private ArrayList<Book> bookList;
    private Context context;
    private int layoutResource;
    private OnItemClickListener listener;

    // Constructor
    public BookThreeRowRecyclerAdapter(Context context, ArrayList<Book> bookList, int layoutResource) {
        this.context = context;
        this.bookList = bookList;
        this.layoutResource = layoutResource;
    }

    // Interface to handle item click events
    public interface OnItemClickListener {
        void onItemClick(Book book);
    }

    // Method to set the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgAvatarBook;
        public TextView tvNameBook, tvNameAuthor;

        public ViewHolder(View itemView) {
            super(itemView);
            imgAvatarBook = itemView.findViewById(R.id.imgAvatarBook);
            tvNameBook = itemView.findViewById(R.id.tvNameBook);
            tvNameAuthor = itemView.findViewById(R.id.tvNameAuthor);

            // Set item click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            // Apply scale animation
                            AnimationUtil.applyScaleAnimation(context, v, new AnimationUtil.AnimationListener() {
                                @Override
                                public void onAnimationEnd() {
                                    // Handle item click
                                    listener.onItemClick(bookList.get(position));
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutResource, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = bookList.get(position);

        // Set image resource
        int resourceId = context.getResources().getIdentifier(book.getImage(), "drawable", context.getPackageName());
        holder.imgAvatarBook.setImageResource(resourceId);

        // Set book title
        holder.tvNameBook.setText(book.getTitle());

        // Set author name (assuming getAuthorId() returns author name)
        holder.tvNameAuthor.setText(String.valueOf(book.getAuthorId()));
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}
